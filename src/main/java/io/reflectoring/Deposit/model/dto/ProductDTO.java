package io.reflectoring.Deposit.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import jakarta.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    @Valid @NotBlank(message = "Uuid cannot be blank")
    private String uuid;
    @Valid @NotBlank(message = "Name cannot be blank")
    private String name;
    @Valid @NotNull
    private Long quantity;
    @Valid @NotNull
    private Double unitWeight;

}
