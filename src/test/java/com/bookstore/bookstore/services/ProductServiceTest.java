package com.bookstore.bookstore.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import com.bookstore.bookstore.dto.ProductDTO;
import com.bookstore.bookstore.entities.Product;
import com.bookstore.bookstore.repositories.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Classe de testes unitarios para ProductService.
 */
@ExtendWith(MockitoExtension.class)
public final class ProductServiceTest {

    /** Preco padrao para os testes. */
    private static final double DEFAULT_PRICE = 150.0;

    /** Tamanho padrao da pagina. */
    private static final int PAGE_SIZE = 10;

    /** Servico a ser testado com injecao de mocks. */
    @InjectMocks
    private ProductService service;

    /** Mock do repositorio de produtos. */
    @Mock
    private ProductRepository repository;

    /** ID de um produto existente simulado. */
    private long existingId;

    /** Entidade de produto simulada. */
    private Product product;

    /** DTO de produto simulado. */
    private ProductDTO productDTO;

    /**
     * Configuracao inicial dos dados falsos para os testes.
     */
    @BeforeEach
    void setUp() {
        existingId = 1L;
        product = new Product();
        product.setId(existingId);
        product.setName("Clean Code");
        product.setDescription("Livro sobre boas práticas");
        product.setPrice(DEFAULT_PRICE);
        product.setImgUrl("http://img.com/clean-code.png");
        productDTO = new ProductDTO(product);
    }

    /**
     * Testa a busca por ID com sucesso.
     */
    @Test
    public void findByIdShouldReturnProductDTOWhenIdExists() {
        Mockito.when(repository.findById(existingId))
                .thenReturn(Optional.of(product));

        ProductDTO result = service.findById(existingId);

        assertNotNull(result);
        assertEquals(product.getName(), result.getName());
        Mockito.verify(repository, Mockito.times(1))
                .findById(existingId);
    }

    /**
     * Testa a listagem paginada.
     */
    @Test
    public void findAllShouldReturnPageOfProductDTO() {
        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        Page<Product> page = new PageImpl<>(List.of(product));
        Mockito.when(repository.findAll(pageable)).thenReturn(page);

        Page<ProductDTO> result = service.findAll(pageable);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
        Mockito.verify(repository, Mockito.times(1)).findAll(pageable);
    }

    /**
     * Testa a insercao de um novo produto.
     */
    @Test
    public void insertShouldReturnProductDTOWhenValidData() {
        Mockito.when(repository.save(any(Product.class)))
                .thenReturn(product);

        ProductDTO result = service.insert(productDTO);

        assertNotNull(result);
        assertEquals(productDTO.getName(), result.getName());
        Mockito.verify(repository, Mockito.times(1))
                .save(any(Product.class));
    }

    /**
     * Testa a atualizacao de um produto existente.
     */
    @Test
    public void updateShouldReturnProductDTOWhenIdExists() {
        Mockito.when(repository.getReferenceById(existingId))
                .thenReturn(product);
        Mockito.when(repository.save(any(Product.class)))
                .thenReturn(product);

        ProductDTO result = service.update(existingId, productDTO);

        assertNotNull(result);
        assertEquals(productDTO.getName(), result.getName());
        Mockito.verify(repository, Mockito.times(1))
                .getReferenceById(existingId);
        Mockito.verify(repository, Mockito.times(1))
                .save(any(Product.class));
    }

    /**
     * Testa a delecao de um produto existente.
     */
    @Test
    public void deleteShouldDoNothingWhenIdExists() {
        Mockito.when(repository.existsById(existingId)).thenReturn(true);
        Mockito.doNothing().when(repository).deleteById(existingId);

        assertDoesNotThrow(() -> {
            service.delete(existingId);
        });

        Mockito.verify(repository, Mockito.times(1))
                .existsById(existingId);
        Mockito.verify(repository, Mockito.times(1))
                .deleteById(existingId);
    }
}
