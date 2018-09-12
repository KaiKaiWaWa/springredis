package com.wk;/**
 * Created by yhopu-pc2 on 2018/7/5.
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author wk
 * @className UserController
 **/
@Controller
@RequestMapping(value = "users")

public class UserController {
//    @RequestMapping(value = "/login" ,method={RequestMethod.GET,RequestMethod.POST})
    @GetMapping("/login")
    @ResponseBody
    public ResponseData login(@RequestParam String userName,@RequestParam String password){
        if ("imjack".equals(userName) && "123456".equals(password)) {
            ResponseData responseData = ResponseData.ok();
            User user = new User();
            user.setId(1);
            user.setUsername(userName);
            user.setPassword(password);
            responseData.putDataValue("user", user);
            String token = Jwt.sign(user, 30L * 24L * 3600L * 1000L);
            if (token != null) {
                responseData.putDataValue("token", token);
            }
            return responseData;
        }
        return ResponseData.customerError().putDataValue(ResponseData.ERRORS_KEY, new String[] { "用户名或者密码错误" });
    }

    @GetMapping("/get")
    public String get(){
        System.out.println("你好");
        return "index";
    }
}
