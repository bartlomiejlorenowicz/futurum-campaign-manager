package pl.futurum.campaignmanager.product.dto.response;

public record ProductResponse(
        Long id,
        String name,
        Long emeraldAccountId,
        long campaignsCount
) {}
