package fr.esgi.fyc.infrastructure.web.controllers;

import fr.esgi.fyc.domain.model.User;
import fr.esgi.fyc.infrastructure.web.DTO.UserCreateDTO;
import fr.esgi.fyc.infrastructure.web.DTO.UserGetDTO;
import fr.esgi.fyc.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> create(HttpServletRequest request, @RequestBody UserCreateDTO user){

        if(userService.getByEmail(user.getEmail()) != null){
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("ERROR : Email already used");
        }

        User userModel = new User(0,
                user.getLogin(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole());
        userService.add(userModel);

        int idUserCreated = userService.getByEmail(user.getEmail()).getId();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(idUserCreated);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserGetDTO>> getAll(HttpServletRequest request){
        List<User> userModels = userService.getAll();
        List<UserGetDTO> users = new ArrayList<>();

        for (User user: userModels) {
            users.add(new UserGetDTO(user));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(HttpServletRequest request, @PathVariable("id") int id){
        User userModel = userService.getById(id);

        if(userModel == null) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("ERROR : User not found");
        }

        UserGetDTO user = new UserGetDTO(userModel);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }

    @PutMapping("/")
    public ResponseEntity<?> update(HttpServletRequest request, @RequestBody User user){

        //TODO: récuperer l'utilisateur à partir du JWT

        if(userService.getById(user.getId()) == null){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("ERROR : User not found");
        }

        int nbUserModified = userService.updateUser(user);

        if( nbUserModified == 0){
            return ResponseEntity
                    .status(HttpStatus.NOT_MODIFIED)
                    .body("ERROR : User not modified");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("SUCCES : User modified"); //TODO: retourner l'id à partir du JWT
    }

    @DeleteMapping("/")
    public ResponseEntity<?> delete(HttpServletRequest request, @RequestBody User user){

        //TODO: récuperer l'utilisateur à partir du JWT

        int nbUserDelete = userService.deleteUser(user);

        if( nbUserDelete == 0){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("ERROR : User not deleted");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("SUCCES : User delete");
    }

}
