package io.reflectoring.Deposit.service.implementations;

import io.reflectoring.Deposit.model.dto.ProductDTO;
import io.reflectoring.Deposit.model.enums.ProductStrategyEnum;
import io.reflectoring.Deposit.repository.ProductRepository;
import io.reflectoring.Deposit.service.interfaces.SaveProduct;
import io.reflectoring.Deposit.service.interfaces.StoreProduct;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DefaultSaveProduct implements SaveProduct {

    private ProductRepository productRepository;
    private Map<ProductStrategyEnum, StoreProduct> productStrategyMap;

    @Autowired
    public DefaultSaveProduct(
            ProductRepository productRepository,
            CreateProduct createProduct,
            UpdateProduct updateProduct) {
        this.productRepository = productRepository;
        this.productStrategyMap = Map.of(
                ProductStrategyEnum.CREATE, createProduct,
                ProductStrategyEnum.UPDATE, updateProduct);
    }

    @Override
    public void accept(@NonNull ProductDTO productDTO) {
        if (productExists(productDTO.getUuid())) {
            productStrategyMap.get(ProductStrategyEnum.UPDATE).accept(productDTO);
        } else {
            productStrategyMap.get(ProductStrategyEnum.CREATE).accept(productDTO);
        }
    }

    private boolean productExists(String uuid) {
        return productRepository.findByUuid(uuid).isPresent();
    }

}
