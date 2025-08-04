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
public class Quote {
    private Long id;

    @NotBlank
    @NotNull
    @Size(max = 100, message = "Author name must not be blank and must be less than or equal to 100 characters")
    @Pattern(regexp = "^[^<>&'\"/`=:]*$", message = "The field contains prohibited characters")
    private String author;
    @NotBlank
    @Size(max = 500, message = "Content must not be blank and must be less than or equal to 500 characters")
    @Pattern(regexp = "^[^<>&'\"/`=:]*$", message = "The field contains prohibited characters")
    private String content;
    @NotNull(message = "Category must not be null")
    private Category category;
}
