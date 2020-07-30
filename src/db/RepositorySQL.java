package db;


import domain.model.Message;
import domain.model.Person;
import domain.model.RelationObj;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class RepositorySQL implements Repository {
	//todo preparedstatemnt -> execute, statement -> executeQuery //todo remove
	// todo of resultset = executequery, geen reponds = execute
	private Properties properties;
	private String url = "jdbc:postgresql://localhost:5432/studentdating?currentSchema=studentdating";

	public RepositorySQL(Properties properties) {
		this.properties = properties;
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			throw new DbException(e.getMessage(), e);
		}
	}

	@Override
	public Person get(String email) { //todo name? case sensitive?
		//System.out.println("SQL Get email"); //Todo
		email = "%" + email + "%"; // todo % ?
		String sql = "SELECT * FROM person WHERE email ILIKE ?"; //todo studentdating. zou niet moeten
		try (Connection connection = DriverManager.getConnection(url, properties);
			 PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, email);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			Person person = new Person();
			person.setFirstName(resultSet.getString("first_name"));
			person.setLastName(resultSet.getString("last_name"));
			person.setAge(resultSet.getInt("age"));
			person.setSex(resultSet.getString("sex"));
			person.setLocation(resultSet.getString("location"));
			person.setEmail(resultSet.getString("email"));
			person.setPassword(resultSet.getString("password")); // todo hashed ?

			return person;
		} catch (SQLException e) {
			System.out.println("error sql get() " + e.getMessage());//todo
			e.printStackTrace();//todo
			throw new DbException(e.getMessage(), e);
		}
	}

