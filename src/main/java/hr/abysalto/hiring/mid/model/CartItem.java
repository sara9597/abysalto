package hr.abysalto.hiring.mid.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Integer cartItemId;
    private Integer userId;
    private Integer productId;
    private Integer quantity;
    private Product product;
} 