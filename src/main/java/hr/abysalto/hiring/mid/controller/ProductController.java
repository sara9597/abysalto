package hr.abysalto.hiring.mid.controller;

import hr.abysalto.hiring.mid.dto.ProductResponse;
import hr.abysalto.hiring.mid.model.Product;
import hr.abysalto.hiring.mid.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product Management", description = "APIs for product browsing and search")
public class ProductController {
    
    private final ProductService productService;
    
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    @GetMapping
    @Operation(summary = "Get all products with pagination")
    public ResponseEntity<ProductResponse> getAllProducts(
            @Parameter(description = "Number of products to skip") @RequestParam(defaultValue = "0") int skip,
            @Parameter(description = "Number of products to return") @RequestParam(defaultValue = "10") int limit) {
        ProductResponse response = productService.getAllProducts(skip, limit);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/categories")
    @Operation(summary = "Get all product categories")
    public ResponseEntity<List<String>> getCategories() {
        List<String> categories = productService.getCategories();
        return ResponseEntity.ok(categories);
    }
    
    @GetMapping("/category/{category}")
    @Operation(summary = "Get products by category with pagination")
    public ResponseEntity<ProductResponse> getProductsByCategory(
            @PathVariable String category,
            @Parameter(description = "Number of products to skip") @RequestParam(defaultValue = "0") int skip,
            @Parameter(description = "Number of products to return") @RequestParam(defaultValue = "10") int limit) {
        ProductResponse response = productService.getProductsByCategory(category, skip, limit);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/search")
    @Operation(summary = "Search products")
    public ResponseEntity<ProductResponse> searchProducts(
            @Parameter(description = "Search query") @RequestParam String q,
            @Parameter(description = "Number of products to skip") @RequestParam(defaultValue = "0") int skip,
            @Parameter(description = "Number of products to return") @RequestParam(defaultValue = "10") int limit) {
        ProductResponse response = productService.searchProducts(q, skip, limit);
        return ResponseEntity.ok(response);
    }
} 