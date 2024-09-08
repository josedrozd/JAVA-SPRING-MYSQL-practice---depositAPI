package io.reflectoring.Deposit.service.interfaces;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.function.Function;

@FunctionalInterface
public interface CalculateProductTotalWeight extends Function<CalculateProductTotalWeight.Model, BigDecimal> {

    @Getter
    @Builder
    class Model {

        private String uuid;

    }

}
