package com.yoho.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yoho.service.AccountService;

@RestController
public class UserController {
	private static final Logger logger = LogManager.getLogger(UserController.class);

	@Autowired
	private AccountService accountService;

	@RequestMapping(value = "/api/user/addroletouser/{username}/{role}", method = RequestMethod.GET)
	@ResponseBody
	public void addRoleToUser(@PathVariable String username, @PathVariable String role) {
		logger.info("addRoleToUser username" + username + " role " + role);
		accountService.addRoleToUser(username, role);

	}
}