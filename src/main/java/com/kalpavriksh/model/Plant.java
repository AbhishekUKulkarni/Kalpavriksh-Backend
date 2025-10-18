package com.kalpavriksh.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "plants")
public class Plant {
    @Id
    private String id;

    private String name;
    private String botanicalName;
    private String description;
    private double price;
    private int quantity;
}
