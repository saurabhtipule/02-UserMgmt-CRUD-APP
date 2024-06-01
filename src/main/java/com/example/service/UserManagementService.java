package com.example.service;

import java.util.Map;

import com.example.binding.LoginForm;
import com.example.binding.UnLockForm;
import com.example.binding.UserForm;

public interface UserManagementService {

	// Login Functionality

	public String loginUser(LoginForm loginForm);

	// Registration Functionality

	public String emailCheck(String email);

	public Map<Integer, String> loadCountries();

	public Map<Integer, String> loadStates(Integer countryId);

	public Map<Integer, String> loadCities(Integer stateId);

	public String registerUser(UserForm userform);

	// Unlock Account Functionality

	public String unLockAccount(UnLockForm unlockForm);

	// Forgot password Functionality

	public String forgotPwd(String email);

}
