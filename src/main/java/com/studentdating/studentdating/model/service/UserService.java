package com.studentdating.studentdating.model.service;

import com.studentdating.studentdating.model.dto.RelationDTO;
import com.studentdating.studentdating.model.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
	void createUser(UserDTO userDTO);
	UserDetails loadUserByUsername(String username);
	List<UserDTO> getUsers();
	UserDTO getUser(long id);
    void addPerson(UserDTO person);
	UserDTO getLoggedIn();
	List<UserDTO> getFriends();
	void update(UserDTO user);
}
