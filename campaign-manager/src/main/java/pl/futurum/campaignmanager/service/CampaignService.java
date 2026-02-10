package pl.futurum.campaignmanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.futurum.campaignmanager.account.model.Campaign;
import pl.futurum.campaignmanager.account.model.EmeraldAccount;
import pl.futurum.campaignmanager.exception.NotFoundException;
import pl.futurum.campaignmanager.mapper.CampaignMapper;
import pl.futurum.campaignmanager.product.model.Product;
import pl.futurum.campaignmanager.product.repository.ProductRepository;
import pl.futurum.campaignmanager.campaign.dto.request.CampaignRequest;
import pl.futurum.campaignmanager.campaign.dto.response.CampaignResponse;
import pl.futurum.campaignmanager.campaign.dto.response.CampaignUpsertResponse;
import pl.futurum.campaignmanager.campaign.repository.CampaignRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final ProductRepository productRepository;

    public CampaignUpsertResponse createCampaign(Long productId, CampaignRequest request) {
        Product product = getProduct(productId);
        EmeraldAccount account = product.getEmeraldAccount();

        account.deduct(request.fund());

        Campaign campaign = CampaignMapper.toEntity(request, product);

        Campaign saved = campaignRepository.save(campaign);

        return new CampaignUpsertResponse(
                toResponse(saved),
                account.getBalance()
        );
    }

    public CampaignUpsertResponse updateCampaign(
            Long productId,
            Long campaignId,
            CampaignRequest request
    ) {
        Campaign campaign = getCampaignEntity(productId, campaignId);
        EmeraldAccount account = campaign.getProduct().getEmeraldAccount();

        CampaignMapper.updateEntity(campaign, request);

        handleFundChange(campaign, request.fund(), account);

        return new CampaignUpsertResponse(
                toResponse(campaign),
                account.getBalance()
        );
    }


    public void deleteCampaign(Long productId, Long campaignId) {
        Campaign campaign = getCampaignEntity(productId, campaignId);
        campaignRepository.delete(campaign);
    }

    @Transactional(readOnly = true)
    public List<CampaignResponse> listCampaigns(Long productId) {
        return campaignRepository.findAllByProduct_Id(productId)
                .stream()
                .map(CampaignMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CampaignResponse getCampaign(Long productId, Long campaignId) {
        return toResponse(getCampaignEntity(productId, campaignId));
    }

    private void handleFundChange(
            Campaign campaign,
            BigDecimal newFund,
            EmeraldAccount account
    ) {
        if (newFund.compareTo(campaign.getFund()) == 0) {
            return;
        }

        var diff = newFund.subtract(campaign.getFund());

        if (diff.signum() > 0) {
            account.deduct(diff);
        } else {
            account.refund(diff.abs());
        }

        campaign.setFund(newFund);
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }

    private Campaign getCampaignEntity(Long productId, Long campaignId) {
        return campaignRepository.findByIdAndProduct_Id(campaignId, productId)
                .orElseThrow(() -> new NotFoundException("Campaign not found"));
    }

    private CampaignResponse toResponse(Campaign campaign) {
        return CampaignMapper.toResponse(campaign);
    }
}
