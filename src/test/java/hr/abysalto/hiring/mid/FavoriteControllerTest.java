package hr.abysalto.hiring.mid;

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
public class FavoriteControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserService userService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetUserFavorites_Success() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("favoritestuser");
        request.setEmail("favoritest@example.com");
        request.setPassword("password123");
        request.setFirstName("Favorite");
        request.setLastName("Test");
        
        User user = userService.registerUser(request);

        mockMvc.perform(get("/api/favorites")
                        .param("userId", user.getUserId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetUserFavorites_WithNullUserId_ShouldReturn400() throws Exception {
        mockMvc.perform(get("/api/favorites")
                        .param("userId", "null"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetUserFavorites_WithNonExistentUserId() throws Exception {
        mockMvc.perform(get("/api/favorites")
                        .param("userId", "99999"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetUserFavorites_WithInvalidUserId_ShouldReturn400() throws Exception {
        mockMvc.perform(get("/api/favorites")
                        .param("userId", "invalid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddToFavorites_Success() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("addfavoriteuser");
        request.setEmail("addfavorite@example.com");
        request.setPassword("password123");
        request.setFirstName("AddFavorite");
        request.setLastName("User");
        
        User user = userService.registerUser(request);

        mockMvc.perform(post("/api/favorites/add")
                        .param("userId", user.getUserId().toString())
                        .param("productId", "1"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(1));
    }

    @Test
    public void testAddToFavorites_WithNullUserId_ShouldReturn400() throws Exception {
        mockMvc.perform(post("/api/favorites/add")
                        .param("userId", "null")
                        .param("productId", "1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddToFavorites_WithNullProductId_ShouldReturn400() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("addfavoritenullproductuser");
        request.setEmail("addfavoritenullproduct@example.com");
        request.setPassword("password123");
        request.setFirstName("AddFavoriteNullProduct");
        request.setLastName("User");
        
        User user = userService.registerUser(request);

        mockMvc.perform(post("/api/favorites/add")
                        .param("userId", user.getUserId().toString())
                        .param("productId", "null"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddToFavorites_WithNonExistentProduct_ShouldReturn400() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("addfavoritenonexistentuser");
        request.setEmail("addfavoritenonexistent@example.com");
        request.setPassword("password123");
        request.setFirstName("AddFavoriteNonExistent");
        request.setLastName("User");
        
        User user = userService.registerUser(request);

        mockMvc.perform(post("/api/favorites/add")
                        .param("userId", user.getUserId().toString())
                        .param("productId", "99999"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddToFavorites_WithInvalidProductId_ShouldReturn400() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("addfavoriteinvalidproductuser");
        request.setEmail("addfavoriteinvalidproduct@example.com");
        request.setPassword("password123");
        request.setFirstName("AddFavoriteInvalidProduct");
        request.setLastName("User");
        
        User user = userService.registerUser(request);

        mockMvc.perform(post("/api/favorites/add")
                        .param("userId", user.getUserId().toString())
                        .param("productId", "invalid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddToFavorites_DuplicateFavorite_ShouldReturn400() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("addfavoriteduplicateuser");
        request.setEmail("addfavoriteduplicate@example.com");
        request.setPassword("password123");
        request.setFirstName("AddFavoriteDuplicate");
        request.setLastName("User");
        
        User user = userService.registerUser(request);

        mockMvc.perform(post("/api/favorites/add")
                        .param("userId", user.getUserId().toString())
                        .param("productId", "1"))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/favorites/add")
                        .param("userId", user.getUserId().toString())
                        .param("productId", "1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRemoveFromFavorites_Success() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("removefavoriteuser");
        request.setEmail("removefavorite@example.com");
        request.setPassword("password123");
        request.setFirstName("RemoveFavorite");
        request.setLastName("User");
        
        User user = userService.registerUser(request);

        mockMvc.perform(post("/api/favorites/add")
                        .param("userId", user.getUserId().toString())
                        .param("productId", "1"))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/api/favorites/remove")
                        .param("userId", user.getUserId().toString())
                        .param("productId", "1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testRemoveFromFavorites_WithNullUserId_ShouldReturn400() throws Exception {
        mockMvc.perform(delete("/api/favorites/remove")
                        .param("userId", "null")
                        .param("productId", "1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRemoveFromFavorites_WithNullProductId_ShouldReturn400() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("removefavoritenullproductuser");
        request.setEmail("removefavoritenullproduct@example.com");
        request.setPassword("password123");
        request.setFirstName("RemoveFavoriteNullProduct");
        request.setLastName("User");
        
        User user = userService.registerUser(request);

        mockMvc.perform(delete("/api/favorites/remove")
                        .param("userId", user.getUserId().toString())
                        .param("productId", "null"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRemoveFromFavorites_WithNonExistentFavorite() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("removefavoritenonexistentuser");
        request.setEmail("removefavoritenonexistent@example.com");
        request.setPassword("password123");
        request.setFirstName("RemoveFavoriteNonExistent");
        request.setLastName("User");
        
        User user = userService.registerUser(request);

        mockMvc.perform(delete("/api/favorites/remove")
                        .param("userId", user.getUserId().toString())
                        .param("productId", "99999"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testIsFavorite_Success_WhenFavoriteExists() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("isfavoriteuser");
        request.setEmail("isfavorite@example.com");
        request.setPassword("password123");
        request.setFirstName("IsFavorite");
        request.setLastName("User");
        
        User user = userService.registerUser(request);

        mockMvc.perform(post("/api/favorites/add")
                        .param("userId", user.getUserId().toString())
                        .param("productId", "1"))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/favorites/check")
                        .param("userId", user.getUserId().toString())
                        .param("productId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    public void testIsFavorite_Success_WhenFavoriteDoesNotExist() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("isfavoritenotuser");
        request.setEmail("isfavoritenot@example.com");
        request.setPassword("password123");
        request.setFirstName("IsFavoriteNot");
        request.setLastName("User");
        
        User user = userService.registerUser(request);

        mockMvc.perform(get("/api/favorites/check")
                        .param("userId", user.getUserId().toString())
                        .param("productId", "99999"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(false));
    }

    @Test
    public void testIsFavorite_WithNullUserId_ShouldReturn400() throws Exception {
        mockMvc.perform(get("/api/favorites/check")
                        .param("userId", "null")
                        .param("productId", "1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testIsFavorite_WithNullProductId_ShouldReturn400() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("isfavoritenullproductuser");
        request.setEmail("isfavoritenullproduct@example.com");
        request.setPassword("password123");
        request.setFirstName("IsFavoriteNullProduct");
        request.setLastName("User");
        
        User user = userService.registerUser(request);

        mockMvc.perform(get("/api/favorites/check")
                        .param("userId", user.getUserId().toString())
                        .param("productId", "null"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testIsFavorite_WithInvalidUserId_ShouldReturn400() throws Exception {
        mockMvc.perform(get("/api/favorites/check")
                        .param("userId", "invalid")
                        .param("productId", "1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testIsFavorite_WithInvalidProductId_ShouldReturn400() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("isfavoriteinvalidproductuser");
        request.setEmail("isfavoriteinvalidproduct@example.com");
        request.setPassword("password123");
        request.setFirstName("IsFavoriteInvalidProduct");
        request.setLastName("User");
        
        User user = userService.registerUser(request);

        mockMvc.perform(get("/api/favorites/check")
                        .param("userId", user.getUserId().toString())
                        .param("productId", "invalid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testFavoriteEndpoints_WithInvalidHttpMethods() throws Exception {
        mockMvc.perform(post("/api/favorites")
                        .param("userId", "1"))
                .andExpect(status().isMethodNotAllowed());

        mockMvc.perform(put("/api/favorites")
                        .param("userId", "1"))
                .andExpect(status().isMethodNotAllowed());

        mockMvc.perform(delete("/api/favorites")
                        .param("userId", "1"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void testFavoriteEndpoints_WithInvalidPaths() throws Exception {
        mockMvc.perform(get("/api/favorites/invalid/path"))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/api/favorites/add"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void testFavoriteEndpoints_WithMissingParameters() throws Exception {
        mockMvc.perform(get("/api/favorites"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/api/favorites/add")
                        .param("userId", "1"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(delete("/api/favorites/remove")
                        .param("userId", "1"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/api/favorites/check")
                        .param("userId", "1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testFavoriteEndpoints_ResponseTime() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("favoriteresponsetimeuser");
        request.setEmail("favoriteresponsetime@example.com");
        request.setPassword("password123");
        request.setFirstName("FavoriteResponseTime");
        request.setLastName("User");
        
        User user = userService.registerUser(request);

        long startTime = System.currentTimeMillis();
        
        mockMvc.perform(get("/api/favorites")
                        .param("userId", user.getUserId().toString()))
                .andExpect(status().isOk());
        
        long endTime = System.currentTimeMillis();
        long responseTime = endTime - startTime;

        assert responseTime < 5000 : "Response time should be less than 5 seconds, but was: " + responseTime;
    }

    @Test
    public void testFavoriteEndpoints_WithSpecialCharacters() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("favoritespecialuser");
        request.setEmail("favoritespecial@example.com");
        request.setPassword("password123");
        request.setFirstName("FavoriteSpecial");
        request.setLastName("User");
        
        User user = userService.registerUser(request);

        mockMvc.perform(get("/api/favorites")
                        .param("userId", user.getUserId().toString())
                        .header("User-Agent", "TestClient@#$%"))
                .andExpect(status().isOk());
    }

    @Test
    public void testFavoriteEndpoints_WithLargeNumbers() throws Exception {
        mockMvc.perform(get("/api/favorites")
                        .param("userId", "999999999"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/favorites/check")
                        .param("userId", "1")
                        .param("productId", "999999999"))
                .andExpect(status().isOk());
    }
} 