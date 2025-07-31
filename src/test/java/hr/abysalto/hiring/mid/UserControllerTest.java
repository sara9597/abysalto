package hr.abysalto.hiring.mid;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.abysalto.hiring.mid.dto.LoginRequest;
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
public class UserControllerTest {

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
    public void testRegisterUser_Success() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("testuser");
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setFirstName("Test");
        request.setLastName("User");

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.firstName").value("Test"))
                .andExpect(jsonPath("$.lastName").value("User"));
    }

    @Test
    public void testRegisterUser_WithNullRequest_ShouldReturn400() throws Exception {
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("null"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRegisterUser_WithEmptyBody_ShouldReturn400() throws Exception {
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testLoginUser_Success() throws Exception {
        UserRegistrationRequest registrationRequest = new UserRegistrationRequest();
        registrationRequest.setUsername("logintest");
        registrationRequest.setEmail("logintest@example.com");
        registrationRequest.setPassword("password123");
        registrationRequest.setFirstName("Login");
        registrationRequest.setLastName("Test");
        
        userService.registerUser(registrationRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("logintest");
        loginRequest.setPassword("password123");

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("logintest"));
    }

    @Test
    public void testLoginUser_WithInvalidCredentials_ShouldReturn400() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("nonexistent");
        loginRequest.setPassword("wrongpassword");

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testLoginUser_WithNullRequest_ShouldReturn400() throws Exception {
        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("null"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllUsers_Success() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetUserById_Success() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("getbyidtest");
        request.setEmail("getbyid@example.com");
        request.setPassword("password123");
        request.setFirstName("GetById");
        request.setLastName("Test");
        
        User createdUser = userService.registerUser(request);

        mockMvc.perform(get("/api/users/" + createdUser.getUserId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("getbyidtest"))
                .andExpect(jsonPath("$.email").value("getbyid@example.com"));
    }

    @Test
    public void testGetUserById_WithNonExistentId_ShouldReturn404() throws Exception {
        mockMvc.perform(get("/api/users/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetUserById_WithInvalidId_ShouldReturn404() throws Exception {
        mockMvc.perform(get("/api/users/invalid"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetCurrentUser_Success() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("currentusertest");
        request.setEmail("currentuser@example.com");
        request.setPassword("password123");
        request.setFirstName("Current");
        request.setLastName("User");
        
        userService.registerUser(request);

        mockMvc.perform(get("/api/users/current")
                        .param("username", "currentusertest"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("currentusertest"));
    }

    @Test
    public void testGetCurrentUser_WithEmptyUsername_ShouldReturn400() throws Exception {
        mockMvc.perform(get("/api/users/current")
                        .param("username", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetCurrentUser_WithNullUsername_ShouldReturn400() throws Exception {
        mockMvc.perform(get("/api/users/current")
                        .param("username", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetCurrentUser_WithNonExistentUsername_ShouldReturn404() throws Exception {
        mockMvc.perform(get("/api/users/current")
                        .param("username", "nonexistentuser"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateUser_Success() throws Exception {
        UserRegistrationRequest createRequest = new UserRegistrationRequest();
        createRequest.setUsername("updatetest");
        createRequest.setEmail("update@example.com");
        createRequest.setPassword("password123");
        createRequest.setFirstName("Update");
        createRequest.setLastName("Test");
        
        User createdUser = userService.registerUser(createRequest);

        UserRegistrationRequest updateRequest = new UserRegistrationRequest();
        updateRequest.setUsername("updatetest");
        updateRequest.setEmail("updated@example.com");
        updateRequest.setPassword("newpassword123");
        updateRequest.setFirstName("Updated");
        updateRequest.setLastName("User");

        mockMvc.perform(put("/api/users/" + createdUser.getUserId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("updated@example.com"))
                .andExpect(jsonPath("$.firstName").value("Updated"))
                .andExpect(jsonPath("$.lastName").value("User"));
    }

    @Test
    public void testUpdateUser_WithNonExistentId_ShouldReturn404() throws Exception {
        UserRegistrationRequest updateRequest = new UserRegistrationRequest();
        updateRequest.setUsername("test");
        updateRequest.setEmail("test@example.com");
        updateRequest.setPassword("password123");
        updateRequest.setFirstName("Test");
        updateRequest.setLastName("User");

        mockMvc.perform(put("/api/users/99999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteUser_Success() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("deletetest");
        request.setEmail("delete@example.com");
        request.setPassword("password123");
        request.setFirstName("Delete");
        request.setLastName("Test");
        
        User createdUser = userService.registerUser(request);

        mockMvc.perform(delete("/api/users/" + createdUser.getUserId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteUser_WithNonExistentId_ShouldReturn404() throws Exception {
        mockMvc.perform(delete("/api/users/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteUser_WithInvalidId_ShouldReturn404() throws Exception {
        mockMvc.perform(delete("/api/users/invalid"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUserEndpoints_WithInvalidHttpMethods() throws Exception {
        mockMvc.perform(post("/api/users"))
                .andExpect(status().isMethodNotAllowed());

        mockMvc.perform(put("/api/users"))
                .andExpect(status().isMethodNotAllowed());

        mockMvc.perform(delete("/api/users"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void testUserEndpoints_WithInvalidContentType() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("test");
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setFirstName("Test");
        request.setLastName("User");

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("invalid content"))
                .andExpect(status().isUnsupportedMediaType());
    }
} 