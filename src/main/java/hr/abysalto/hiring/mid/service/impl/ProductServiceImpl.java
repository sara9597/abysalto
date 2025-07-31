package hr.abysalto.hiring.mid.service.impl;

import hr.abysalto.hiring.mid.client.DummyJsonApiClient;
import hr.abysalto.hiring.mid.dto.ProductResponse;
import hr.abysalto.hiring.mid.model.Category;
import hr.abysalto.hiring.mid.model.Product;
import hr.abysalto.hiring.mid.service.ProductService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final DummyJsonApiClient dummyJsonApiClient;

    public ProductServiceImpl(DummyJsonApiClient dummyJsonApiClient) {
        this.dummyJsonApiClient = dummyJsonApiClient;
    }

    @Cacheable("products")
    public ProductResponse getAllProducts(int skip, int limit, String sortBy, String sortOrder) {
        ProductResponse response = dummyJsonApiClient.getProducts(skip, limit);
        List<Product> sortedProducts = sortProducts(response.getProducts(), sortBy, sortOrder);
        response.setProducts(sortedProducts);
        return response;
    }

    @Cacheable("product")
    public Product getProductById(Integer id) {
        return dummyJsonApiClient.getProductById(id);
    }

    @Cacheable("categories")
    public List<Category> getCategories() {
        List<Category> categories = dummyJsonApiClient.getCategories();
        return categories;
    }

    @Cacheable("productsByCategory")
    public ProductResponse getProductsByCategory(String category, int skip, int limit, String sortBy, String sortOrder) {
        ProductResponse response = dummyJsonApiClient.getProductsByCategory(category, skip, limit);
        List<Product> sortedProducts = sortProducts(response.getProducts(), sortBy, sortOrder);
        response.setProducts(sortedProducts);
        return response;
    }

    @Cacheable("searchResults")
    public ProductResponse searchProducts(String query, int skip, int limit, String sortBy, String sortOrder) {
        ProductResponse response = dummyJsonApiClient.searchProducts(query, skip, limit);
        List<Product> sortedProducts = sortProducts(response.getProducts(), sortBy, sortOrder);
        response.setProducts(sortedProducts);
        return response;
    }

    private List<Product> sortProducts(List<Product> products, String sortBy, String sortOrder) {
        Comparator<Product> comparator = getComparator(sortBy);
        
        if ("desc".equalsIgnoreCase(sortOrder)) {
            comparator = comparator.reversed();
        }
        
        return products.stream()
                .sorted(comparator)
                .toList();
    }

    private Comparator<Product> getComparator(String sortBy) {
        return switch (sortBy.toLowerCase()) {
            case "price" -> Comparator.comparing(Product::getPrice);
            case "rating" -> Comparator.comparing(Product::getRating);
            case "stock" -> Comparator.comparing(Product::getStock);
            case "title" -> Comparator.comparing(Product::getTitle);
            case "brand" -> Comparator.comparing(Product::getBrand);
            case "category" -> Comparator.comparing(Product::getCategory);
            default -> Comparator.comparing(Product::getTitle);
        };
    }
} 