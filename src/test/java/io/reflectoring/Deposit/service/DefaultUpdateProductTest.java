package io.reflectoring.Deposit.service;

import io.reflectoring.Deposit.model.dto.ProductDTO;
import io.reflectoring.Deposit.model.entity.Product;
import io.reflectoring.Deposit.repository.ProductRepository;
import io.reflectoring.Deposit.service.implementations.DefaultUpdateProduct;
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
public class DefaultUpdateProductTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private DefaultUpdateProduct instance;

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
        verify(productRepository, times(1)).updateQuantity(eq(UUID), eq(QTY));
        verify(productRepository, times(0)).save(any(Product.class));
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
        verify(productRepository, times(0)).updateQuantity(eq(UUID), eq(QTY));
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void whenNullDTOThenIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> instance.accept(null));
    }

    @Test
    void whenNullDTOAttributesThenIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> instance.accept(ProductDTO.builder().build()));
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
