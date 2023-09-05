package com.docker.controller;


import com.docker.entity.User;
import com.docker.service.UserService;
import com.docker.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author docker
 * @since 2023-09-05
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "findAll")
    public List<User> findAll(){
        return userService.list();
    }
}

