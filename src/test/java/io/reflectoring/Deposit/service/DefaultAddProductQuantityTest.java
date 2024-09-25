package io.reflectoring.Deposit.service;

import io.reflectoring.Deposit.model.dto.ProductDTO;
import io.reflectoring.Deposit.model.entity.Product;
import io.reflectoring.Deposit.repository.ProductRepository;
import io.reflectoring.Deposit.service.implementations.CreateProduct;
import io.reflectoring.Deposit.service.implementations.DefaultAddProductQuantity;
import io.reflectoring.Deposit.service.implementations.UpdateProduct;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static io.reflectoring.Deposit.utils.constants.ProductConstants.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DefaultAddProductQuantityTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CreateProduct createProduct;
    @Mock
    private UpdateProduct updateProduct;
    @InjectMocks
    private DefaultAddProductQuantity instance;

    @Test
    void whenProductFoundThenUpdateQuantity() {
        when(productRepository.findByUuid(anyString())).thenReturn(
                Optional.of(Product.builder()
                        .name(NAME)
                        .uuid(UUID)
                        .unitWeight(WEIGHT)
                        .quantity(QTY)
                        .build()));

        instance.accept(ProductDTO.builder()
                .name(NAME)
                .uuid(UUID)
                .quantity(QTY)
                .unitWeight(WEIGHT)
                .build());

        verify(productRepository, times(1)).findByUuid(eq(UUID));
        verify(updateProduct, times(1)).accept(any());
        verify(createProduct, times(0)).accept(any());
    }

    @Test
    void whenProductNotFoundThenSaveNewOne() {
        when(productRepository.findByUuid(anyString())).thenReturn(
                Optional.empty());

        instance.accept(ProductDTO.builder()
                .name(NAME)
                .uuid(UUID)
                .quantity(QTY)
                .unitWeight(WEIGHT)
                .build());

        verify(productRepository, times(1)).findByUuid(eq(UUID));
        verify(updateProduct, times(0)).accept(any());
        verify(createProduct, times(1)).accept(any());
    }

    @Test
    void whenNullDTOThenIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> instance.accept(null));
    }

    @Test
    void whenJPARepositoryFailsThenDoNotUpdate() {
        when(productRepository.findByUuid(anyString())).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> instance.accept(ProductDTO.builder()
                .name(NAME)
                .uuid(UUID)
                .quantity(QTY)
                .unitWeight(WEIGHT)
                .build()));
        verify(productRepository, times(0)).updateQuantity(eq(UUID), eq(QTY));
        verify(productRepository, times(0)).save(any(Product.class));
    }

}
