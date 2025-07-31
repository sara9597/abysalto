package hr.abysalto.hiring.mid;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.abysalto.hiring.mid.dto.CartItemRequest;
import hr.abysalto.hiring.mid.dto.UserRegistrationRequest;
import hr.abysalto.hiring.mid.model.User;
import hr.abysalto.hiring.mid.service.UserService;
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
public class CartControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetUserCart_Success() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("carttestuser");
        request.setEmail("carttest@example.com");
        request.setPassword("password123");
        request.setFirstName("Cart");
        request.setLastName("Test");
        
        User user = userService.registerUser(request);

        mockMvc.perform(get("/api/cart")
                        .param("userId", user.getUserId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetUserCart_WithNullUserId_ShouldReturn400() throws Exception {
        mockMvc.perform(get("/api/cart")
                        .param("userId", "null"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetUserCart_WithNonExistentUserId() throws Exception {
        mockMvc.perform(get("/api/cart")
                        .param("userId", "99999"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetUserCart_WithInvalidUserId_ShouldReturn400() throws Exception {
        mockMvc.perform(get("/api/cart")
                        .param("userId", "invalid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddToCart_Success() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("addtocartuser");
        request.setEmail("addtocart@example.com");
        request.setPassword("password123");
        request.setFirstName("AddToCart");
        request.setLastName("User");
        
        User user = userService.registerUser(request);

        CartItemRequest cartRequest = new CartItemRequest();
        cartRequest.setProductId(1);
        cartRequest.setQuantity(2);

        mockMvc.perform(post("/api/cart/add")
                        .param("userId", user.getUserId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(1))
                .andExpect(jsonPath("$.quantity").value(2));
    }

    @Test
    public void testAddToCart_WithNullUserId_ShouldReturn400() throws Exception {
        CartItemRequest cartRequest = new CartItemRequest();
        cartRequest.setProductId(1);
        cartRequest.setQuantity(2);

        mockMvc.perform(post("/api/cart/add")
                        .param("userId", "null")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddToCart_WithNullRequest_ShouldReturn400() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("addtocartnulluser");
        request.setEmail("addtocartnull@example.com");
        request.setPassword("password123");
        request.setFirstName("AddToCartNull");
        request.setLastName("User");
        
        User user = userService.registerUser(request);

        mockMvc.perform(post("/api/cart/add")
                        .param("userId", user.getUserId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("null"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddToCart_WithNullProductId_ShouldReturn400() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("addtocartnullproductuser");
        request.setEmail("addtocartnullproduct@example.com");
        request.setPassword("password123");
        request.setFirstName("AddToCartNullProduct");
        request.setLastName("User");
        
        User user = userService.registerUser(request);

        CartItemRequest cartRequest = new CartItemRequest();
        cartRequest.setProductId(null);
        cartRequest.setQuantity(2);

        mockMvc.perform(post("/api/cart/add")
                        .param("userId", user.getUserId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddToCart_WithZeroQuantity_ShouldReturn400() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("addtocartzeroqtyuser");
        request.setEmail("addtocartzeroqty@example.com");
        request.setPassword("password123");
        request.setFirstName("AddToCartZeroQty");
        request.setLastName("User");
        
        User user = userService.registerUser(request);

        CartItemRequest cartRequest = new CartItemRequest();
        cartRequest.setProductId(1);
        cartRequest.setQuantity(0);

        mockMvc.perform(post("/api/cart/add")
                        .param("userId", user.getUserId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddToCart_WithNegativeQuantity_ShouldReturn400() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("addtocartnegqtyuser");
        request.setEmail("addtocartnegqty@example.com");
        request.setPassword("password123");
        request.setFirstName("AddToCartNegQty");
        request.setLastName("User");
        
        User user = userService.registerUser(request);

        CartItemRequest cartRequest = new CartItemRequest();
        cartRequest.setProductId(1);
        cartRequest.setQuantity(-1);

        mockMvc.perform(post("/api/cart/add")
                        .param("userId", user.getUserId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddToCart_WithNonExistentProduct_ShouldReturn400() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("addtocartnonexistentuser");
        request.setEmail("addtocartnonexistent@example.com");
        request.setPassword("password123");
        request.setFirstName("AddToCartNonExistent");
        request.setLastName("User");
        
        User user = userService.registerUser(request);

        CartItemRequest cartRequest = new CartItemRequest();
        cartRequest.setProductId(99999);
        cartRequest.setQuantity(1);

        mockMvc.perform(post("/api/cart/add")
                        .param("userId", user.getUserId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateCartItemQuantity_Success() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("updatecartuser");
        request.setEmail("updatecart@example.com");
        request.setPassword("password123");
        request.setFirstName("UpdateCart");
        request.setLastName("User");
        
        User user = userService.registerUser(request);

        CartItemRequest cartRequest = new CartItemRequest();
        cartRequest.setProductId(1);
        cartRequest.setQuantity(1);

        mockMvc.perform(post("/api/cart/add")
                        .param("userId", user.getUserId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartRequest)))
                .andExpect(status().isCreated());

        mockMvc.perform(put("/api/cart/update")
                        .param("userId", user.getUserId().toString())
                        .param("productId", "1")
                        .param("quantity", "3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.quantity").value(3));
    }

    @Test
    public void testUpdateCartItemQuantity_WithNullUserId_ShouldReturn400() throws Exception {
        mockMvc.perform(put("/api/cart/update")
                        .param("userId", "null")
                        .param("productId", "1")
                        .param("quantity", "2"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateCartItemQuantity_WithNullProductId_ShouldReturn400() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("updatecartnullproductuser");
        request.setEmail("updatecartnullproduct@example.com");
        request.setPassword("password123");
        request.setFirstName("UpdateCartNullProduct");
        request.setLastName("User");
        
        User user = userService.registerUser(request);

        mockMvc.perform(put("/api/cart/update")
                        .param("userId", user.getUserId().toString())
                        .param("productId", "null")
                        .param("quantity", "2"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateCartItemQuantity_WithZeroQuantity_ShouldReturn400() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("updatecartzeroqtyuser");
        request.setEmail("updatecartzeroqty@example.com");
        request.setPassword("password123");
        request.setFirstName("UpdateCartZeroQty");
        request.setLastName("User");
        
        User user = userService.registerUser(request);

        mockMvc.perform(put("/api/cart/update")
                        .param("userId", user.getUserId().toString())
                        .param("productId", "1")
                        .param("quantity", "0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateCartItemQuantity_WithNonExistentItem_ShouldReturn404() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("updatecartnonexistentuser");
        request.setEmail("updatecartnonexistent@example.com");
        request.setPassword("password123");
        request.setFirstName("UpdateCartNonExistent");
        request.setLastName("User");
        
        User user = userService.registerUser(request);

        mockMvc.perform(put("/api/cart/update")
                        .param("userId", user.getUserId().toString())
                        .param("productId", "99999")
                        .param("quantity", "2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testRemoveFromCart_Success() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("removecartuser");
        request.setEmail("removecart@example.com");
        request.setPassword("password123");
        request.setFirstName("RemoveCart");
        request.setLastName("User");
        
        User user = userService.registerUser(request);

        CartItemRequest cartRequest = new CartItemRequest();
        cartRequest.setProductId(1);
        cartRequest.setQuantity(1);

        mockMvc.perform(post("/api/cart/add")
                        .param("userId", user.getUserId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartRequest)))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/api/cart/remove")
                        .param("userId", user.getUserId().toString())
                        .param("productId", "1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testRemoveFromCart_WithNullUserId_ShouldReturn400() throws Exception {
        mockMvc.perform(delete("/api/cart/remove")
                        .param("userId", "null")
                        .param("productId", "1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRemoveFromCart_WithNullProductId_ShouldReturn400() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("removecartnullproductuser");
        request.setEmail("removecartnullproduct@example.com");
        request.setPassword("password123");
        request.setFirstName("RemoveCartNullProduct");
        request.setLastName("User");
        
        User user = userService.registerUser(request);

        mockMvc.perform(delete("/api/cart/remove")
                        .param("userId", user.getUserId().toString())
                        .param("productId", "null"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRemoveFromCart_WithNonExistentItem() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("removecartnonexistentuser");
        request.setEmail("removecartnonexistent@example.com");
        request.setPassword("password123");
        request.setFirstName("RemoveCartNonExistent");
        request.setLastName("User");
        
        User user = userService.registerUser(request);

        mockMvc.perform(delete("/api/cart/remove")
                        .param("userId", user.getUserId().toString())
                        .param("productId", "99999"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testClearCart_Success() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("clearcartuser");
        request.setEmail("clearcart@example.com");
        request.setPassword("password123");
        request.setFirstName("ClearCart");
        request.setLastName("User");
        
        User user = userService.registerUser(request);

        CartItemRequest cartRequest1 = new CartItemRequest();
        cartRequest1.setProductId(1);
        cartRequest1.setQuantity(1);

        CartItemRequest cartRequest2 = new CartItemRequest();
        cartRequest2.setProductId(2);
        cartRequest2.setQuantity(2);

        mockMvc.perform(post("/api/cart/add")
                        .param("userId", user.getUserId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartRequest1)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/cart/add")
                        .param("userId", user.getUserId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartRequest2)))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/api/cart/clear")
                        .param("userId", user.getUserId().toString()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testClearCart_WithNullUserId_ShouldReturn400() throws Exception {
        mockMvc.perform(delete("/api/cart/clear")
                        .param("userId", "null"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testClearCart_WithNonExistentUser() throws Exception {
        mockMvc.perform(delete("/api/cart/clear")
                        .param("userId", "99999"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testCartEndpoints_WithInvalidHttpMethods() throws Exception {
        mockMvc.perform(post("/api/cart"))
                .andExpect(status().isMethodNotAllowed());

        mockMvc.perform(put("/api/cart"))
                .andExpect(status().isMethodNotAllowed());

        mockMvc.perform(delete("/api/cart"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void testCartEndpoints_WithInvalidContentType() throws Exception {
        CartItemRequest cartRequest = new CartItemRequest();
        cartRequest.setProductId(1);
        cartRequest.setQuantity(1);

        mockMvc.perform(post("/api/cart/add")
                        .param("userId", "1")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("invalid content"))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void testCartEndpoints_ResponseTime() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("responsetimeuser");
        request.setEmail("responsetime@example.com");
        request.setPassword("password123");
        request.setFirstName("ResponseTime");
        request.setLastName("User");
        
        User user = userService.registerUser(request);

        long startTime = System.currentTimeMillis();
        
        mockMvc.perform(get("/api/cart")
                        .param("userId", user.getUserId().toString()))
                .andExpect(status().isOk());
        
        long endTime = System.currentTimeMillis();
        long responseTime = endTime - startTime;

        assert responseTime < 5000 : "Response time should be less than 5 seconds, but was: " + responseTime;
    }
} 