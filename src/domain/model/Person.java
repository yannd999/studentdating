package domain.model;

import domain.DomainException;

import java.util.ArrayList;
import java.util.List;

public class Person {
	private String firstName, lastName, sex, location, email, password;
	private int age;
	private List<RelationObj> relations;
	//private List<Message> chats = new ArrayList<>(); //todo replace by db???

	public Person() {
		relations = new ArrayList<>();
	}

	public Person(String firstName, String lastName, int age, String sex, String location, String email, String password) {
		relations = new ArrayList<>();
		setFirstName(firstName);
		setLastName(lastName);
		setAge(age);
		setSex(sex);
		setLocation(location);
		setEmail(email);
		setPassword(password);
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (email.trim().isEmpty()) throw new IllegalArgumentException("Email not valid");
		else this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if (password.trim().isEmpty()) throw new DomainException("Password is empty");
		this.password = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		if (age < 15 || age > 150) throw new DomainException("Age is invalid");
		this.age = age;
	}

	public void addRelation(RelationObj relation) {
		if (relation == null) throw new IllegalArgumentException("Relation is null");
		this.relations.add(relation);
	}

	public void setRelations(List<RelationObj> relations) {
		if (relations == null) throw new IllegalArgumentException("Relations is null");
		this.relations = relations;
	}

	public List<RelationObj> getRelations() {
		return this.relations;
	}

/*	public void addChat(String bericht, String ontvanger, String zender) {
		chats.add(new Message(zender, ontvanger, bericht));
		System.out.println("Person chats: " + chats); //todo
	}

	public void setChats(List<Message> chats) {
		if (chats == null) throw new IllegalArgumentException("Chats is null");
		this.chats = chats;
	}

	public List<Message> getChats() {
		return this.chats;
	}*/

	@Override
	public boolean equals(Object o) {
		return o instanceof Person &&
				this.firstName.equals(((Person) o).getFirstName()) &&
				this.lastName.equals(((Person) o).getLastName()) &&
				this.age == ((Person) o).getAge() &&
				this.sex.equals(((Person) o).getSex()) &&
				this.location.equals(((Person) o).getLocation()) &&
				this.email.equals(((Person) o).getEmail()) &&
				this.password.equals(((Person) o).getPassword());
	}

	@Override
	public String toString() {
		return firstName + ' ' + lastName + ", age: " + age + ", sex: " + sex + ", location: " + location + ", email: " + email;
	}
}
