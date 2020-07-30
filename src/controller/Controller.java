package controller;

import domain.service.PersonService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private PersonService personService;
	private ControllerFactory controllerFactory = new ControllerFactory();

	public Controller() {
		super();
	}

	public void init() throws ServletException {
		super.init();
		ServletContext context = getServletContext();

		Properties properties = new Properties();
		Enumeration<String> parameterNames = context.getInitParameterNames();
		while (parameterNames.hasMoreElements()) {
			String propertyName = parameterNames.nextElement();
			properties.setProperty(propertyName, context.getInitParameter(propertyName));
		}
		personService = new PersonService(properties);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String destination = "index.jsp";
		RequestHandler handler = null;
		if (action != null) {
			try {
				System.out.println("Controller ac: " + action); //Todo
				handler = controllerFactory.getController(action, personService);
				destination = handler.handleRequest(request, response);
			}
			catch (Exception exc) {//todo catch what?
				System.out.println("Controller exception: " + exc.getMessage()); //todo
				exc.printStackTrace(); //todo
				List<String> errors = new ArrayList<>();
				errors.add(exc.getMessage());
				request.setAttribute("errors", errors);
				destination = "index.jsp";
			}
		}
		if (handler instanceof AsyncHandler) {
			System.out.println("Async Controller: " + destination); //todo
			response.setContentType("text/json");
			response.getWriter().write(destination);
		}
		else {
			RequestDispatcher view = request.getRequestDispatcher(destination);
			view.forward(request, response);
		}
	}
}
