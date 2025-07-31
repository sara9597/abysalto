package hr.abysalto.hiring.mid.controller;

import hr.abysalto.hiring.mid.dto.ProductResponse;
import hr.abysalto.hiring.mid.model.Product;
import hr.abysalto.hiring.mid.service.impl.ProductServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ecommerce")
public class EcommerceWebController {

    private final ProductServiceImpl productService;

    public EcommerceWebController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String products(
            @RequestParam(defaultValue = "0") int skip,
            @RequestParam(defaultValue = "12") int limit,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder,
            Model model) {
        
        ProductResponse response;
        
        if (search != null && !search.trim().isEmpty()) {
            response = productService.searchProducts(search, skip, limit, sortBy, sortOrder);
        } else if (category != null && !category.trim().isEmpty()) {
            response = productService.getProductsByCategory(category, skip, limit, sortBy, sortOrder);
        } else {
            response = productService.getAllProducts(skip, limit, sortBy, sortOrder);
        }
        
        model.addAttribute("products", response.getProducts());
        model.addAttribute("total", response.getTotal());
        model.addAttribute("skip", response.getSkip());
        model.addAttribute("limit", response.getLimit());
        model.addAttribute("currentPage", (skip / limit) + 1);
        model.addAttribute("totalPages", (int) Math.ceil((double) response.getTotal() / limit));
        model.addAttribute("selectedCategory", category);
        model.addAttribute("searchQuery", search);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortOrder", sortOrder);
        
        return "ecommerce/products";
    }

    @GetMapping("/product/{id}")
    public String productDetail(@PathVariable Integer id, Model model) {
        try {
            Product product = productService.getProductById(id);
            model.addAttribute("product", product);
            return "ecommerce/product-detail";
        } catch (Exception e) {
            model.addAttribute("error", "Product not found");
            return "ecommerce/error";
        }
    }

    @GetMapping("/cart")
    public String cart(@RequestParam Integer userId, Model model) {
        model.addAttribute("userId", userId);
        return "ecommerce/cart";
    }

    @GetMapping("/favorites")
    public String favorites(@RequestParam Integer userId, Model model) {
        model.addAttribute("userId", userId);
        return "ecommerce/favorites";
    }

    @GetMapping("/register")
    public String register() {
        return "ecommerce/register";
    }

    @GetMapping("/login")
    public String login() {
        return "ecommerce/login";
    }

    @GetMapping("/profile")
    public String profile() {
        return "ecommerce/profile";
    }
} 