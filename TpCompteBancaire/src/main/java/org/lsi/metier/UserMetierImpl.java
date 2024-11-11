package org.lsi.metier;

import org.lsi.dao.EmployeRepository;
import org.lsi.dao.UserRepository;
import org.lsi.dto.UserRequest;
import org.lsi.dto.UserResponse;
import org.lsi.entities.Employe;
import org.lsi.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserMetierImpl implements UserMetier {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeRepository employeRepository;



    @Override
    public List<UserResponse> getAllUser() {
        return convertToDTO(userRepository.findAll());
    }

    @Override
    public UserResponse getUser(UserRequest userRequest) {
        User user = userRepository.findByEmail(userRequest.getEmail());
        if(user != null){
            return convertToDTO(user) ;
        }
        return null;
    }

    @Override
    public UserResponse addUser(UserRequest userRequest) {
        // Check if the email already exists
        if (userRepository.findByEmail(userRequest.getEmail()) != null) {
            throw new IllegalArgumentException("User with this email already exists.");
        }

        // Create a new user
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setRole(userRequest.getRole() != null ? userRequest.getRole() : "ROLE_EMP");

        // Handle the employe lookup
        if (userRequest.getCodeEmploye() != null) {
            Employe employe = employeRepository.findById(userRequest.getCodeEmploye())
                    .orElseThrow(() -> new IllegalArgumentException("Employe not found with ID: " + userRequest.getCodeEmploye()));
            user.setEmploye(employe);
        } else {
            throw new IllegalArgumentException("Employe code is required.");
        }

        // Save user
        try {
            User savedUser = userRepository.save(user);
            return convertToDTO(savedUser);
        } catch (Exception e) {
            // Log the exception for further inspection and throw a custom exception
            throw new RuntimeException("An error occurred while saving the user.");
        }
    }

    @Override
    public UserResponse updateUser(UserRequest userRequest) {
        User user = userRepository.findByEmail(userRequest.getEmail());
        if(user != null){

            if(userRequest.getEmail() != null){
                user.setEmail(userRequest.getEmail());
            }
            if(userRequest.getPassword() != null){
                user.setPassword(userRequest.getPassword());
            }
            if(userRequest.getRole() != null){
                user.setRole(userRequest.getRole());
            }
            User savedUser = userRepository.save(user);
            return convertToDTO(savedUser);
        }
        return null;
    }


    private UserResponse convertToDTO(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setPassword(user.getPassword());
        userResponse.setRole(user.getRole());
        userResponse.setEmploye(user.getEmploye());
        return userResponse ;
    }

    private List<UserResponse> convertToDTO(List<User> users) {
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


}