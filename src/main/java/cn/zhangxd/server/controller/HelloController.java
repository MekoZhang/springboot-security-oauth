package cn.zhangxd.server.controller;

import cn.zhangxd.server.common.controller.BaseController;
import cn.zhangxd.server.domain.User;
import cn.zhangxd.server.service.IHelloService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by zhangxd on 16/3/9.
 */
@RestController
@RequestMapping("/api")
@Api(basePath = "/api", value = "用户API", description = "用户相关", produces = "application/json")
public class HelloController extends BaseController {

    @Autowired
    IHelloService helloService;

    @ApiOperation(httpMethod = "GET", value = "value", notes = "notes")
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public List<User> hello(String a) {
//        String b = null;
//        b.toString();
        logger.info("Controller");
        List<User> users = this.helloService.findUsers();
        return users;
    }

}
