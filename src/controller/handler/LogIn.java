package controller.handler;

import controller.SyncHandler;
import domain.model.Person;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class LogIn extends SyncHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
		List<String> errors = new ArrayList<>();
// mag geen twee keer zelfde email voor verschillende accounts todo
		//System.out.println("Login"); //todo
		String email = request.getParameter("email");
		if (email == null || email.trim().isEmpty()) errors.add("No email given");

		String password = request.getParameter("password");
		if (password == null || password.trim().isEmpty()) errors.add("No password given");
		//System.out.println("Tryy size: " + errors.size()); //Todo
		if (errors.size() == 0) {
			// hash password
			String pass = getPersonService().hashPassword(password);
			Person person = getPersonService().getAuthenticatedUser(email, pass);
			if (person != null) {
				createSession(person, request);
				request.setAttribute("persons", getPersonService().getPersons());
				request.setAttribute("welcome", person.getFirstName());//todo not blue?
			}
			else errors.add("Invalid credentials");
		}
		else System.out.println("errors login: " + errors.toString()); //Todo
		request.setAttribute("errors", errors);
		return "index.jsp";
	}
	
	private void createSession(Person person, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("user", person);
		//System.out.println("LogIn user in session: " + person.getFirstName()); //todo
	}
}
