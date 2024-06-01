package com.example.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.binding.UserForm;
import com.example.service.UserManagementService;

@RestController
public class UserRestController {

	@Autowired
	private UserManagementService service;

	@GetMapping(value = "/email/{emailId}")
	public String emailCheck(@PathVariable("emailId") String emailId) {
		return service.emailCheck(emailId);
	}

	@GetMapping(value = "/countries")
	public Map<Integer, String> getCountries() {
		return service.loadCountries();
	}

	@GetMapping(value = "/states/{countryId}")
	public Map<Integer, String> getStates(@PathVariable("countryId") Integer countryId) {
		return service.loadStates(countryId);
	}

	@GetMapping(value = "/cities/{stateId}")
	public Map<Integer, String> getCities(@PathVariable("stateId") Integer stateId) {
		return service.loadCities(stateId);
	}

	@PostMapping(value = "/user")
	public String regsiterUser(@RequestBody UserForm userform) {
		return service.registerUser(userform);
	}

}
