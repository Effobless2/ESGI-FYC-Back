package fr.esgi.fyc.infrastructure.web.controllers;

import fr.esgi.fyc.domain.model.User;
import fr.esgi.fyc.infrastructure.web.DTO.UserCreateDTO;
import fr.esgi.fyc.infrastructure.web.DTO.UserGetDTO;
import fr.esgi.fyc.domain.services.UserService;
import fr.esgi.fyc.infrastructure.web.Security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

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

          user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

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
            String token = jwtUtils.resolveToken(request);
            Boolean tokenIsvalidated = jwtUtils.validateToken(token);

            if(!tokenIsvalidated){
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("ERROR : UNAUTHORIZED");
            }

            int userId = jwtUtils.getUserId(token);
            user.setId(userId);
            int nbUserModified = userService.updateUser(user);

            if( nbUserModified == 0){
                return ResponseEntity
                        .status(HttpStatus.NOT_MODIFIED)
                        .body("ERROR : User not modified");
            }

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userId);

        } catch (Exception e) {
          return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("ERROR : " + e.getMessage());
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<?> delete(HttpServletRequest request){
        try{
            String token = jwtUtils.resolveToken(request);
            Boolean tokenIsvalidated = jwtUtils.validateToken(token);

            if(!tokenIsvalidated){
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("ERROR : UNAUTHORIZED");
            }

            int userId = jwtUtils.getUserId(token);
            User user = userService.getById(userId);

            int nbUserDelete = userService.deleteUser(user);

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
