package pl.futurum.campaignmanager.campaign.dto.response;

import pl.futurum.campaignmanager.account.model.CampaignStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record CampaignResponse(
        Long id,
        Long productId,
        String name,
        Set<String> keywords,
        BigDecimal bidAmount,
        BigDecimal fund,
        CampaignStatus status,
        String town,
        Integer radiusKm,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
