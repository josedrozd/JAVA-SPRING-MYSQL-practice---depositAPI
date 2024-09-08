package io.reflectoring.Deposit.service.implementations;

import io.reflectoring.Deposit.model.dto.ProductDTO;
import io.reflectoring.Deposit.service.interfaces.CalculateProductTotalWeight;
import io.reflectoring.Deposit.service.interfaces.FindProductByUUID;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class DefaultCalculateProductTotalWeight implements CalculateProductTotalWeight {

    @Autowired
    private FindProductByUUID findProductByUUID;

    @Override
    public BigDecimal apply(@NonNull Model model) {
        ProductDTO product = findProductByUUID.apply(
                FindProductByUUID.Model.builder()
                        .uuid(model.getUuid())
                        .build());
        return calculateTotalWeight(product);
    }

    private BigDecimal calculateTotalWeight(ProductDTO product){
        return BigDecimal.valueOf(product.getQuantity() * product.getUnitWeight());
    }

}
