package pl.futurum.campaignmanager.product.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ProductUpdateRequest(
        @NotBlank String name
) {}
