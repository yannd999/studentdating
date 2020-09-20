package com.studentdating.studentdating.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.studentdating.studentdating.model.exception.DomainException;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name="person")
public class User implements Serializable { //serializable volgens tutorial https://www.youtube.com/watch?v=XHD3cJWiicA
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	@JsonIgnore
	@Column(name="password")
	private String password;
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	@Column(name="birth_date", columnDefinition="DATE")
	private LocalDate birth_date;
	@Column(name="sex")
	private String sex;
	@Column(name="location")
	private String location;
	@Column(name="email")
	private String username;
	@JsonIgnore
	@Column(name="role", columnDefinition="VARCHAR(15)")
	@Type(type = "org.hibernate.type.TextType")
	@Enumerated(EnumType.STRING)
	private String role; // Role role;//todo

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		if (id < 0) throw new DomainException("Id is invalid");
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		if (firstName.trim().isEmpty()) throw new DomainException("First name is empty");
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
		if (username.trim().isEmpty()) throw new DomainException("Username not valid");
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

	public void setRole(String role) { //Role role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if (password.trim().isEmpty()) throw new DomainException("Password is empty");
		this.password = password;
	}


  /*todo  public void addRelation(RelationDTO relation) {
        if (relation == null) throw new IllegalArgumentException("Relation is null");
        this.relations.add(relation);
    }

    public void setRelations(List<RelationDTO> relations) {
        if (relations == null) throw new IllegalArgumentException("Relations is null");
        this.relations = relations;
    }

    public List<RelationDTO> getRelations() {
        return this.relations;
    }*/

/*	public void addChat(String bericht, String ontvanger, String zender) {
		chats.add(new MessageDTO(zender, ontvanger, bericht));
		System.out.println("Person chats: " + chats); //todo
	}

	public void setChats(List<MessageDTO> chats) {
		if (chats == null) throw new IllegalArgumentException("Chats is null");
		this.chats = chats;
	}

	public List<MessageDTO> getChats() {
		return this.chats;
	}*/

	@Override
	public boolean equals(Object o) {
		return o instanceof User &&
				this.firstName.equals(((User) o).getFirstName()) &&
				this.lastName.equals(((User) o).getLastName()) &&
				this.birth_date == ((User) o).getBirthDate() &&
				this.sex.equals(((User) o).getSex()) &&
				this.location.equals(((User) o).getLocation()) &&
				this.username.equals(((User) o).getUsername()) &&
				this.password.equals(((User) o).getPassword());
	}

	@Override
	public String toString() {
		return id + " name: " + firstName + " " + lastName + ", birthday: " + birth_date + ", sex: " + sex + ", location: " + location + ", username: " + username + ", rol: " + role;
	}
}
