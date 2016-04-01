package cn.zhangxd.server.base;

import cn.zhangxd.server.common.interceptor.LogInterceptor;
import cn.zhangxd.server.common.message.converter.JsonMapper;
import cn.zhangxd.server.common.message.converter.MyBeanSerializerModifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

/**
 * WEB配置类
 * Created by zhangxd on 16/3/10.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }

    @Bean
    public LogInterceptor logInterceptor() {
        return new LogInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 配置日志拦截器拦截请求路径
        registry
                .addInterceptor(logInterceptor())
                .addPathPatterns("/api/**")
                .addPathPatterns("/admin/**");
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new JsonMapper();
        mapper.setSerializerFactory(mapper.getSerializerFactory().withSerializerModifier(new MyBeanSerializerModifier()));
        return mapper;
    }

    @Bean
    public HttpMessageConverter httpMessageConverter() {
        return new MappingJackson2HttpMessageConverter(this.objectMapper());
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(false) // 系统对外暴露的 URL 不会识别和匹配 .* 后缀
                .setUseTrailingSlashMatch(true); // 系统不区分 URL 的最后一个字符是否是斜杠 /
    }

    @Override
    public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
        // 等价于<mvc:default-servlet-handler />, 对静态资源文件的访问, 将无法 mapping 到 Controller 的 path 交给 default servlet handler 处理
        configurer.enable();
    }

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        super.addViewControllers(registry);
        registry.addViewController("/").setViewName("forward:/index");
        registry.addViewController("/index");
        registry.addViewController("/login");
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

//    @Bean
//    public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer() {
//        // 自定义错误页面
//        return new EmbeddedServletContainerCustomizer() {
//            @Override
//            public void customize(ConfigurableEmbeddedServletContainer container) {
//                container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"));
//                container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500"));
//            }
//        };
//    }

}
