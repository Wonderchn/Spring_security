package com.hongna;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hongna.domain.User;
import com.hongna.mapper.UserMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;


@SpringBootTest
public class MapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Test
    public void testUserMapper(){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName,"cl");

        User user = userMapper.selectOne(wrapper);
        System.out.println(user);

        String encode = encoder.encode("111");
        System.out.println(encode.toString());
    }
}