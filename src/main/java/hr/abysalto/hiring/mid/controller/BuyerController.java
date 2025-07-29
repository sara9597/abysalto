package hr.abysalto.hiring.mid.controller;

import hr.abysalto.hiring.mid.model.BuyerRequest;
import hr.abysalto.hiring.mid.model.Buyer;
import hr.abysalto.hiring.mid.service.BuyerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Buyer", description = "Buyer management operations")
@RestController
@RequestMapping("/api/buyers")
public class BuyerController {

    private final BuyerService buyerService;

    public BuyerController(BuyerService buyerService) {
        this.buyerService = buyerService;
    }

    @Operation(summary = "Get all buyers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved buyers",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Buyer.class)))
    })
    @GetMapping
    public ResponseEntity<List<Buyer>> getAllBuyers() {
        List<Buyer> buyers = buyerService.getAllBuyers();
        return ResponseEntity.ok(buyers);
    }

    @Operation(summary = "Get buyer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved buyer",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Buyer.class))),
            @ApiResponse(responseCode = "404", description = "Buyer not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Buyer> getBuyerById(@PathVariable Integer id) {
        return buyerService.getBuyerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new buyer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Buyer created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Buyer.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<Buyer> createBuyer(@RequestBody BuyerRequest request) {
        Buyer createdBuyer = buyerService.createBuyer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBuyer);
    }

    @Operation(summary = "Update an existing buyer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Buyer updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Buyer.class))),
            @ApiResponse(responseCode = "404", description = "Buyer not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Buyer> updateBuyer(@PathVariable Integer id, @RequestBody BuyerRequest request) {
        return buyerService.updateBuyer(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a buyer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Buyer deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Buyer not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBuyer(@PathVariable Integer id) {
        boolean deleted = buyerService.deleteBuyer(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
