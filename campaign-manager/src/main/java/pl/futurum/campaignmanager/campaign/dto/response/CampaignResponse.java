package pl.futurum.campaignmanager.campaign.dto.response;

import pl.futurum.campaignmanager.account.model.CampaignStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CampaignResponse(
        Long id,
        Long productId,
        String name,
        List<String> keywords,
        BigDecimal bidAmount,
        BigDecimal fund,
        CampaignStatus status,
        String town,
        Integer radiusKm,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
