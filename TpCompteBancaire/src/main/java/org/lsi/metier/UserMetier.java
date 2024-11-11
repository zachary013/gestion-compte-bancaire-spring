package org.lsi.metier;

import org.lsi.dto.UserRequest;
import org.lsi.dto.UserResponse;
import org.lsi.entities.User;

import java.util.List;

public interface UserMetier {
    List<UserResponse> getAllUser();
    UserResponse getUser(UserRequest userRequest);
    UserResponse addUser(UserRequest userRequest); // REGISTER
    UserResponse updateUser(UserRequest userRequest);
}
