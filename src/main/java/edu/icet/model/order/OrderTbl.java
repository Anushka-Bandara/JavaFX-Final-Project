package edu.icet.model.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderTbl {
    private String orderId;
    private Date orderDate;
    private String paymentType;
    private Double total;

}
