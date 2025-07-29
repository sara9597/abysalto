package hr.abysalto.hiring.mid.service.impl;

import hr.abysalto.hiring.mid.client.DummyJsonApiClient;
import hr.abysalto.hiring.mid.dto.ProductResponse;
import hr.abysalto.hiring.mid.model.Product;
import hr.abysalto.hiring.mid.service.ProductService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    
    private final DummyJsonApiClient dummyJsonApiClient;
    
    public ProductServiceImpl(DummyJsonApiClient dummyJsonApiClient) {
        this.dummyJsonApiClient = dummyJsonApiClient;
    }
    
    @Cacheable("products")
    public ProductResponse getAllProducts(int skip, int limit) {
        return dummyJsonApiClient.getProducts(skip, limit);
    }
    
    @Cacheable("product")
    public Optional<Product> getProductById(Integer id) {
        try {
            Product product = dummyJsonApiClient.getProductById(id);
            return Optional.of(product);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    
    @Cacheable("categories")
    public List<String> getCategories() {
        return dummyJsonApiClient.getCategories();
    }
    
    @Cacheable("productsByCategory")
    public ProductResponse getProductsByCategory(String category, int skip, int limit) {
        return dummyJsonApiClient.getProductsByCategory(category, skip, limit);
    }
    
    @Cacheable("searchProducts")
    public ProductResponse searchProducts(String query, int skip, int limit) {
        return dummyJsonApiClient.searchProducts(query, skip, limit);
    }
} 