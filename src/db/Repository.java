package db;

import domain.model.Message;
import domain.model.Person;
import domain.model.RelationObj;

import java.util.List;

public interface Repository {
	/**
	 * Returns person with given name
	 * @trows DbException if the given name is not effective of empty
	 * @return the person with given name, if it exists
	 * @return null if no person with given name is found
	 */
	Person get(String email); // todo name?

	//Person getPerson(String firstName);

	void addPerson(Person person);

	/**
	 * Returns a list with all persons
	 */
	List<Person> getAll();

	/**
	 * All males
	 */
	/*List<Person> getAllMales();

	*//**
	 * All females
	 *//*
	List<Person> getAllFemales();

	*//**
	 * Sorts persons on age
	 *//*
	List<Person> getByAge(int age);

	*//**
	 * Sorts persons on age gap
	 *//*
	List<Person> getByAgeGap(int min, int max);*/

	Person getAuthenticatedUser(String email, String password);

    void makeRelation(String a, String b, String me);

	//RelationObj getRelation(String a, String b, String c);

	List<RelationObj> getRelations();

	List<RelationObj> getRelations(String name);

	List<Message> getChats(String name);

	String hashPassword(String password);

	void sendChat(String bericht, String ontvanger, String zender);

	List<Person> getFriends(String email);

	void editPassword(String email, String hashPassword);
}