/*	@Override
	public Person getPerson(String firstName) { //todo name? case sensitive?
		System.out.println("SQL getPerson String"); //todo
		String sql = "SELECT * FROM person WHERE first_name ILIKE ?;";
		try (Connection connection = DriverManager.getConnection(url, properties);
			 PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, firstName);
			//statement.setString(2, lastName);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			resultSet.next();
			Person person = new Person();
			person.setFirstName(firstName);
			person.setLastName(resultSet.getString("last_name"));
			person.setAge(resultSet.getInt("age"));
			person.setSex(resultSet.getString("sex"));
			person.setLocation(resultSet.getString("location"));
			person.setEmail(resultSet.getString("email"));
			person.setPassword(resultSet.getString("password"));

			return person;
		} catch (SQLException e) {
			System.out.println("error sql get() " + e.getMessage());//todo
			e.printStackTrace();//todo
			throw new DbException(e.getMessage(), e);
		}
	}*/

	public String hashPassword(String password) {
		//System.out.println("hashPassword sql: " + password); //todo
		return String.valueOf(Objects.hash(password)); //todo enough?
	}

	@Override
	public void addPerson(Person person) {
		//System.out.println("SQL Add person"); //Todo
		if (person == null) throw new DbException("Person is null");
		//System.out.println("Add person: " + person.getFirstName() + " " + person.getLastName()); //Todo
		try (Connection connection = DriverManager.getConnection(url, properties);
			 PreparedStatement statement = connection.prepareStatement("INSERT INTO person (first_name, last_name, age, sex, location, email, password) values (?, ?, ?, ?, ?, ?, ?);")) {
			statement.setString(1, person.getFirstName());
			statement.setString(2, person.getLastName());
			statement.setInt(3, person.getAge());
			statement.setString(4, person.getSex());
			statement.setString(5, person.getLocation());
			statement.setString(6, person.getEmail());
			statement.setString(7, hashPassword(person.getPassword()));
			statement.execute();
		} catch (SQLException e) {
			System.out.println("Error RepoSQL add person: " + e.getMessage()); //todo
			e.printStackTrace(); //todo
			throw new DbException(e.getMessage(), e);
		}
		//System.out.println("Hashed password: " + hashPassword(person.getPassword()) + " , first: " + person.getPassword()); //todo test
	}

	@Override
	public List<Person> getAll() {
		//System.out.println("SQL Get all"); //Todo
		try (Connection connection = DriverManager.getConnection(url, properties);
			 Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery("SELECT * FROM person");
			System.out.println("Get persons sql"); //Todo
			return createListFromResultset(resultSet);
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}

	/**
	 * Creates a list of persons from the given resultset
	 *
	 * @throws SQLException
	 */
	private List<Person> createListFromResultset(ResultSet resultSet) throws SQLException {
		List<Person> persons = new ArrayList<>();
		while (resultSet.next()) {
			Person person = new Person();
			person.setFirstName(resultSet.getString("first_name"));
			person.setLastName(resultSet.getString("last_name"));
			person.setAge(resultSet.getInt("age"));
			person.setSex(resultSet.getString("sex"));
			person.setLocation(resultSet.getString("location"));
			person.setEmail(resultSet.getString("email"));
			person.setPassword(resultSet.getString("password")); // todo hashed ?
			persons.add(person);
		}
		return persons;
	}

	@Override
	public Person getAuthenticatedUser(String email, String password) {
		//System.out.println("SQL Get authenticateduser"); //Todo
		Person person = get(email);
		if (person.getPassword().equals(password)) return person;
		else return null;
	}

	////////////// relations /////////////////////////

	@Override
	public void makeRelation(String a, String b, String me) {
		//System.out.println("SQL make relation"); //Todo
		if (a == null || b == null || me == null) throw new DbException("Person is null");
		try (Connection connection = DriverManager.getConnection(url, properties);
			 PreparedStatement statement = connection.prepareStatement("INSERT INTO relation (choser, lover_1, lover_2) values (?, ?, ?);")) {
			statement.setString(1, me);
			statement.setString(2, a);
			statement.setString(3, b); //todo extra rij aantal voor aantal keren dat twee personen gematcht worden?
			// todo geen meerdere relaties met zelfde mensen? (lovers? of ook by?)
			statement.execute();
		} catch (SQLException e) {
			System.out.println("Error RepoSQL make relation: " + e.getMessage()); //todo
			e.printStackTrace(); //todo
			throw new DbException(e.getMessage(), e);
		}
	}
/*
	public RelationObj getRelation(String a, String b, String c) {
		if (a == null || b == null || c == null) throw new DbException("Person is null");
		RelationObj relation = null;
		try (Connection connection = DriverManager.getConnection(url, properties);
			 PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE email ILIKE ? OR email ILIKE ? OR email ILIKE ?;")) {
			statement.setString(1, c);
			statement.setString(2, a);
			statement.setString(3, b);
			ResultSet resultSet = statement.executeQuery();
			String emailA = resultSet.getString("email"); //todo get by first and last name
			resultSet.next();
			String emailB = resultSet.getString("email");
			resultSet.next();
			String emailC = resultSet.getString("email");
			//relation = new RelationObj(get(emailA), get(emailB), get(emailC));
		} catch (SQLException e) {
			e.printStackTrace(); //todo
			throw new DbException(e.getMessage(), e);
		}
		System.out.println("relatie: " + relation.toString()); //todo
		return relation;
	}*/

	public List<RelationObj> getRelations() {
		//System.out.println("SQL Get relations"); //Todo
		List<RelationObj> relations = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(url, properties);
			 Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery("SELECT * FROM relation");
			while (resultSet.next()) {
				RelationObj relation = new RelationObj();
				relation.setChoser(resultSet.getString("choser"));
				relation.setLover_1(resultSet.getString("lover_1"));
				relation.setLover_2(resultSet.getString("lover_2"));
				relations.add(relation);
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
		return relations;
	}

	public List<RelationObj> getRelations(String name) {
		//System.out.println("SQL Get relations String"); //Todo
		List<RelationObj> relations = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(url, properties);
			 PreparedStatement statement = connection.prepareStatement("SELECT * FROM relation where lover_1 ILIKE ? OR lover_2 ILIKE ?;")) {
			statement.setString(1, name);
			statement.setString(2, name);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				RelationObj relation = new RelationObj();
				relation.setLover_1(resultSet.getString("lover_1"));
				relation.setLover_2(resultSet.getString("lover_2"));
				relations.add(relation);
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
		return relations;
	}

	public List<Message> getChats(String name) {
		//System.out.println("SQL Get chats from " + name); //Todo
		List<Message> messages = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection(url, properties);
			 PreparedStatement statement = connection.prepareStatement("SELECT * FROM message where sender ILIKE ? OR receiver ILIKE ?;")) {
			statement.setString(1, name);
			statement.setString(2, name);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Message message = new Message();
				message.setBericht(resultSet.getString("message"));
				message.setOntvanger(resultSet.getString("receiver"));
				message.setZender(resultSet.getString("sender"));
				messages.add(message);
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
		return messages;
	}

	public void sendChat(String bericht, String ontvanger, String zender) {
		//System.out.println("SQL send chat"); //Todo
		try (Connection connection = DriverManager.getConnection(url, properties);
			 PreparedStatement statement = connection.prepareStatement("INSERT INTO message (message, sender, receiver) values (?, ?, ?);")) {
			statement.setString(1, bericht);
			statement.setString(2, zender);
			statement.setString(3, ontvanger); //todo extra rij aantal voor aantal keren dat twee personen gematcht worden?
			// todo geen meerdere relaties met zelfde mensen? (lovers? of ook by?)
			statement.execute();
		} catch (SQLException e) {
			System.out.println("Error RepoSQL make relation: " + e.getMessage()); //todo
			e.printStackTrace(); //todo
			throw new DbException(e.getMessage(), e);
		}
	}


	public List<Person> getFriends(String email) {
		//System.out.println("SQL Get friends"); //Todo
		try (Connection connection = DriverManager.getConnection(url, properties);
			 PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE email NOT ILIKE ?;")) {
			statement.setString(1, email);
			ResultSet resultSet = statement.executeQuery();
			return createListFromResultset(resultSet);
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}

	public void editPassword(String email, String password) {
		System.out.println("Edit password sql van " + email + " new: " + password); //todo
		try (Connection connection = DriverManager.getConnection(url, properties);
			 PreparedStatement statement = connection.prepareStatement("UPDATE person SET password = ? WHERE email = ?;")) {
			statement.setString(1, password);
			statement.setString(2, email);
			statement.execute();
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}
}
