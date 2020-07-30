package controller.handler;

import controller.SyncHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class Profiles extends SyncHandler {
    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession().getAttribute("user") == null) {
            ArrayList<String> errors = new ArrayList<>();
            errors.add("Unauthorized");
            request.setAttribute("errors", errors); //todo error
            return "index.jsp";
        }
        request.setAttribute("list", getPersonService().getPersons());
        return "everybody.jsp";
    }
}
