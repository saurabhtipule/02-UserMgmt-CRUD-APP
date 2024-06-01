package com.example.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.UserManagementService;

@RestController
public class ForgotPasswordRestController {

	@Autowired
	private UserManagementService service;

	@GetMapping(value = "/forgotpwd/{emailId}")
	public String forgotPassword(@PathVariable("emailId") String emailId) {
		return service.forgotPwd(emailId);
	}
}
