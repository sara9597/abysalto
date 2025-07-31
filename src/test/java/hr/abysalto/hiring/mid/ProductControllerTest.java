package hr.abysalto.hiring.mid;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
public class ProductControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAllProducts_Success() throws Exception {
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.products").isArray())
                .andExpect(jsonPath("$.total").isNumber())
                .andExpect(jsonPath("$.skip").value(0))
                .andExpect(jsonPath("$.limit").value(10));
    }

    @Test
    public void testGetAllProducts_WithPagination() throws Exception {
        mockMvc.perform(get("/api/products")
                        .param("skip", "5")
                        .param("limit", "3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.skip").value(5))
                .andExpect(jsonPath("$.limit").value(3));
    }

    @Test
    public void testGetAllProducts_WithSorting() throws Exception {
        mockMvc.perform(get("/api/products")
                        .param("sortBy", "price")
                        .param("sortOrder", "desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.products").isArray());
    }

    @Test
    public void testGetAllProducts_WithAllParameters() throws Exception {
        mockMvc.perform(get("/api/products")
                        .param("skip", "10")
                        .param("limit", "5")
                        .param("sortBy", "rating")
                        .param("sortOrder", "asc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.skip").value(10))
                .andExpect(jsonPath("$.limit").value(5));
    }

    @Test
    public void testGetAllProducts_WithInvalidSortField() throws Exception {
        mockMvc.perform(get("/api/products")
                        .param("sortBy", "invalidField"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetAllProducts_WithInvalidSortOrder() throws Exception {
        mockMvc.perform(get("/api/products")
                        .param("sortOrder", "invalid"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetProductById_Success() throws Exception {
        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testGetProductById_WithNonExistentId_ShouldReturn404() throws Exception {
        mockMvc.perform(get("/api/products/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetProductById_WithInvalidId_ShouldReturn404() throws Exception {
        mockMvc.perform(get("/api/products/invalid"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetProductById_WithZeroId_ShouldReturn404() throws Exception {
        mockMvc.perform(get("/api/products/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetProductById_WithNegativeId_ShouldReturn404() throws Exception {
        mockMvc.perform(get("/api/products/-1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetCategories_Success() throws Exception {
        mockMvc.perform(get("/api/products/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetCategories_WithHeaders() throws Exception {
        mockMvc.perform(get("/api/products/categories")
                        .header("Accept", "application/json")
                        .header("User-Agent", "TestClient"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetProductsByCategory_Success() throws Exception {
        mockMvc.perform(get("/api/products/category/smartphones"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.products").isArray());
    }

    @Test
    public void testGetProductsByCategory_WithPagination() throws Exception {
        mockMvc.perform(get("/api/products/category/laptops")
                        .param("skip", "2")
                        .param("limit", "5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.skip").value(2))
                .andExpect(jsonPath("$.limit").exists());
    }

    @Test
    public void testGetProductsByCategory_WithSorting() throws Exception {
        mockMvc.perform(get("/api/products/category/fragrances")
                        .param("sortBy", "price")
                        .param("sortOrder", "desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetProductsByCategory_WithAllParameters() throws Exception {
        mockMvc.perform(get("/api/products/category/home-decoration")
                        .param("skip", "5")
                        .param("limit", "3")
                        .param("sortBy", "rating")
                        .param("sortOrder", "asc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.skip").value(5))
                .andExpect(jsonPath("$.limit").exists());
    }

    @Test
    public void testGetProductsByCategory_WithNonExistentCategory() throws Exception {
        mockMvc.perform(get("/api/products/category/nonexistent"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.products").isArray());
    }

    @Test
    public void testGetProductsByCategory_WithEmptyCategory() throws Exception {
        mockMvc.perform(get("/api/products/category/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSearchProducts_Success() throws Exception {
        mockMvc.perform(get("/api/products/search")
                        .param("query", "phone"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.products").isArray());
    }

    @Test
    public void testSearchProducts_WithPagination() throws Exception {
        mockMvc.perform(get("/api/products/search")
                        .param("query", "laptop")
                        .param("skip", "3")
                        .param("limit", "4"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.skip").value(3))
                .andExpect(jsonPath("$.limit").exists());
    }

    @Test
    public void testSearchProducts_WithSorting() throws Exception {
        mockMvc.perform(get("/api/products/search")
                        .param("query", "watch")
                        .param("sortBy", "price")
                        .param("sortOrder", "desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testSearchProducts_WithAllParameters() throws Exception {
        mockMvc.perform(get("/api/products/search")
                        .param("query", "shirt")
                        .param("skip", "1")
                        .param("limit", "2")
                        .param("sortBy", "title")
                        .param("sortOrder", "asc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.skip").value(1))
                .andExpect(jsonPath("$.limit").exists());
    }

    @Test
    public void testSearchProducts_WithEmptyQuery_ShouldReturn400() throws Exception {
        mockMvc.perform(get("/api/products/search")
                        .param("query", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSearchProducts_WithNullQuery_ShouldReturn400() throws Exception {
        mockMvc.perform(get("/api/products/search")
                        .param("query", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSearchProducts_WithWhitespaceQuery_ShouldReturn400() throws Exception {
        mockMvc.perform(get("/api/products/search")
                        .param("query", "   "))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSearchProducts_WithSpecialCharacters() throws Exception {
        mockMvc.perform(get("/api/products/search")
                        .param("query", "test@#$%"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testSearchProducts_WithLongQuery() throws Exception {
        String longQuery = "a".repeat(1000);
        mockMvc.perform(get("/api/products/search")
                        .param("query", longQuery))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testProductEndpoints_WithInvalidHttpMethods() throws Exception {
        mockMvc.perform(post("/api/products"))
                .andExpect(status().isMethodNotAllowed());

        mockMvc.perform(put("/api/products"))
                .andExpect(status().isMethodNotAllowed());

        mockMvc.perform(delete("/api/products"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void testProductEndpoints_WithInvalidPaths() throws Exception {
        mockMvc.perform(get("/api/products/invalid/path"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testProductEndpoints_WithInvalidParameters() throws Exception {
        mockMvc.perform(get("/api/products")
                        .param("skip", "-1"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/products")
                        .param("limit", "-5"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/products")
                        .param("limit", "0"))
                .andExpect(status().isOk());
    }

    @Test
    public void testProductEndpoints_ResponseTime() throws Exception {
        long startTime = System.currentTimeMillis();
        
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk());
        
        long endTime = System.currentTimeMillis();
        long responseTime = endTime - startTime;

        assert responseTime < 5000 : "Response time should be less than 5 seconds, but was: " + responseTime;
    }
} 