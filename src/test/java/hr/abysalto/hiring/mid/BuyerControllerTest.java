package hr.abysalto.hiring.mid;

import hr.abysalto.hiring.mid.model.BuyerRequest;
import hr.abysalto.hiring.mid.model.Buyer;
import hr.abysalto.hiring.mid.service.BuyerService;
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
public class BuyerControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private BuyerService buyerService;

    @Test
    public void testGetAllBuyers() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mockMvc.perform(get("/api/buyers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testCreateBuyer() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        String buyerJson = """
                {
                    "firstName": "Test",
                    "lastName": "User",
                    "title": "Mr"
                }
                """;

        mockMvc.perform(post("/api/buyers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buyerJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("Test"))
                .andExpect(jsonPath("$.lastName").value("User"))
                .andExpect(jsonPath("$.title").value("Mr"));
    }

    @Test
    public void testGetBuyerById() throws Exception {
        // First create a buyer
        BuyerRequest request = new BuyerRequest("Test", "User", "Mr");
        Buyer createdBuyer = buyerService.createBuyer(request);

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mockMvc.perform(get("/api/buyers/" + createdBuyer.getBuyerId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("Test"))
                .andExpect(jsonPath("$.lastName").value("User"));
    }
}