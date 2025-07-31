package hr.abysalto.hiring.mid.client;

import hr.abysalto.hiring.mid.dto.ProductResponse;
import hr.abysalto.hiring.mid.model.Category;
import hr.abysalto.hiring.mid.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "dummy-json-api", url = "https://dummyjson.com")
public interface DummyJsonApiClient {
    
    @GetMapping("/products")
    ProductResponse getProducts(@RequestParam(defaultValue = "0") int skip, 
                              @RequestParam(defaultValue = "10") int limit);
    
    @GetMapping("/products/{id}")
    Product getProductById(@PathVariable Integer id);
    
    @GetMapping("/products/categories")
    List<Category> getCategories();
    
    @GetMapping("/products/category/{category}")
    ProductResponse getProductsByCategory(@PathVariable String category,
                                        @RequestParam(defaultValue = "0") int skip,
                                        @RequestParam(defaultValue = "10") int limit);
    
    @GetMapping("/products/search")
    ProductResponse searchProducts(@RequestParam String q,
                                 @RequestParam(defaultValue = "0") int skip,
                                 @RequestParam(defaultValue = "10") int limit);
} 