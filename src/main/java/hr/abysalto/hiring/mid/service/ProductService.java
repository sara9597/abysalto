package hr.abysalto.hiring.mid.service;

import hr.abysalto.hiring.mid.dto.ProductResponse;
import hr.abysalto.hiring.mid.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    ProductResponse getAllProducts(int skip, int limit);

    Optional<Product> getProductById(Integer id);
    
    List<String> getCategories();
    
    ProductResponse getProductsByCategory(String category, int skip, int limit);
    
    ProductResponse searchProducts(String query, int skip, int limit);
} 