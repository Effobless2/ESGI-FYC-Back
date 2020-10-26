package fr.esgi.fyc.infrastructure.web.controllers;

import fr.esgi.fyc.domain.model.User;
import fr.esgi.fyc.infrastructure.web.DTO.UserCreateDTO;
import fr.esgi.fyc.infrastructure.web.DTO.UserGetDTO;
import fr.esgi.fyc.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> create(HttpServletRequest request, @RequestBody UserCreateDTO user){
        try{
          if(userService.getByEmail(user.getEmail()) != null){
              return ResponseEntity
                  .status(HttpStatus.CONFLICT)
                  .body("ERROR : Email already used");
          }

          User userModel = new User(0,
                  user.getPassword(),
                  user.getFirstName(),
                  user.getLastName(),
                  user.getEmail(),
                  user.getRole());
          userService.add(userModel);

          int id = userService.getByEmail(user.getEmail()).getId();

          if(id == -1){
            return ResponseEntity
              .status(HttpStatus.CONFLICT)
              .body("ERROR : NOT CREATE");
          }

          return ResponseEntity
                  .status(HttpStatus.CREATED)
                  .body(id);

        } catch (Exception e) {
          return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("ERROR : " + e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAll(HttpServletRequest request){
        try{
          List<User> userModels = userService.getAll();
          List<UserGetDTO> users = new ArrayList<>();

          for (User user: userModels) {
              users.add(new UserGetDTO(user));
          }

          return ResponseEntity
                  .status(HttpStatus.OK)
                  .body(users);

        } catch (Exception e) {
          return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("ERROR : " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(HttpServletRequest request, @PathVariable("id") int id){
        try{
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

        } catch (Exception e) {
          return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("ERROR : " + e.getMessage());
        }
    }

    @PutMapping("/")
    public ResponseEntity<?> update(HttpServletRequest request, @RequestBody User user){
        try{
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

        } catch (Exception e) {
          return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("ERROR : " + e.getMessage());
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> delete(@PathVariable int userId){
        try{
            User u = userService.getById(userId);
          //TODO: récuperer l'utilisateur à partir du JWT

            if (u == null) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("ERROR : User not deleted");
            }
          int nbUserDelete = userService.deleteUser(u);

          if( nbUserDelete == 0){
              return ResponseEntity
                      .status(HttpStatus.CONFLICT)
                      .body("ERROR : User not deleted");
          }

          return ResponseEntity
                  .status(HttpStatus.OK)
                  .body("SUCCES : User delete");
        } catch (Exception e) {
          return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("ERROR : " + e.getMessage());
        }
    }

}
