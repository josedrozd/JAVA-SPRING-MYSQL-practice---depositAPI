package io.reflectoring.Deposit.service.implementations;

import io.reflectoring.Deposit.model.dto.ProductDTO;
import io.reflectoring.Deposit.model.entity.Product;
import io.reflectoring.Deposit.exception.NotFoundException;
import io.reflectoring.Deposit.repository.ProductRepository;
import io.reflectoring.Deposit.service.interfaces.FindProductByUUID;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultFindProductByUUID implements FindProductByUUID {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductDTO apply(@NonNull Model model) {
        Product product = findProduct(model.getUuid());

        return ProductDTO.builder()
                .uuid(model.getUuid())
                .name(product.getName())
                .quantity(product.getQuantity())
                .unitWeight(product.getUnitWeight())
                .build();
    }

    private Product findProduct(String uuid) {
        return productRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException(String.format("Product with uuid: %s is not found", uuid)));
    }

}
