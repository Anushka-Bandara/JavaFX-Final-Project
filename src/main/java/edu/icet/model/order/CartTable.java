package edu.icet.model.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartTable {
    String userId;
    String productId;
    String size;
    String category;
    Double price;
    Integer qty;
    Double subtotal;

}
