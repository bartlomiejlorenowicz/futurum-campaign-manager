package pl.futurum.campaignmanager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.futurum.campaignmanager.account.model.Campaign;
import pl.futurum.campaignmanager.account.model.CampaignStatus;
import pl.futurum.campaignmanager.account.model.EmeraldAccount;
import pl.futurum.campaignmanager.campaign.dto.request.CampaignRequest;
import pl.futurum.campaignmanager.campaign.repository.CampaignRepository;
import pl.futurum.campaignmanager.exception.NotFoundException;
import pl.futurum.campaignmanager.product.model.Product;
import pl.futurum.campaignmanager.product.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CampaignServiceTest {

    @Mock
    CampaignRepository campaignRepository;

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    CampaignService campaignService;

    Product product;
    EmeraldAccount account;

    @BeforeEach
    void setup() {
        account = new EmeraldAccount();
        account.setBalance(new BigDecimal("1000"));

        product = new Product();
        product.setId(1L);
        product.setEmeraldAccount(account);
    }

    @Test
    void createCampaign_shouldDeductFundsAndSaveCampaign() {
        // given
        CampaignRequest request = new CampaignRequest(
                "Test campaign",
                Set.of("java", "spring"),
                new BigDecimal("2.00"),
                new BigDecimal("200"),
                CampaignStatus.ON,
                "Warsaw",
                20
        );

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(campaignRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        // when
        var response = campaignService.createCampaign(1L, request);

        // then
        assertThat(response.campaign().name()).isEqualTo("Test campaign");

        verify(campaignRepository).save(any(Campaign.class));
    }

    @Test
    void createCampaign_shouldThrow_whenProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                campaignService.createCampaign(1L, mock(CampaignRequest.class))
        ).isInstanceOf(NotFoundException.class);
    }

    @Test
    void updateCampaign_shouldRefundMoney_whenFundDecreases() {
        // given
        Campaign campaign = new Campaign();
        campaign.setId(10L);
        campaign.setFund(new BigDecimal("500"));
        campaign.setProduct(product);

        when(campaignRepository.findByIdAndProduct_Id(10L, 1L))
                .thenReturn(Optional.of(campaign));

        CampaignRequest request = new CampaignRequest(
                "Updated",
                Set.of("java"),
                new BigDecimal("1.00"),
                new BigDecimal("300"),
                CampaignStatus.ON,
                "Krakow",
                10
        );

        // when
        var response = campaignService.updateCampaign(1L, 10L, request);

        // then
        assertThat(campaign.getFund()).isEqualByComparingTo("300");
    }

    @Test
    void deleteCampaign_shouldDeleteCampaign() {
        Campaign campaign = new Campaign();
        campaign.setId(5L);
        campaign.setProduct(product);

        when(campaignRepository.findByIdAndProduct_Id(5L, 1L))
                .thenReturn(Optional.of(campaign));

        campaignService.deleteCampaign(1L, 5L);

        verify(campaignRepository).delete(campaign);
    }
}