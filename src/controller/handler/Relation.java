package controller.handler;

import controller.AsyncHandler;
import domain.model.Person;
import domain.model.RelationObj;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class Relation extends AsyncHandler {
    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession().getAttribute("user") == null) {
            ArrayList<String> errors = new ArrayList<>();
            errors.add("Unauthorized");
            request.setAttribute("errors", errors); //todo error
            return "index.jsp";
        }
        String personA = request.getParameter("personA");
        String personB = request.getParameter("personB");
        Person myself = (Person) request.getSession().getAttribute("user");
        // todo stuur notificatie naar a en b
        // in table relation
        getPersonService().makeRelation(personA, personB, myself.getFirstName() + " " + myself.getLastName());
        // add relation to people
       /* String myName = myself.getFirstName() + " " + myself.getLastName();
        if (myName.equals(personA)) {
            RelationObj relation = new RelationObj(myName, personB);
            myself.addRelation(relation);
        }
        else if (myName.equals(personB)) {
            RelationObj relation = new RelationObj(myName, personA);
            myself.addRelation(relation);
        }*/
        return "niets"; //todo
    }
}
