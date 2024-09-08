package io.reflectoring.Deposit.service;

import io.reflectoring.Deposit.model.dto.ProductDTO;
import io.reflectoring.Deposit.service.implementations.DefaultCalculateProductTotalWeight;
import io.reflectoring.Deposit.service.interfaces.CalculateProductTotalWeight;
import io.reflectoring.Deposit.service.interfaces.FindProductByUUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static io.reflectoring.Deposit.utils.constants.ProductConstants.*;
import static io.reflectoring.Deposit.utils.constants.ProductConstants.QTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultCalculateProductTotalWeightTest {

    @Mock
    private FindProductByUUID findProductByUUID;
    @InjectMocks
    public DefaultCalculateProductTotalWeight instance;

    @Test
    void whenProductFoundThenCalculateQuantity() {
        when(findProductByUUID.apply(any())).thenReturn(
                ProductDTO.builder()
                        .name(NAME)
                        .uuid(UUID)
                        .unitWeight(WEIGHT)
                        .quantity(QTY)
                        .build());

        BigDecimal result = instance.apply(CalculateProductTotalWeight.Model.builder().uuid(UUID).build());

        assertEquals(BigDecimal.valueOf(2.2), result);
    }

    @Test
    void whenProductSearchExceptionThenFail() {
        when(findProductByUUID.apply(any())).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class,
                () -> instance.apply(CalculateProductTotalWeight.Model.builder().uuid(UUID).build()));
    }

    @Test
    void whenNullUUIDThenException() {
        assertThrows(IllegalArgumentException.class, () -> instance.apply(null));
    }

}
