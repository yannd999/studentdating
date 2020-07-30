package controller.handler;

import controller.SyncHandler;
import domain.model.Person;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class Profile extends SyncHandler {
    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession().getAttribute("user") == null) {
            ArrayList<String> errors = new ArrayList<>();
            errors.add("Unauthorized");
            request.setAttribute("errors", errors); //todo error
            return "index.jsp";
        }
        // relaties opvragen en in session steken
        else {
            Person me = (Person) request.getSession().getAttribute("user");
            me.setRelations(getPersonService().getRelations(me.getFirstName() + " " + me.getLastName()));
            //me.setChats(getPersonService().getChats(me.getFirstName() + " " + me.getLastName()));
            request.getSession().setAttribute("user", me);
        }
        //System.out.println("profile"); //todo
        //todo bio?
        return "profile.jsp";
    }
}
