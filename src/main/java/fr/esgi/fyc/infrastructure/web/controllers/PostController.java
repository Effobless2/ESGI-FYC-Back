package fr.esgi.fyc.infrastructure.web.controllers;

import fr.esgi.fyc.domain.model.Post;
import fr.esgi.fyc.domain.services.PostService;
import fr.esgi.fyc.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/posts")
public class PostController {

  @Autowired
  UserService userService;

  @Autowired
  PostService postService;

    @PostMapping("/")
    public ResponseEntity<?> create(HttpServletRequest request, @RequestBody Post post){
      try{

        //TODO: test authentification User via JWT

        post.setId(0);

        Date currentDate = new Date(System.currentTimeMillis());
        post.setCreatedAt(currentDate);

        if(userService.getById(post.getId()) != null){
          return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body("ERROR : UNAUTHORIZED");
        }

        int id = postService.add(post);

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
}
