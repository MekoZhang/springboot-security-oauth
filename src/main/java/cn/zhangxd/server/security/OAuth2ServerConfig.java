package cn.zhangxd.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStoreSerializationStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * OAuth2.0 Server配置
 * Created by zhangxd on 16/3/17.
 * <p>
 * 1.请求token
 * curl -X POST -vu adminClient:12345 http://localhost:8090/oauth/token -H "Accept: application/json" -d "password=spring&username=roy&grant_type=password&scope=admin%20read%20write"
 * 2.使用token请求
 * curl -i -H "Authorization: Bearer 2ce95fff-f2ba-4f39-8eac-abc298f54f0e" http://localhost:8090/api/hello
 * 3.刷新token
 * curl -X POST -vu adminClient:12345 http://localhost:8090/oauth/token -H "Accept: application/json" -d "grant_type=refresh_token&refresh_token=0428558c-7e36-4326-a1e5-99e5f510384a"
 */
@Configuration
public class OAuth2ServerConfig {

    private static final String RESOURCE_ID = "rest_service";

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfig extends ResourceServerConfigurerAdapter {

        @Autowired
        private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

        @Autowired
        private CustomLogoutSuccessHandler customLogoutSuccessHandler;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources
                    .resourceId(RESOURCE_ID);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    .exceptionHandling()
                    .authenticationEntryPoint(customAuthenticationEntryPoint)
                    .and()
                    .formLogin()
                    .loginPage("/#/access/signin")
                    .defaultSuccessUrl("/#/app/dashboard-v1")
                    .and()
                    .logout()
                    .logoutUrl("/oauth/logout")
                    .logoutSuccessHandler(customLogoutSuccessHandler)
                    .and()
                    .csrf()
                    .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
                    .disable()
                    .headers()
                    .frameOptions().disable()
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/api/**").hasRole("USER")
                    .anyRequest().authenticated()
            ;
        }
    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        @Autowired
        private RedisConnectionFactory redisConnectionFactory;

        @Bean
        public RedisTokenStoreSerializationStrategy redisTokenStoreSerializationStrategy() {
            return new CustomSerializationStrategy();
        }

        @Bean
        public TokenStore tokenStore() {
            RedisTokenStore tokenStore = new RedisTokenStore(this.redisConnectionFactory);
            tokenStore.setSerializationStrategy(this.redisTokenStoreSerializationStrategy());
            return new RedisTokenStore(this.redisConnectionFactory);
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            // 定义了客户端细节服务
            clients
                    .inMemory()
                    .withClient("adminClient")
                    .authorizedGrantTypes("password", "refresh_token")
                    .authorities("ADMIN")
                    .scopes("admin", "read", "write")
                    .resourceIds(RESOURCE_ID)
                    .secret("12345")
                    .accessTokenValiditySeconds(3600) // 1 hour
                    .refreshTokenValiditySeconds(2592000) // 30 days

                    .and()
                    .withClient("apiClient")
                    .authorizedGrantTypes("password", "refresh_token")
                    .authorities("USER")
                    .scopes("api", "read", "write")
                    .resourceIds(RESOURCE_ID)
                    .secret("12345")
                    .accessTokenValiditySeconds(3600) // 1 hour
                    .refreshTokenValiditySeconds(2592000) // 30 days
            ;
        }

//        @Override
//        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//            // 在令牌端点上定义了安全约束
//            security
//                    .tokenKeyAccess("permitAll()")
//                    .checkTokenAccess("isAuthenticated()")
//            ;
//        }

        @Autowired
        private CustomUserDetailsService customUserDetailsService;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            // 定义了授权和令牌端点和令牌服务
            endpoints
                    .tokenStore(this.tokenStore())
                    .authenticationManager(this.authenticationManager)
                    .userDetailsService(this.customUserDetailsService)
            ;
        }

        @Bean
        @Primary
        public DefaultTokenServices tokenServices() {
            final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
            defaultTokenServices.setTokenStore(this.tokenStore());
            defaultTokenServices.setSupportRefreshToken(true);
            return defaultTokenServices;
        }
    }

}
