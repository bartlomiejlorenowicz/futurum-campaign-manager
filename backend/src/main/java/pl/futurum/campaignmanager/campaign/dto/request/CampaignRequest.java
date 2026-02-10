package pl.futurum.campaignmanager.campaign.dto.request;

import jakarta.validation.constraints.*;
import pl.futurum.campaignmanager.account.model.CampaignStatus;

import java.math.BigDecimal;
import java.util.Set;

public record CampaignRequest(
        @NotBlank
        String name,

        @NotEmpty
        Set<@NotBlank String> keywords,

        @NotNull
        @DecimalMin("0.01")
        BigDecimal bidAmount,

        @DecimalMin("0.01")
        BigDecimal fund,

        @NotNull
        CampaignStatus status,

        @NotBlank
        String town,

        @NotNull
        @Min(1) Integer radiusKm
) {}
