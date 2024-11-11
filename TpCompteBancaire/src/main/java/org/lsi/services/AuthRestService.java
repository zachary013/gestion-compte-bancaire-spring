package org.lsi.services;


import org.lsi.dto.UserRequest;
import org.lsi.dto.UserResponse;
import org.lsi.entities.User;
import org.lsi.metier.UserMetierImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthRestService {

    @Autowired
    private UserMetierImpl userMetier ;

    @PostMapping("/login")
    public UserResponse login(@RequestBody UserRequest userRequest) {
        return userMetier.getUser(userRequest) ;
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody UserRequest userRequest) {
        return userMetier.addUser(userRequest);
    }

    @PutMapping("/update")
    public UserResponse update(@RequestBody UserRequest userRequest) {
        return userMetier.updateUser(userRequest) ;
    }

    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        return userMetier.getAllUser() ;
    }


}
