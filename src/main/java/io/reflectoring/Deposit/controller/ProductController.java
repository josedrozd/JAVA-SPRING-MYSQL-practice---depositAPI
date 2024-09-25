package io.reflectoring.Deposit.controller;

import io.reflectoring.Deposit.model.dto.ProductDTO;
import io.reflectoring.Deposit.service.interfaces.CalculateProductTotalWeight;
import io.reflectoring.Deposit.service.interfaces.FindProductByUUID;
import io.reflectoring.Deposit.service.interfaces.SaveProduct;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;

@RestController
@RequestMapping("/products")
@Validated
public class ProductController {

    @Autowired
    private FindProductByUUID findProductByUUID;
    @Autowired
    private SaveProduct saveProduct;
    @Autowired
    private CalculateProductTotalWeight calculateProductTotalWeight;

    /*
    * Finds a product by its uuid. If it does not exist it throws a 404 exception.
    */
    @GetMapping("/{uuid}")
    public ResponseEntity<ProductDTO> getProductByUUID(@Valid @NotBlank @PathVariable String uuid) {
        return ResponseEntity.ok(findProductByUUID.apply(
                FindProductByUUID.Model.builder().uuid(uuid).build()));
    }

    /*
    * It updates the quantity of a product adding to the existing one
    * or creates a new record in the database it it does not exist.
    */
    @PutMapping
    public ResponseEntity<Void> updateProduct(@Valid @NotNull @RequestBody ProductDTO body) {
        saveProduct.accept(body);
        return ResponseEntity.ok().build();
    }

    /*
    * Calculates the total weight of a product in the deposit. If it does not exist it throws a 404 exception.
     */
    @GetMapping("/{uuid}/weight")
    public ResponseEntity<BigDecimal> getProductTotalWeightByUUID(@Valid @NotNull @PathVariable String uuid) {
        return ResponseEntity.ok(calculateProductTotalWeight.apply(
                CalculateProductTotalWeight.Model.builder().uuid(uuid).build()));
    }

}
