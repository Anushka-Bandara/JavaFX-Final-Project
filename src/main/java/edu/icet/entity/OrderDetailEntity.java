package edu.icet.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class OrderDetailEntity {
    @Id
    private String orderId;
    private String productId;
    private String size;
    private String category;
    private Double price;
    private Integer qty;
}
