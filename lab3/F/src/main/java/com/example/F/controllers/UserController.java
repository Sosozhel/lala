package com.example.F.controllers;

import com.example.F.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    @PutMapping("/{userId}/roles")
    public ResponseEntity<?> updateUserRoles(@PathVariable Long userId, @RequestBody Set<String> roleNames) {
        try {
            return ResponseEntity.ok(userService.updateUserRoles(userId, roleNames));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}