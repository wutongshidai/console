package com.wutong.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.parasol.core.service.UserService;
import com.parasol.core.user.User;


@RestController
@RequestMapping("/business")
public class ConsumerController {

	@Reference
	private UserService userService;
	
	
	private String PREFIX = "/business/consumer/";
	
	@GetMapping("/consumer")
	public User index(int id) {
		return userService.selectByPrimaryKey(id);
	}
}
