package com.kalpavriksh.service;

import com.kalpavriksh.model.*;
import com.kalpavriksh.repository.OrderRepository;
import com.kalpavriksh.repository.PlantRepository;
import com.kalpavriksh.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepo;
    private final UserRepository userRepo;
    private final PlantRepository plantRepo;

    public OrderService(OrderRepository orderRepo, UserRepository userRepo, PlantRepository plantRepo) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
        this.plantRepo = plantRepo;
    }

    public Order placeOrder(String userId) {
        Optional<User> userOpt = userRepo.findById(userId);
        if (userOpt.isEmpty()) return null;

        User user = userOpt.get();
        List<OrderItem> orderItems = new ArrayList<>();
        double totalPrice = 0;

        // Check stock & prepare order items
        for (CartItem cartItem : user.getCart()) {
            Optional<Plant> plantOpt = plantRepo.findById(cartItem.getPlantId());
            if (plantOpt.isEmpty()) continue;
            Plant plant = plantOpt.get();

            if (plant.getQuantity() < cartItem.getQuantity()) {
                throw new RuntimeException("Not enough stock for " + plant.getName());
            }

            // Decrement stock
            plant.setQuantity(plant.getQuantity() - cartItem.getQuantity());
            plantRepo.save(plant);

            // Add to order
            OrderItem orderItem = OrderItem.builder()
                    .plantId(plant.getId())
                    .plantName(plant.getName())
                    .quantity(cartItem.getQuantity())
                    .price(plant.getPrice())
                    .build();
            orderItems.add(orderItem);

            totalPrice += plant.getPrice() * cartItem.getQuantity();
        }

        if (orderItems.isEmpty()) return null;

        // Clear user's cart
        user.getCart().clear();
        userRepo.save(user);

        // Save order
        Order order = Order.builder()
                .userId(userId)
                .items(orderItems)
                .totalPrice(totalPrice)
                .status("PLACED")
                .createdAt(Instant.now())
                .build();

        return orderRepo.save(order);
    }

    public List<Order> getUserOrders(String userId) {
        return orderRepo.findByUserId(userId);
    }
}
