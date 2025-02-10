package com.vietpq.JobHunter.controller;

import com.nimbusds.jose.util.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("Hello Pham Quoc Viet");
    }
}
