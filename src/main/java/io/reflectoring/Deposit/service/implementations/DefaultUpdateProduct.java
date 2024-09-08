package io.reflectoring.Deposit.service.implementations;

import io.reflectoring.Deposit.model.dto.ProductDTO;
import io.reflectoring.Deposit.model.entity.Product;
import io.reflectoring.Deposit.repository.ProductRepository;
import io.reflectoring.Deposit.service.interfaces.UpdateProduct;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class DefaultUpdateProduct implements UpdateProduct {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void accept(@NonNull ProductDTO newProduct) {
        Optional<Product> existingProduct = productRepository.findByUuid(newProduct.getUuid());
        if (existingProduct.isPresent()) {
            updateQuantity(newProduct.getUuid(), newProduct.getQuantity());
        } else {
            saveNewProduct(newProduct);
        }
    }

    private void updateQuantity(String uuid, Long quantity) {
        log.info(String.format("Product found for uuid: %s. Updating existing quantity.", uuid));
        productRepository.updateQuantity(uuid, quantity);
    }

    private void saveNewProduct(ProductDTO newProduct) {
        log.info(String.format("Saving new product for uuid: %s", newProduct.getUuid()));
        productRepository.save(Product.builder()
                .uuid(newProduct.getUuid())
                .name(newProduct.getName())
                .quantity(newProduct.getQuantity())
                .unitWeight(newProduct.getUnitWeight())
                .build());
    }

}
