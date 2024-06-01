package com.example.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.binding.LoginForm;
import com.example.service.UserManagementService;

@RestController
public class LoginRestController {

	@Autowired
	private UserManagementService service;

	@PostMapping(value = "/login")
	public String login(@RequestBody LoginForm form) {
		return service.loginUser(form);
	}

}
