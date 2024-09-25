package io.reflectoring.Deposit.service.implementations;

import io.reflectoring.Deposit.model.dto.ProductDTO;
import io.reflectoring.Deposit.model.entity.Product;
import io.reflectoring.Deposit.repository.ProductRepository;
import io.reflectoring.Deposit.service.interfaces.StoreProduct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Qualifier("CREATE")
public class CreateProduct implements StoreProduct {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void accept(ProductDTO productDTO) {
        log.info(String.format("Saving new product for uuid: %s", productDTO.getUuid()));
        productRepository.save(Product.builder()
                .uuid(productDTO.getUuid())
                .name(productDTO.getName())
                .quantity(productDTO.getQuantity())
                .unitWeight(productDTO.getUnitWeight())
                .build());
    }

}
