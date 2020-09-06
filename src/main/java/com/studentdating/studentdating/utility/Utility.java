package com.studentdating.studentdating.utility;

import com.studentdating.studentdating.model.dto.UserDTO;
import com.studentdating.studentdating.model.exception.NotAuthorizedException;

import javax.servlet.http.HttpSession;

public class Utility {
    public static void checkRole(HttpSession session, String role) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user != null && user.getRole().equals(role)) {
            System.out.println("utility role: " + user.toString()); //todo
        }
        else throw new NotAuthorizedException();
    }
}
