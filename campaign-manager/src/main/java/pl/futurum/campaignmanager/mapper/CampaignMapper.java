package pl.futurum.campaignmanager.mapper;

import pl.futurum.campaignmanager.account.model.Campaign;
import pl.futurum.campaignmanager.campaign.dto.request.CampaignRequest;
import pl.futurum.campaignmanager.campaign.dto.response.CampaignResponse;
import pl.futurum.campaignmanager.product.model.Product;

import java.util.List;
import java.util.Set;

public class CampaignMapper {

    private CampaignMapper() {}

    public static Campaign toEntity(
            CampaignRequest request,
            Product product
    ) {
        return Campaign.builder()
                .name(request.name())
                .keywords(Set.copyOf(request.keywords()))
                .bidAmount(request.bidAmount())
                .fund(request.fund())
                .status(request.status())
                .town(request.town())
                .radiusKm(request.radiusKm())
                .product(product)
                .build();
    }

    public static CampaignResponse toResponse(Campaign campaign) {
        return new CampaignResponse(
                campaign.getId(),
                campaign.getProduct().getId(),
                campaign.getName(),
                campaign.getKeywords() != null
                        ? List.copyOf(campaign.getKeywords())
                        : List.of(),
                campaign.getBidAmount(),
                campaign.getFund(),
                campaign.getStatus(),
                campaign.getTown(),
                campaign.getRadiusKm(),
                campaign.getCreatedAt(),
                campaign.getUpdatedAt()
        );
    }

    public static void updateEntity(
            Campaign campaign,
            CampaignRequest request
    ) {
        campaign.setName(request.name());
        campaign.setKeywords(Set.copyOf(request.keywords()));
        campaign.setBidAmount(request.bidAmount());
        campaign.setFund(request.fund());
        campaign.setTown(request.town());
        campaign.setRadiusKm(request.radiusKm());
        campaign.setStatus(request.status());
    }
}
