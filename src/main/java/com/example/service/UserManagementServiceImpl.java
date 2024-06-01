package com.example.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.binding.LoginForm;
import com.example.binding.UnLockForm;
import com.example.binding.UserForm;
import com.example.entities.CityMasterEntity;
import com.example.entities.CountryMasterEntity;
import com.example.entities.StateMasterEntity;
import com.example.entities.UserAccountEntity;
import com.example.repository.CityMasterRepository;
import com.example.repository.CountryMasterRepository;
import com.example.repository.StateMasterRepository;
import com.example.repository.UserAccountRepository;
import com.example.utils.Emailutils;

@Service
public class UserManagementServiceImpl implements UserManagementService {

	@Autowired
	private UserAccountRepository userRepo;

	@Autowired
	private CountryMasterRepository countryRepo;

	@Autowired
	private StateMasterRepository stateRepo;

	@Autowired
	private CityMasterRepository cityRepo;

	@Autowired
	private Emailutils mailSender;

	@Override
	public String loginUser(LoginForm loginForm) {

		UserAccountEntity entity = userRepo.findByEmailAndPassword(loginForm.getEmail(), loginForm.getPassword());

		if (entity == null)
			return "Invalid Credentials";

		if (entity != null && entity.getAccStatus().equals("LOCKED"))
			return "Your Account is Locked";

		return "Success";
	}

	@Override
	public String emailCheck(String email) {

		UserAccountEntity entity = userRepo.findByEmail(email);

		if (entity == null)
			return "UNIQUE";

		return "DUPLICATE";
	}

	@Override
	public Map<Integer, String> loadCountries() {
		List<CountryMasterEntity> countries = countryRepo.findAll();

		Map<Integer, String> countryMap = new HashMap<Integer, String>();

		for (CountryMasterEntity entity : countries) {
			countryMap.put(entity.getCountryId(), entity.getCountryName());
		}

		return countryMap;
	}

	@Override
	public Map<Integer, String> loadStates(Integer countryId) {

		List<StateMasterEntity> states = stateRepo.findByCountryId(countryId);

		Map<Integer, String> stateMap = new HashMap<Integer, String>();

		for (StateMasterEntity entity : states) {
			stateMap.put(entity.getStateId(), entity.getStateName());
		}

		return stateMap;
	}

	@Override
	public Map<Integer, String> loadCities(Integer stateId) {

		List<CityMasterEntity> cities = cityRepo.findByStateId(stateId);

		Map<Integer, String> cityMap = new HashMap<Integer, String>();

		for (CityMasterEntity entity : cities) {
			cityMap.put(entity.getCityId(), entity.getCityName());
		}

		return cityMap;
	}

	@Override
	public String registerUser(UserForm userform) {

		UserAccountEntity entity = new UserAccountEntity();

		BeanUtils.copyProperties(userform, entity);

		entity.setAccStatus("LOCKED");

		entity.setPassword(generatePwd());

		// Todo:Send Registration Email

		entity = userRepo.save(entity);

		String to = entity.getEmail();
		String subject = "User - Registration- Complete Java Classes";
		String body = "";
		String fileName = "Unlock_Acc_Email_Body_Template.txt";

		body = readEmailBodyContent(fileName, entity);

		boolean isSent = mailSender.sendEmail(to, subject, body);

		if (entity.getUserId() != null && isSent) {
			return "SUCCESS";
		}

		return "FAIL";
	}

	private String readEmailBodyContent(String fileName, UserAccountEntity entity) {

		String body = "";

		StringBuffer sb = new StringBuffer();

		try {
			FileReader fr = new FileReader(fileName);

			BufferedReader br = new BufferedReader(fr);

			String str = br.readLine();

			while (str != null) {
				sb.append(str);
				str = br.readLine();
			}

			body = sb.toString();

			body = body.replace("{fname}", entity.getFirstName());
			body = body.replace("{lname}", entity.getLastName());
			body = body.replace("{Temp_Pass}", entity.getPassword());
			body = body.replace("{EMAIL}", entity.getEmail());

			br.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return body;
	}

	private String generatePwd() {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 6;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		return generatedString;
	}

	@Override
	public String unLockAccount(UnLockForm unlockForm) {

		if (!unlockForm.getNewPass().equals(unlockForm.getConfirmPass())) {
			return "Password and Confirm PassWord Should be Same";
		}

		UserAccountEntity entity = userRepo.findByEmailAndPassword(unlockForm.getEmail(), unlockForm.getTempPass());

		if (entity == null) {
			return "Invalid Credentials";
		}

		entity.setAccStatus("UNLOCKED");
		entity.setPassword(unlockForm.getNewPass());

		userRepo.save(entity);

		return "Account Unlocked";
	}

	@Override
	public String forgotPwd(String email) {

		UserAccountEntity entity = userRepo.findByEmail(email);

		if (entity == null) {
			return "Invalid Email ID";
		}

		String fileName = "Recover_Pass_Email_Body_Template.txt";

		String to = entity.getEmail();
		String subject = "Recover - PassWord - Complete Java Classes";
		String body = readEmailBodyContent(fileName, entity);

		boolean isSent = mailSender.sendEmail(to, subject, body);
		if (!isSent) {
			return "ERROR";
		}

		return "Password sent to registered Email.";
	}

}
