package edu.icet.model.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InventoryTbl2 {
    private String productId;
    private String name;
    private String category;
    private String size;
    private Integer qty;
}
