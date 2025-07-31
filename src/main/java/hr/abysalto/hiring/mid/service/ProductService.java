package hr.abysalto.hiring.mid.service;

import hr.abysalto.hiring.mid.dto.ProductResponse;
import hr.abysalto.hiring.mid.model.Category;
import hr.abysalto.hiring.mid.model.Product;
import java.util.List;

public interface ProductService {

    ProductResponse getAllProducts(int skip, int limit, String sortBy, String sortOrder);

    Product getProductById(Integer id);
    
    List<Category> getCategories();

    ProductResponse getProductsByCategory(String category, int skip, int limit, String sortBy, String sortOrder);

    ProductResponse searchProducts(String query, int skip, int limit, String sortBy, String sortOrder);
} 