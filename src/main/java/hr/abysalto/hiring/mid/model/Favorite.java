package hr.abysalto.hiring.mid.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {
    private Integer favoriteId;
    private Integer userId;
    private Integer productId;
    private Product product;
} 