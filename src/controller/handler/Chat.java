package controller.handler;

import controller.AsyncHandler;
import domain.model.Message;
import domain.model.Person;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class Chat extends AsyncHandler {

    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        Person person = null;

        if (request.getSession().getAttribute("user") != null) person = (Person) request.getSession().getAttribute("user");
        String zender = request.getParameter("zender");//todo weg
        String bericht = request.getParameter("bericht");//todo
        String ontvanger = request.getParameter("ontvanger");//todo
        //if (bericht != null) System.out.println("Chat " + bericht + " van " + zender + " naar " + ontvanger); //todo
        if (bericht != null) getPersonService().addChat(bericht, ontvanger, zender);//todo
        //System.out.println("Chat alle chats: " + getPersonService().getChats(zender)); //todo
        // todo get chats where i am sender or receiver
        //if (request.getParameter("zender") != null) getPersonService().addChat(request.getParameter("bericht"), request.getParameter("ontvanger"), request.getParameter("zender"));
        String json = chatsToJSON(person);
        //System.out.println("Chat json: " + json);
        return json;
//        return chatsToJSON(person);
    }

    private String chatsToJSON(Person person) {
        //System.out.println("cat to json van " + person.getFirstName() + " " + person.getLastName()); //todo
        String json = "{\"chats\":[";

        List<Message> messages = getPersonService().getChats(person.getFirstName() + " " + person.getLastName());
        for (Message m: messages) {
            json += m.toString() + ",";
        }
        // laatste komma uit de string verwijderen
        if (messages.size() != 0) json = json.substring(0, json.length() - 1);
        return json + "]}";
    }
}
