package pl.futurum.campaignmanager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.futurum.campaignmanager.account.model.EmeraldAccount;
import pl.futurum.campaignmanager.account.repository.EmeraldAccountRepository;
import pl.futurum.campaignmanager.campaign.repository.CampaignRepository;
import pl.futurum.campaignmanager.exception.NotFoundException;
import pl.futurum.campaignmanager.product.dto.request.ProductRequest;
import pl.futurum.campaignmanager.product.dto.request.ProductUpdateRequest;
import pl.futurum.campaignmanager.product.model.Product;
import pl.futurum.campaignmanager.product.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    EmeraldAccountRepository emeraldAccountRepository;

    @Mock
    CampaignRepository campaignRepository;

    @InjectMocks
    ProductService productService;

    EmeraldAccount account;

    @BeforeEach
    void setup() {
        account = new EmeraldAccount();
        account.setId(1L);
    }

    @Test
    void create_shouldCreateProduct_whenAccountExists() {
        // given
        ProductRequest request = new ProductRequest("Test product", 1L);

        when(emeraldAccountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        when(productRepository.save(any()))
                .thenAnswer(inv -> inv.getArgument(0));

        // when
        var response = productService.create(request);

        // then
        assertThat(response.name()).isEqualTo("Test product");
        assertThat(response.campaignsCount()).isZero();

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void create_shouldThrow_whenAccountNotFound() {
        ProductRequest request = new ProductRequest("Test", 99L);

        when(emeraldAccountRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.create(request))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Emerald account not found");
    }

    @Test
    void list_shouldReturnProductsWithCampaignCount() {
        // given
        EmeraldAccount account = new EmeraldAccount();
        account.setId(10L);

        Product product = new Product();
        product.setId(1L);
        product.setName("Product A");
        product.setEmeraldAccount(account);

        when(productRepository.findAll())
                .thenReturn(List.of(product));

        when(campaignRepository.countByProduct_Id(1L))
                .thenReturn(3L);

        // when
        var result = productService.list();

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).campaignsCount()).isEqualTo(3);
    }

    @Test
    void delete_shouldDeleteProduct() {
        Product product = new Product();
        product.setId(1L);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        productService.delete(1L);

        verify(productRepository).delete(product);
    }

    @Test
    void delete_shouldThrow_whenProductNotFound() {
        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.delete(1L))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void update_shouldUpdateProductName() {
        // given
        EmeraldAccount account = new EmeraldAccount();
        account.setId(10L);

        Product product = new Product();
        product.setId(1L);
        product.setName("Old name");
        product.setEmeraldAccount(account);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(campaignRepository.countByProduct_Id(1L))
                .thenReturn(2L);

        ProductUpdateRequest request = new ProductUpdateRequest("New name");

        // when
        var response = productService.update(1L, request);

        // then
        assertThat(product.getName()).isEqualTo("New name");
        assertThat(response.campaignsCount()).isEqualTo(2);
    }

    @Test
    void get_shouldReturnProduct() {
        // given
        EmeraldAccount account = new EmeraldAccount();
        account.setId(10L);

        Product product = new Product();
        product.setId(1L);
        product.setName("Product");
        product.setEmeraldAccount(account);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(campaignRepository.countByProduct_Id(1L))
                .thenReturn(1L);

        // when
        var response = productService.get(1L);

        // then
        assertThat(response.name()).isEqualTo("Product");
        assertThat(response.campaignsCount()).isEqualTo(1);
    }

}