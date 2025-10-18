package com.kalpavriksh.controller;

import com.kalpavriksh.model.CartItem;
import com.kalpavriksh.model.User;
import com.kalpavriksh.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")

public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = service.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 if no users
        }
        return ResponseEntity.ok(users);
    }
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return service.createUser(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        return service.getUser(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/cart")
    public ResponseEntity<User> addToCart(@PathVariable String id, @RequestBody CartItem item) {
        User updated = service.addToCart(id, item);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}/cart/{plantId}")
    public ResponseEntity<User> removeFromCart(@PathVariable String id, @PathVariable String plantId) {
        User updated = service.removeFromCart(id, plantId);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}/cart")
    public ResponseEntity<User> clearCart(@PathVariable String id) {
        User updated = service.clearCart(id);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }
}
