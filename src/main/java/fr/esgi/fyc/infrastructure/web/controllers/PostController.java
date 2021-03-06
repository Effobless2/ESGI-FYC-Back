package fr.esgi.fyc.infrastructure.web.controllers;

import fr.esgi.fyc.domain.model.Post;
import fr.esgi.fyc.domain.model.User;
import fr.esgi.fyc.domain.services.PostService;
import fr.esgi.fyc.domain.services.UserService;
import fr.esgi.fyc.infrastructure.web.Security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/posts")
public class PostController {

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  UserService userService;

  @Autowired
  PostService postService;

  @PostMapping("/")
  public ResponseEntity<?> create(HttpServletRequest request, @RequestBody Post post){
    try{

      String token = jwtUtils.resolveToken(request);
      Boolean tokenIsvalidated = jwtUtils.validateToken(token);

      if(!tokenIsvalidated){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("ERROR : UNAUTHORIZED");
      }

      int userId = jwtUtils.getUserId(token);

      User userModel = userService.getById(userId);

      if(userModel == null) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("ERROR : User not found");
      }

      post.setUser(userModel);
      post.setId(0);
      Date currentDate = new Date(System.currentTimeMillis());
      post.setCreatedAt(currentDate);

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

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(HttpServletRequest request, @PathVariable("id") int id){
    try{
      Post postModel = postService.getById(id);

      if(postModel == null) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("ERROR : Post not found");
      }

      return ResponseEntity
              .status(HttpStatus.OK)
              .body(postModel);

    } catch (Exception e) {
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("ERROR : " + e.getMessage());
    }
  }

  @GetMapping("/")
  public ResponseEntity<?> getAll(HttpServletRequest request){
    try{

      List<Post> posts = postService.getAll();

      return ResponseEntity
              .status(HttpStatus.OK)
              .body(posts);

    } catch (Exception e) {
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("ERROR : " + e.getMessage());
    }
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<?> getByUser(HttpServletRequest request, @PathVariable("userId") int userId){
    try{

      User userModel = userService.getById(userId);

      if(userModel == null) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("ERROR : User not found");
      }

      List<Post> posts = postService.getByUser(userModel);

      return ResponseEntity
              .status(HttpStatus.OK)
              .body(posts);

    } catch (Exception e) {
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("ERROR : " + e.getMessage());
    }
  }

  @PutMapping("/")
  public ResponseEntity<?> update(HttpServletRequest request, @RequestBody Post post){
    try{
      String token = jwtUtils.resolveToken(request);
      Boolean tokenIsvalidated = jwtUtils.validateToken(token);

      if(!tokenIsvalidated){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("ERROR : UNAUTHORIZED");
      }

      Post postInDB = postService.getById(post.getId());

      if(postInDB == null){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("ERROR : Post not found");
      }

      int userId = jwtUtils.getUserId(token);
      Boolean idIsMatche = postInDB.getUser().getId() == userId;

      if(!idIsMatche){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("ERROR : You are not the author of the post");
      }

      int nbPostModified = postService.updatePost(post);

      if(nbPostModified == 0){
        return ResponseEntity
                .status(HttpStatus.NOT_MODIFIED)
                .body("ERROR : Post not modified");
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

  @DeleteMapping("/{postId}")
  public ResponseEntity<?> delete(HttpServletRequest request, @PathVariable("postId") int postId){
    try{
      String token = jwtUtils.resolveToken(request);
      Boolean tokenIsvalidated = jwtUtils.validateToken(token);

      if(!tokenIsvalidated){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("ERROR : UNAUTHORIZED");
      }

      int userId = jwtUtils.getUserId(token);
      User userModel = userService.getById(userId);

      Post post = postService.getById(postId);
      post.setUser(userModel);

      if(post == null){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("ERROR : Post not found");
      }

      Boolean idIsMatche = userModel.getId() == post.getUser().getId();

      if(!idIsMatche){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("ERROR : You are not the author of the post");
      }

      int nbPostDelete = postService.deletePost(post.getId());

      if( nbPostDelete == 0){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("ERROR : Post not deleted");
      }

      return ResponseEntity
              .status(HttpStatus.OK)
              .body("SUCCES : Post delete");

    } catch (Exception e) {
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("ERROR : " + e.getMessage());
    }

  }
}
