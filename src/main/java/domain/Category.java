package domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 50, message = "Category name must not be blank and must be less than or equal to 50 characters")
    @Pattern(regexp = "^[^<>&'\"/`=:]*$", message = "The field contains prohibited characters")
    private String name;
}
