package com.studentdating.studentdating.controller;

import com.studentdating.studentdating.model.dto.UserDTO;
import com.studentdating.studentdating.model.service.UserService;
import org.springframework.web.bind.annotation.*;

//import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {
    private final UserService service;

    public UserRestController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    @ResponseBody
    public List<UserDTO> getTasks() {
        return service.getUsers();
    }

    @PostMapping("/users")
    public UserDTO createNewTask(@RequestBody UserDTO user) {// @Valid
        service.addPerson(user);
        return user;
    }
}
