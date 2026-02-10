package pl.futurum.campaignmanager.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRequest(

        @NotBlank
        String name,

        @NotNull
        Long emeraldAccountId
) {
}
