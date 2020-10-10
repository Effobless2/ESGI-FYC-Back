package fr.esgi.fyc.controllers;

import fr.esgi.fyc.DTO.UserCreateDTO;
import fr.esgi.fyc.DTO.UserGetDTO;
import fr.esgi.fyc.domain.model.User;
import fr.esgi.fyc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/user")
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

    @GetMapping("/all")
    public ResponseEntity<List<UserGetDTO>> getAll(HttpServletRequest request){
        List<UserGetDTO> users = userService.getAll();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserGetDTO> getById(HttpServletRequest request, @PathVariable("id") int id){
        UserGetDTO user = userService.getById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }
}
