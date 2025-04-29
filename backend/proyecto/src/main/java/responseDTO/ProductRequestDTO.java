package responseDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;
@Data
public class ProductRequestDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private LocalDateTime createdAt;
    private CategoryDTO category;
    private String captchaToken;

    @Data
    public static class CategoryDTO {
        private Long id;
    }
}
