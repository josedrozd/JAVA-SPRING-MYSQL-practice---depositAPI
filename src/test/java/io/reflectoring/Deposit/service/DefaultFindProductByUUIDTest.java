package io.reflectoring.Deposit.service;

import io.reflectoring.Deposit.model.dto.ProductDTO;
import io.reflectoring.Deposit.model.entity.Product;
import io.reflectoring.Deposit.exception.NotFoundException;
import io.reflectoring.Deposit.repository.ProductRepository;
import io.reflectoring.Deposit.service.implementations.DefaultFindProductByUUID;
import io.reflectoring.Deposit.service.interfaces.FindProductByUUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static io.reflectoring.Deposit.utils.constants.ProductConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DefaultFindProductByUUIDTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private DefaultFindProductByUUID instance;

    @Test
    void whenProductFoundThenSuccess() {
        when(productRepository.findByUuid(anyString())).thenReturn(
                Optional.of(Product.builder()
                        .name(NAME)
                        .uuid(UUID)
                        .unitWeight(WEIGHT)
                        .quantity(QTY)
                        .build()));

        ProductDTO result = instance.apply(FindProductByUUID.Model.builder().uuid(UUID).build());

        assertEquals(UUID, result.getUuid());
        verify(productRepository, times(1)).findByUuid(anyString());
    }

    @Test
    void whenProductNotFoundThenException() {
        when(productRepository.findByUuid(anyString())).thenReturn(
                Optional.empty());

        assertThrows(NotFoundException.class, () -> instance.apply(FindProductByUUID.Model.builder().uuid(UUID).build()));
    }

    @Test
    void whenUUIDIsNullThenIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () -> instance.apply(null));
    }

}
