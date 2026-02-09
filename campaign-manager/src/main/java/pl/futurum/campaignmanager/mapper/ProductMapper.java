package pl.futurum.campaignmanager.mapper;

import pl.futurum.campaignmanager.account.model.EmeraldAccount;
import pl.futurum.campaignmanager.product.dto.request.ProductRequest;
import pl.futurum.campaignmanager.product.dto.response.ProductResponse;
import pl.futurum.campaignmanager.product.model.Product;

public class ProductMapper {

    private ProductMapper() {}

    public static Product toEntity(
            ProductRequest request,
            EmeraldAccount account
    ) {
        return Product.builder()
                .name(request.name())
                .emeraldAccount(account)
                .build();
    }

    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getEmeraldAccount().getId()
        );
    }
}
