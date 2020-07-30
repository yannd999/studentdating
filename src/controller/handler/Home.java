package controller.handler;

import controller.SyncHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class Home extends SyncHandler {
    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession().getAttribute("user") == null) {
            ArrayList<String> errors = new ArrayList<>();
            errors.add("Unauthorized");
            request.setAttribute("errors", errors); //todo error
            return "index.jsp";
        }
        //System.out.println("Home"); //todo
        request.setAttribute("persons", getPersonService().getPersons());
        return "index.jsp";
    }
}
