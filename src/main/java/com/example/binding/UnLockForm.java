package com.example.binding;

import lombok.Data;

@Data
public class UnLockForm {

	private String email;

	private String tempPass;

	private String newPass;

	private String confirmPass;

}
