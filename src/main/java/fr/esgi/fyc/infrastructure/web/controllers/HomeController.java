package fr.esgi.fyc.infrastructure.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
@CrossOrigin("*")
public class HomeController {

    @GetMapping
    public ResponseEntity<int[]> Get() {
        return ResponseEntity
                .status(200)
                .body(new int[]{1, 2, 3});
    }
}
