package controller.handler;

import controller.SyncHandler;
import domain.model.Person;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class RelationPage extends SyncHandler {
    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ArrayList<String> errors = new ArrayList<>();
        if (request.getSession().getAttribute("user") == null) {
            errors.add("Unauthorized");
            request.setAttribute("errors", errors); //todo error
            return "index.jsp";
        }
        else if (!((Person)request.getSession().getAttribute("user")).getEmail().equals("admin@gmail.com")) {
            errors.add("Unauthorized");
            request.setAttribute("errors", errors); //todo error
            return "index.jsp";
        }
        request.setAttribute("relations", getPersonService().getRelations());
        return "relaties.jsp";
        //todo only relations where im one of them
    }
}
