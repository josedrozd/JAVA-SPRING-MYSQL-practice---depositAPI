package io.reflectoring.Deposit.service.interfaces;

import io.reflectoring.Deposit.model.dto.ProductDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.function.Function;

@FunctionalInterface
public interface FindProductByUUID extends Function<FindProductByUUID.Model, ProductDTO> {

    @Getter
    @Builder
    class Model {

        private String uuid;

    }
}
