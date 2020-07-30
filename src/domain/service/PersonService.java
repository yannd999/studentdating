package domain.service;

import db.Repository;
import db.RepositorySQL;
import domain.model.Message;
import domain.model.Person;
import domain.model.RelationObj;

import java.util.List;
import java.util.Properties;

public class PersonService {
	private Repository personRepository;

	public PersonService(Properties properties) {
		personRepository = new RepositorySQL(properties);
	}

	/*public Person getPerson(String email) {
		return personRepository.get(email);
	}*/

	/*public Person getPersonByName(String firstName) {
		return personRepository.getPerson(firstName);
	}*/

	public Person getPerson(String email) {
		return personRepository.get(email);
	}

	public List<Person> getPersons() {
		return personRepository.getAll();
	}

	public void addPerson(Person person) {
		personRepository.addPerson(person);
	}

/*	public void updatePersons(Person person) {
		getPersonRepository().update(person);
	}//todo

	public void deletePerson(String id) {
		getPersonRepository().delete(id);
	}*/

	public Person getAuthenticatedUser(String email, String password) {
		//System.out.println("Service authenticate: " + email + " p: " + password); //Todo
		return personRepository.getAuthenticatedUser(email, password);
	}

	public void makeRelation(String a, String b, String me) {
		personRepository.makeRelation(a, b, me);
	}

/*	public RelationObj getRelation(String a, String b, String c) {
		return personRepository.getRelation(a, b, c);
	}*/


	public List<RelationObj> getRelations() {
		return personRepository.getRelations();
	}

	public List<RelationObj> getRelations(String name) {
		return personRepository.getRelations(name);
	}

	public List<Message> getChats(String name) {
		return personRepository.getChats(name);
	}

	public String hashPassword(String password) {
		return personRepository.hashPassword(password); //todo enough?
	}

	public void addChat(String bericht, String ontvanger, String zender) {
		// todo ontvanger moet first en last name
		if (bericht.trim().isEmpty() || ontvanger.trim().isEmpty() || zender.trim().isEmpty()) throw new IllegalArgumentException("Parameter is null");
		//System.out.println("add chat " + bericht + ", o: " + ontvanger + ", z: " + zender); //todo
		// bericht toevoegen bij zender
		personRepository.sendChat(bericht, ontvanger, zender);
//		this.getPersonByName(zender).addChat(bericht, ontvanger, zender); //todo by email
		// bericht toevoegen bij ontvanger
//		this.getPersonByName(ontvanger).addChat(bericht, ontvanger, zender);
		//System.out.println("service chat: " + bericht + ", o: " + ontvanger + ", ze: " + zender); //Todo
	}

	public List<Person> getFriends(String email) {
		return personRepository.getFriends(email);
	}

	public void editPassword(Person person, String newpass) {
		personRepository.editPassword(person.getEmail(), hashPassword(newpass));
	}
}
