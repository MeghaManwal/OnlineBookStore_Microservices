package com.microservices.bookservice.model;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import lombok.Data;

@Entity
@Table(name="user_data")
public @Data class UserData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(type="uuid-char")
	private UUID userId;
	
	private String fullName;
	private String phoneNumber;
	public String emailID;
	public String password;
	public boolean isVerified;
        public LocalDateTime createdAt = LocalDateTime.now();
     
	public UserData() { }

        public UserData(String fullName, String phoneNumber, String emailID, String password) {
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.emailID = emailID;
		this.password = password;
	}

	
			
}
