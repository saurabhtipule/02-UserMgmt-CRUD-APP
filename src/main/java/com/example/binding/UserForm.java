package com.example.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserForm {

	private String firstName;

	private String lastName;

	private String email;

	private Long phoneNo;

	private LocalDate dob;

	private String gender;

	private Integer countryId;

	private Integer stateId;

	private Integer cityId;
}
