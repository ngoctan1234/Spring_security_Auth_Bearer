package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.responses.ApiResponse;
import com.example.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/list")
    public ResponseEntity<ApiResponse> index() {
        ApiResponse apiResponse=ApiResponse.builder()
                .data(userService.getAllUsers())
                .message("get all successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody User user) throws Exception {
        ApiResponse apiResponse=ApiResponse.builder()
                .data(userService.login(user.getUsername(), user.getPassword()))
                .message("login successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }
    @PostMapping("register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody User user, BindingResult result) throws Exception {
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage).toList();
            ApiResponse apiResponse = ApiResponse.builder()
                    .data(errors)
                    .message("Validation failed")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
            return ResponseEntity.badRequest().body(apiResponse);
        }
        User user1 = userService.createUser(user);
        ApiResponse apiResponse=ApiResponse.builder()
                .data(user1)
                .message("Insert successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }
}
