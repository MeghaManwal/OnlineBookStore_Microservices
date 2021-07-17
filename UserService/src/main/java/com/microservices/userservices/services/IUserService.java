package com.microservices.userservices.services;

import javax.mail.MessagingException;

import com.microservices.userservices.dto.UserDataDTO;
import com.microservices.userservices.dto.UserLoginDTO;
import com.microservices.userservices.model.UserData;

public interface IUserService {

	public UserData createNewUser(UserDataDTO userdto);
	public String userLogin(UserLoginDTO userLoginDto);
	public String sendPasswordResetLink(String emailId) throws MessagingException;
	public String resetPassword(String password, String urlToken);
	public void verifyEmail(String tokenId);
	public UserData setUserDetails(String token);
}
