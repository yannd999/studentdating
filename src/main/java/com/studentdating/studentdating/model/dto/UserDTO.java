package com.studentdating.studentdating.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.studentdating.studentdating.model.exception.DomainException;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.time.Period;

public class UserDTO {
	private Long id;
	private String firstName, lastName, sex, location, username; // email
	private String editFirstName, editLastName, editLocation; // om errors te kunnen gooien bij signup (no firstname given) en niet bij edit pagina
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) //todo signup error bij date
	private LocalDate birth_date;
	@JsonIgnore //todo?
	private String password, reppassword;
	private String role; // Role role;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		if (id < 0) throw new DomainException("Id is invalid");
		this.id = id;
	}

	public UserDTO() {
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		if (firstName.trim().isEmpty()) throw new IllegalArgumentException("First name is empty");
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		if (lastName.trim().isEmpty()) throw new DomainException("Last name is empty");
		this.lastName = lastName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		if (sex.trim().isEmpty()) throw new DomainException("Sex is empty");
		this.sex = sex;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		if (location.trim().isEmpty()) throw new DomainException("Location is empty"); // todo location needed?
		this.location = location;
	}

	public String getUsername() {
		return username; // email
	}

	public void setUsername(String username) {
		if (username.trim().isEmpty()) throw new IllegalArgumentException("Email not valid");
		else this.username = username; // email
	}

	public int getAge() {
		return Period.between(birth_date, LocalDate.now()).getYears();
	}

	public LocalDate getBirthDate() {
		return birth_date;
	}

	public void setBirthDate(LocalDate birthDate) {
		// todo max min
		this.birth_date = birthDate;
	}

	public String getRole() { // Role getRole() {
		return role;
	}

	public void setRole(String role) { // void setRole(Role role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if (password.trim().isEmpty()) throw new IllegalArgumentException("Password is empty");
		this.password = password;
	}

	public String getReppassword() {
		return reppassword;
	}

	public void setReppassword(String reppassword) {
		if (reppassword.trim().isEmpty()) throw new IllegalArgumentException("Password II is empty");
		this.reppassword = reppassword;
	}

	// bij update user parameter niet verplicht dus geen error bij null
	public String getEditFirstName() {
		return editFirstName;
	}
	public void setEditFirstName(String editFirstName) {
		//todo enkel setten if not null???????????
		this.firstName = editFirstName;
	}

	public String getEditLastName() {
		return editLastName;
	}
	public void setEditLastName(String editLastName) {
		this.lastName = editLastName;
	}

	public String getEditLocation() {
		return editLocation;
	}
	public void setEditLocation(String editLocation) {
		this.location = editLocation;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof UserDTO &&
				this.firstName.equals(((UserDTO) o).getFirstName()) &&
				this.lastName.equals(((UserDTO) o).getLastName()) &&
				this.birth_date == ((UserDTO) o).getBirthDate() &&
				this.sex.equals(((UserDTO) o).getSex()) &&
				this.location.equals(((UserDTO) o).getLocation()) &&
				this.username.equals(((UserDTO) o).getUsername()) &&
				this.password.equals(((UserDTO) o).getPassword());
	}

	@Override
	public String toString() {
		return id + " name: " + firstName + " " + lastName + ", birthday: " + birth_date + ", sex: " + sex + ", location: " + location + ", username: " + username + ", rol: " + role; //todo password wegdoen
	}
}
