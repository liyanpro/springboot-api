package cloud.liyan.springbootapi.controller;

import cloud.liyan.springbootapi.annotation.LoginCheck;
import cloud.liyan.springbootapi.util.TokenUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liyan
 * @description
 * @date 2020-05-20 14:37
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @RequestMapping("/login")
    public Object login(HttpServletRequest request, HttpServletResponse response) {

        String loginName = request.getHeader("loginName");
        Map<String, Object> claims = new HashMap<>();
        claims.put("loginName", loginName);
        //密码校验正确后走下面创建token
        return TokenUtil.getToken(claims);
    }

    @LoginCheck
    @RequestMapping("/test")
    public Object testApi(HttpServletRequest request, HttpServletResponse response) {
        return "hello.word";

    }

}
