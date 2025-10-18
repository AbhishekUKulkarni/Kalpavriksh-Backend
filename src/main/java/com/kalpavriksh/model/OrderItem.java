package com.kalpavriksh.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {
    private String plantId;
    private String plantName;
    private int quantity;
    private double price; // price per item
}
