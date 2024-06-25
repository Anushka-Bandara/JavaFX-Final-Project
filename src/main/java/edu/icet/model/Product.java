package edu.icet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.awt.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    private String productId;
    private String name;
    private String size;
    private String category;
    private Double price;
    private Integer qty;
    private String imagePath;
}
