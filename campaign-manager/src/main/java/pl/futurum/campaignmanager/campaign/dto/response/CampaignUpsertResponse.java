package pl.futurum.campaignmanager.campaign.dto.response;

import java.math.BigDecimal;

public record CampaignUpsertResponse(
        CampaignResponse campaign,
        BigDecimal emeraldBalance
) {}
