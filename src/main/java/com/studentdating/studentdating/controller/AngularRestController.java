package com.studentdating.studentdating.controller;

import com.studentdating.studentdating.model.dto.UserDTO;
import com.studentdating.studentdating.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/users")
public class AngularRestController {

    @Autowired
    private final UserService service;

   /* @Autowired
    private final ChatService chatService;
*/
    public AngularRestController(UserService service) {
        this.service = service;
        //chatService = new ChatService();
    }

    @ResponseBody
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getAllRegistredPersons")
    public List<UserDTO> getRegistredPersons() {
        List<UserDTO> a = new ArrayList<>();
        a = service.getUsers();
        return a;
    }

/*    @PostMapping("/editUser/{username}")
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:4200")
    public void changeUser(@PathVariable String username, @RequestBody Person user) {
        this.service.updateUsernameOfPerson(user, user.getFirstName());
        System.out.println("Update " + username + ": " + user.toString());
    }*/
}
