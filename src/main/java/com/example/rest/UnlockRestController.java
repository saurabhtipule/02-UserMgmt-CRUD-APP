package com.example.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.binding.UnLockForm;
import com.example.service.UserManagementService;

@RestController
public class UnlockRestController {

	@Autowired
	private UserManagementService service;

	@PostMapping(value = "/unlock")
	public String unlockAccount(@RequestBody UnLockForm unlockForm) {
		return service.unLockAccount(unlockForm);
	}

}
