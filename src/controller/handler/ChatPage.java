package controller.handler;

import controller.SyncHandler;
import domain.model.Person;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class ChatPage extends SyncHandler {
    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {

        if (request.getSession().getAttribute("user") != null) {
            Person p = (Person) request.getSession().getAttribute("user");
            request.setAttribute("list", getPersonService().getFriends(p.getEmail()));
            return "chatPage.jsp";
        }
        else {
            ArrayList<String> errors = new ArrayList<>();
            errors.add("U hebt geen toegang tot deze pagina. Gelieve in te loggen.");
            request.setAttribute("errors", errors);
            return "index.jsp";
        }
    }
}
