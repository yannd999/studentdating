package com.studentdating.studentdating.model.service;

import com.studentdating.studentdating.model.db.UserRepository;
import com.studentdating.studentdating.model.dto.UserDTO;
import com.studentdating.studentdating.model.entity.User;
import com.studentdating.studentdating.model.exception.DomainException;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.*;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private final UserRepository repository;
	@Autowired
	private final PasswordEncoder passwordEncoder;

	private User loggedIn;

	public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
	}

	public List<UserDTO> getUsers() {
		List<UserDTO> users = new ArrayList<>();
		for (User u: repository.findAll()) { //todo sort by age
			users.add(convert(u));
		}
		return users;
	}

	public UserDTO getUser(long id) {
		return getUsers().get((int) id);
	} // todo id -1 ??
	public UserDTO getUser(String username) {
		User user = repository.findByUsername(username);
		if (user == null) return null;
		else return convert(user);
	}

	@Override //todo inlog role = lid
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByUsername(username);
		loggedIn = user; // om ingelogde user te kunnen opvragen
		if (user == null) throw new UsernameNotFoundException("User does not exist");
		else System.out.println("Loaduserbyusername repo user: " + user.toString()); //todo
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
	}

	@Override
	public void createUser(UserDTO userDTO) {
		System.out.println("Service create: " + userDTO.getFirstName() + " " + userDTO.getLastName() + " " + userDTO.getPassword() + " " + userDTO.getReppassword());//todo
		if (!userDTO.getPassword().equals(userDTO.getReppassword())) throw new DomainException("Passwords do not match");
		User user = new User();
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setBirthDate(userDTO.getBirthDate());
		user.setSex(userDTO.getSex());
		user.setLocation(userDTO.getLocation());
		user.setUsername(userDTO.getUsername());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		user.setRole("lid");
		System.out.println("new user: " + user.toString()); //todo
		repository.save(user);
	}

	@Override
	public void addPerson(UserDTO user) {
		repository.save(convertBack(user));
	}

	@Override
	public UserDTO getLoggedIn() {
		return convert(repository.getOne(loggedIn.getId())); // always up to date
	}

	@Override
	public List<UserDTO> getFriends() {
		List<UserDTO> users = this.getUsers();
		users.remove(getLoggedIn());
		return users;
	}
/*
	public List<Message> getChats(String name) {
		return personRepository.getChats(name);
	}
*/

	//todo hash passwords

	public void addChat(String bericht, String ontvanger, String zender) {
		// todo ontvanger moet first en last name
		if (bericht.trim().isEmpty() || ontvanger.trim().isEmpty() || zender.trim().isEmpty()) throw new IllegalArgumentException("Parameter is null");
		//System.out.println("add chat " + bericht + ", o: " + ontvanger + ", z: " + zender); //todo
		// bericht toevoegen bij zender
		//personRepository.sendChat(bericht, ontvanger, zender);
//		this.getPersonByName(zender).addChat(bericht, ontvanger, zender); //todo by email
		// bericht toevoegen bij ontvanger
//		this.getPersonByName(ontvanger).addChat(bericht, ontvanger, zender);
		//System.out.println("service chat: " + bericht + ", o: " + ontvanger + ", ze: " + zender); //Todo
	}

	public List<UserDTO> getFriends(String email) {
		List<UserDTO> users = this.getUsers();
		Iterator<UserDTO> userIterator = users.iterator();
		while (userIterator.hasNext()) {
			if (userIterator.next().getUsername().equals(email)) userIterator.remove(); //remove myself
		}
		return users;
	}

	public void update(UserDTO user) {
		repository.save(convertBack(user));
	}

/*	public void editPassword(UserDTO user, String newpass) {
		repository.editPassword(user.getEmail(), hashPassword(newpass));
	}*/

	private UserDTO convert(User user) {
		UserDTO dto = new UserDTO();
		dto.setId(user.getId());
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		dto.setBirthDate(user.getBirthDate());
		dto.setSex(user.getSex());
		dto.setLocation(user.getLocation());
		dto.setUsername(user.getUsername());
		dto.setPassword(user.getPassword());
		dto.setRole(user.getRole());
		return dto;
	}

	private User convertBack(UserDTO user) {
		User u = new User();
		u.setId(user.getId());
		u.setFirstName(user.getFirstName());
		u.setLastName(user.getLastName());
		u.setBirthDate(user.getBirthDate());
		u.setSex(user.getSex());
		u.setLocation(user.getLocation());
		u.setUsername(user.getUsername());
		u.setPassword(user.getPassword());
		u.setRole(user.getRole());
		return u;
	}

	//todo convert relation?
}
