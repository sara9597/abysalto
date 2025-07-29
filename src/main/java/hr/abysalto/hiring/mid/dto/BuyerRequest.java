package hr.abysalto.hiring.mid.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyerRequest {
    private String firstName;
    private String lastName;
    private String title;
}