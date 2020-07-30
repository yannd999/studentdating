package controller.handler;

import controller.AsyncHandler;
import controller.RequestHandler;
import domain.model.Message;
import domain.model.Person;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Registreer extends AsyncHandler {

    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            System.out.println("reader in registreer: " + request.getReader().readLine());//todo testing
            System.out.println("Registreer"); //todo
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String age = request.getParameter("age");
            String sex = request.getParameter("geslacht");
            String location = request.getParameter("location");
            String email = request.getParameter("email");
            String password = request.getParameter("rpassword");
            String reppassword = request.getParameter("rherpassword");
            System.out.println("Registratie: " + firstName + " " + lastName + ", " + age + sex + ", " + location + ", " + email + ", " + password); //todo weg
            if (!password.equals(reppassword)) System.out.println("Passwords different"); //todo
            Person nieuwLid = new Person(firstName, lastName, Integer.parseInt(age), sex, location, email, password); //todo password?
            getPersonService().addPerson(nieuwLid);
        } catch (IllegalArgumentException | NullPointerException x) {
            System.out.println("Error in registratie: " + x.getMessage());
            x.printStackTrace();
        }
        return "";
    }
}
