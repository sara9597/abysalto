package hr.abysalto.hiring.mid.dto;

import hr.abysalto.hiring.mid.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private List<Product> products;
    private Integer total;
    private Integer skip;
    private Integer limit;
} 