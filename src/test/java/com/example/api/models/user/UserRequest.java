package com.example.api.models.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserRequest{
	String firstName;
	String lastName;
	String password;
	Integer userStatus;
	String phone;
	Integer id;
	String email;
	String username;
}