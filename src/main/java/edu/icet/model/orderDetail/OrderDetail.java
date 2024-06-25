package edu.icet.model.orderDetail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetail {
    private String orderId;
    private String productId;
    private String size;
    private String category;
    private Double price;
    private Integer qty;
}
