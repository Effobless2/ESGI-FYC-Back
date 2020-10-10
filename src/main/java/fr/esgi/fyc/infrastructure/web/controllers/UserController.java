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
    public ResponseEntity<?> create(HttpServletRequest request, @Validated UserCreateDTO user){

        //TODO:Verifier qu'aucun n'utilisateur existe déjà avec cette email (champ Unique)

        fr.esgi.fyc.domain.model.User userModel = new fr.esgi.fyc.domain.model.User(0,
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
        List<UserGetDTO> userDTOs = new ArrayList<>();

        for (User user: userModels) {
            userDTOs.add(new UserGetDTO(user));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserGetDTO> getById(HttpServletRequest request, @PathVariable("id") int id){
        UserGetDTO user = new UserGetDTO(userService.getById(id));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }
}
