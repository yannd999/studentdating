package controller.handler;

import controller.SyncHandler;
import domain.model.Person;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class EditPassword extends SyncHandler {
    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ArrayList<String> errors = new ArrayList<>();
        Person person;
        // not logged in
        if (request.getSession().getAttribute("user") == null) {
            errors.add("Unauthorized");
            request.setAttribute("errors", errors); //todo error
            return "editprofile.jsp";
        }
        else person = (Person) request.getSession().getAttribute("user");

        String oldpass = request.getParameter("oldpassword");
        String newpass = request.getParameter("newpassword");
        String repnewpass = request.getParameter("repnewpassword");

        // naar pagina
        if (oldpass == null && newpass == null && repnewpass == null) return "editprofile.jsp";
        // form ingevuld
        else {
            if (oldpass == null || newpass == null || repnewpass == null) errors.add("Invalid fields");
            else if (!(getPersonService().hashPassword(oldpass)).equals(person.getPassword()))
                errors.add("Old password is incorrect");
            else if (!newpass.equals(repnewpass)) errors.add("New passwords do not match");
            else getPersonService().editPassword(person, newpass);
        }
        request.setAttribute("errors", errors); //todo error
        if (errors.size() == 0) return "profile.jsp";
        else return "editprofile.jsp";
    }
}
