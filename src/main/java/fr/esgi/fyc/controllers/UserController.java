package fr.esgi.fyc.controllers;

import fr.esgi.fyc.DTO.UserCreateDTO;
import fr.esgi.fyc.domain.model.User;
import fr.esgi.fyc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> create(HttpServletRequest request, @Validated UserCreateDTO user){
        User userModel = new User(0,
                user.getLogin(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole());
        userService.add(userModel);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("SUCCESS : User create");
    }

    @GetMapping("/id")
    public ResponseEntity<User> getById(HttpServletRequest request, @Validated Integer id){

        User user = userService.getById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }
}
