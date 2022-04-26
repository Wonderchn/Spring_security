package com.hongna.service.Impl;

import com.hongna.domain.LoginUser;
import com.hongna.domain.ResponseResult;
import com.hongna.domain.User;
import com.hongna.service.LoginServcie;
import com.hongna.utils.JwtUtil;
import com.hongna.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;
@Service
public class LoginServiceImpl implements LoginServcie {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //如果认证没通过，给出对应的提示
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("登录失败");
        }
        //认证成功，发放token，redis中存储用户的相关信息
        LoginUser loginUser = (LoginUser)authenticate.getCredentials();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //authenticate存入redis
        redisCache.setCacheObject("login:"+userId,loginUser);
        //把token响应给前端
        HashMap<String,String> map = new HashMap<>();
        map.put("token",jwt);
        //后续携带token进行校验
        return new ResponseResult(200,"登陆成功",map);
    }
}
