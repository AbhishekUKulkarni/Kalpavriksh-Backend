package com.kalpavriksh.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {
    private String plantId;
    private int quantity;
}
