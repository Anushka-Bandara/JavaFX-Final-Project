package edu.icet.model.orderDetail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DetailViewTable {
    String productId;
    String size;
    String category;
    Double price;
    Integer qty;

}
