package com.kalpavriksh.service;

import com.kalpavriksh.model.CartItem;
import com.kalpavriksh.model.User;
import com.kalpavriksh.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User createUser(User user) {
        return repo.save(user);
    }
    public List<User> getAllUsers( ) {
        return repo.findAll();
    }
    public Optional<User> getUser(String id) {
        return repo.findById(id);
    }

    public Optional<User> getByEmail(String email) {
        return repo.findByEmail(email);
    }

    public User addToCart(String userId, CartItem item) {
        return repo.findById(userId).map(user -> {
            List<CartItem> cart = user.getCart();
            boolean exists = false;
            for (CartItem c : cart) {
                if (c.getPlantId().equals(item.getPlantId())) {
                    c.setQuantity(c.getQuantity() + item.getQuantity());
                    exists = true;
                    break;
                }
            }
            if (!exists) cart.add(item);
            return repo.save(user);
        }).orElse(null);
    }

    public User removeFromCart(String userId, String plantId) {
        return repo.findById(userId).map(user -> {
            user.getCart().removeIf(item -> item.getPlantId().equals(plantId));
            return repo.save(user);
        }).orElse(null);
    }

    public User clearCart(String userId) {
        return repo.findById(userId).map(user -> {
            user.getCart().clear();
            return repo.save(user);
        }).orElse(null);
    }
}
