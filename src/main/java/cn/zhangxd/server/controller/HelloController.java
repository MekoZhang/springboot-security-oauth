package cn.zhangxd.server.controller;

import cn.zhangxd.server.common.controller.BaseController;
import cn.zhangxd.server.domain.User;
import cn.zhangxd.server.service.IHelloService;
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
public class HelloController extends BaseController {

    @Autowired
    IHelloService helloService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public List<User> hello(String a) {
//        String b = null;
//        b.toString();
        logger.info("Controller");
        List<User> users = this.helloService.findUsers();
        return users;
    }

}
