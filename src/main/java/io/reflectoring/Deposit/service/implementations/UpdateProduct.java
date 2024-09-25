package io.reflectoring.Deposit.service.implementations;

import io.reflectoring.Deposit.model.dto.ProductDTO;
import io.reflectoring.Deposit.repository.ProductRepository;
import io.reflectoring.Deposit.service.interfaces.StoreProduct;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Qualifier("UPDATE")
public class UpdateProduct implements StoreProduct {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void accept(@NonNull ProductDTO productDTO) {
        log.info(String.format("Product found for uuid: %s. Updating existing quantity.", productDTO.getUuid()));
        productRepository.updateQuantity(productDTO.getUuid(), productDTO.getQuantity());
    }

}
