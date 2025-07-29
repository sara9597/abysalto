package hr.abysalto.hiring.mid.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Buyer {
    private Integer buyerId;
    private String firstName;
    private String lastName;
    private String title;
}
