package fr.esgi.fyc.infrastructure.web.controllers;

import fr.esgi.fyc.domain.model.User;
import fr.esgi.fyc.domain.services.UserService;
import fr.esgi.fyc.infrastructure.web.DTO.AuthDTO;
import fr.esgi.fyc.infrastructure.web.Security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  UserService userService;

  @PostMapping("/")
  public ResponseEntity<?> create(HttpServletRequest request, @RequestBody AuthDTO authDTO){
    try {
      User user = userService.getByEmail(authDTO.getEmail());
      if(user == null){
        return ResponseEntity
          .status(HttpStatus.UNAUTHORIZED)
          .body("Error : User not found");
      }

      if(user.getIsBlocked()){
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body("Error : User blocked");
      }

      Boolean passwordIsMatche = bCryptPasswordEncoder.matches(authDTO.getPassword(), user.getPassword());

      if (!passwordIsMatche){

        if(user.getNbTestLogin() >= 2){

          user.setNbTestLogin(user.getNbTestLogin() + 1);
          user.setIsBlocked(true);
          userService.updateUser(user);

          return ResponseEntity
                  .status(HttpStatus.FORBIDDEN)
                  .body("Error : User blocked");
        }

        user.setNbTestLogin(user.getNbTestLogin() + 1);
        userService.updateUser(user);


        return ResponseEntity
          .status(HttpStatus.UNAUTHORIZED)
          .body("Error : Incorrect password");
      }

      if(user.getNbTestLogin() > 0){
        user.setNbTestLogin(0);
        userService.updateUser(user);
      }

      String token = jwtUtils.generateToken(user);

      return ResponseEntity
        .status(HttpStatus.OK)
        .body(token);

      } catch (Exception e) {

        return ResponseEntity
          .status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("ERROR : " + e.getMessage());

      }
  }

  @PutMapping("/pwd")
  public ResponseEntity<?> updatePwd(HttpServletRequest request, @RequestBody AuthDTO authDTO) {
    try {
      String token = jwtUtils.resolveToken(request);
      Boolean tokenIsValid = jwtUtils.validateToken(token);

      if(!tokenIsValid){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("ERROR: UNAUTHORIZED");
      }

      int userId = jwtUtils.getUserId(token);

      if (userService.getById(userId) == null) {
        return ResponseEntity
          .status(HttpStatus.NOT_FOUND)
          .body("ERROR : User not found");
      }

      authDTO.setId(userId);
      authDTO.setPassword(bCryptPasswordEncoder.encode(authDTO.getPassword()));

      int nbUserModified = userService.updateUserPassword(authDTO);

      if (nbUserModified == 0) {
        return ResponseEntity
          .status(HttpStatus.NOT_MODIFIED)
          .body("ERROR : User password not modified");
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

}
