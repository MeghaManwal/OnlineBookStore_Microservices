package com.microservices.userservices.services;

import java.util.Optional;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.microservices.userservices.dto.UserDataDTO;
import com.microservices.userservices.dto.UserLoginDTO;
import com.microservices.userservices.exception.BookStoreException;
import com.microservices.userservices.exception.UserDataException;
import com.microservices.userservices.model.UserData;
import com.microservices.userservices.repository.UserDataRepository;
import com.microservices.userservices.utils.EmailService;
import com.microservices.userservices.utils.Token;

@Service
public class UserService implements IUserService{

	@Autowired
	private UserDataRepository userdatarepo;
	
	@Autowired
        private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	Token jwtToken = new Token();
	
	@Autowired
        private EmailService emailService;

	@Override
	public UserData createNewUser(UserDataDTO userdto) {
//		Optional<UserData> checkEmailId = userdatarepo.findByEmailID(userdto.getEmailId()) ;
//		if(checkEmailId.isPresent()) {
//			throw new BookStoreException(BookStoreException.ExceptionTypes.USER_ALREADY_PRESENT);
//		}else {
			String password = bCryptPasswordEncoder.encode(userdto.getPassword());	
			userdto.setPassword(password);
		    UserData userdata = new UserData(userdto.getFullName(),
		    		                         userdto.getPhoneNumber(),
		    		                         userdto.getEmailId(),
		    		                         password);
		    UserData savedata = userdatarepo.save(userdata);
		    String tokenId = jwtToken.generateVerificationToken(userdata.getUserId());
		    String requestUrl ="http://localhost:8080/user/verify/email/"+tokenId;
		    try {
	            emailService.sendMail(requestUrl,"the verification link is ", userdto.getEmailId());
	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }
		    return savedata;
	        //}
	}

	@Override
	public String userLogin(UserLoginDTO userLoginDto) {
		Optional<UserData> userEmail = userdatarepo.findByEmailID(userLoginDto.getEmailId());
		if (!userEmail.isPresent()) {
	                throw new UserDataException(UserDataException.ExceptionTypes.EMAIL_NOT_FOUND);
	        }
		if(userEmail.get().isVerified){
	                boolean password = bCryptPasswordEncoder.matches(userLoginDto.password, userEmail.get().password);
	        if (!password) {
	                throw new UserDataException(UserDataException.ExceptionTypes.PASSWORD_NOT_FOUND);
	        }
	        String tokenString = jwtToken.generateLoginToken(userEmail.get());
	        return tokenString;
	        }
	        throw  new UserDataException(UserDataException.ExceptionTypes.EMAIL_INVALID);
	}
	
	@Override
        public String sendPasswordResetLink(String email) throws MessagingException {
                UserData userdata = userdatarepo.findByEmailID(email)
        		            .orElseThrow(() -> new UserDataException(UserDataException.ExceptionTypes.EMAIL_NOT_FOUND));
                String token = jwtToken.generateVerificationtoken(userdata);
                String urlToken = "Link provided to RESET your password \n"
                           +"http://localhost:8080/user/reset/password/" 
        		           +token;
                emailService.sendMail(urlToken, "To RESET Password", userdata.emailID);
                return "The link to RESET Password is sent";
        }

	@Override
	public String resetPassword(String password, String urlToken) {
		UUID userId = jwtToken.decodeJWT(urlToken);
		UserData userdata = userdatarepo.findById(userId)
				             .orElseThrow(() -> new UserDataException(UserDataException.ExceptionTypes.USER_NOT_FOUND));
		String encodePassword = bCryptPasswordEncoder.encode(password);
		userdata.password=encodePassword;
		userdatarepo.save(userdata);
                return "Password is Successfully Reset";
	}

	@Override
	public void verifyEmail(String tokenId) {
		UUID token = jwtToken.decodeJWT(tokenId);
		Optional<UserData> userId= userdatarepo.findById(token);
		if(!userId.isPresent()) {
			throw new BookStoreException(BookStoreException.ExceptionTypes.USER_NOT_FOUND); 
		}
		userId.get().isVerified=true;
		userdatarepo.save(userId.get());
	}

	@Override
	public UserData setUserDetails(String token) {
		UUID userId = jwtToken.decodeJWT(token);

                UserData findTheExistedUser = userdatarepo.findById(userId).
                orElseThrow(() ->new BookStoreException(BookStoreException.ExceptionTypes.USER_NOT_FOUND));

                return findTheExistedUser;
	}
}