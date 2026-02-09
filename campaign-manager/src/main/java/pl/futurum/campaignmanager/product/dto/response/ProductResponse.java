package pl.futurum.campaignmanager.product.dto.response;

import jakarta.validation.constraints.NotNull;

public record ProductResponse(
        Long id,
        String name,
        Long emeraldAccountId) {
}
