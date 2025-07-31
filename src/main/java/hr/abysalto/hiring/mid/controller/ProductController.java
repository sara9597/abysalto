package hr.abysalto.hiring.mid.controller;

import hr.abysalto.hiring.mid.dto.ProductResponse;
import hr.abysalto.hiring.mid.model.Category;
import hr.abysalto.hiring.mid.model.Product;
import hr.abysalto.hiring.mid.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Product management APIs")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "Get all products with pagination and sorting")
    public ResponseEntity<ProductResponse> getAllProducts(
            @Parameter(description = "Number of products to skip") @RequestParam(defaultValue = "0") int skip,
            @Parameter(description = "Number of products to return") @RequestParam(defaultValue = "10") int limit,
            @Parameter(description = "Sort field (title, price, rating, stock)") @RequestParam(defaultValue = "title") String sortBy,
            @Parameter(description = "Sort direction (asc, desc)") @RequestParam(defaultValue = "asc") String sortOrder) {

        ProductResponse response = productService.getAllProducts(skip, limit, sortBy, sortOrder);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        try {
            Product product = productService.getProductById(id);
            if (product == null) {
                throw new NoSuchElementException("Product not found with ID: " + id);
            }
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            throw new NoSuchElementException("Product not found with ID: " + id);
        }
    }

    @GetMapping("/categories")
    @Operation(summary = "Get all product categories")
    public ResponseEntity<List<Category>> getCategories() {
        System.out.println("Get all categories called.");
        List<Category> categories = productService.getCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Get products by category with pagination and sorting")
    public ResponseEntity<ProductResponse> getProductsByCategory(
            @PathVariable String category,
            @Parameter(description = "Number of products to skip") @RequestParam(defaultValue = "0") int skip,
            @Parameter(description = "Number of products to return") @RequestParam(defaultValue = "10") int limit,
            @Parameter(description = "Sort field (title, price, rating, stock)") @RequestParam(defaultValue = "title") String sortBy,
            @Parameter(description = "Sort direction (asc, desc)") @RequestParam(defaultValue = "asc") String sortOrder) {

        ProductResponse response = productService.getProductsByCategory(category, skip, limit, sortBy, sortOrder);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    @Operation(summary = "Search products with pagination and sorting")
    public ResponseEntity<ProductResponse> searchProducts(
            @Parameter(description = "Search query") @RequestParam String query,
            @Parameter(description = "Number of products to skip") @RequestParam(defaultValue = "0") int skip,
            @Parameter(description = "Number of products to return") @RequestParam(defaultValue = "10") int limit,
            @Parameter(description = "Sort field (title, price, rating, stock)") @RequestParam(defaultValue = "title") String sortBy,
            @Parameter(description = "Sort direction (asc, desc)") @RequestParam(defaultValue = "asc") String sortOrder) {

        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("Search query cannot be empty");
        }
        ProductResponse response = productService.searchProducts(query, skip, limit, sortBy, sortOrder);
        return ResponseEntity.ok(response);
    }
} 