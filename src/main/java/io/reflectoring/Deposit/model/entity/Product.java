package io.reflectoring.Deposit.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    private String uuid;
    @NonNull
    private String name;
    @NonNull
    private Long quantity;
    @NonNull
    @Column(name = "unit_weight")
    private Double unitWeight;

}
