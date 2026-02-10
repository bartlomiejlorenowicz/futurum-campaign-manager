package pl.futurum.campaignmanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.futurum.campaignmanager.account.model.EmeraldAccount;
import pl.futurum.campaignmanager.campaign.repository.CampaignRepository;
import pl.futurum.campaignmanager.exception.NotFoundException;
import pl.futurum.campaignmanager.mapper.ProductMapper;
import pl.futurum.campaignmanager.product.dto.request.ProductUpdateRequest;
import pl.futurum.campaignmanager.product.model.Product;
import pl.futurum.campaignmanager.account.repository.EmeraldAccountRepository;
import pl.futurum.campaignmanager.product.repository.ProductRepository;
import pl.futurum.campaignmanager.product.dto.request.ProductRequest;
import pl.futurum.campaignmanager.product.dto.response.ProductResponse;

import java.util.List;

import static pl.futurum.campaignmanager.mapper.ProductMapper.toResponse;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final EmeraldAccountRepository emeraldAccountRepository;
    private final CampaignRepository campaignRepository;

    public ProductResponse create(ProductRequest request) {
        EmeraldAccount account = emeraldAccountRepository.findById(request.emeraldAccountId())
                .orElseThrow(() -> new NotFoundException("Emerald account not found"));

        Product product = ProductMapper.toEntity(request,  account);

        Product saved = productRepository.save(product);

        return toResponse(saved, 0);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> list() {
        return productRepository.findAll()
                .stream()
                .map(product -> ProductMapper.toResponse(
                        product,
                        campaignRepository.countByProduct_Id(product.getId())
                ))
                .toList();
    }

    @Transactional
    public void delete(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        productRepository.delete(product);
    }

    @Transactional
    public ProductResponse update(Long productId, ProductUpdateRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        product.setName(request.name());
        long campaignsCount = campaignRepository.countByProduct_Id(productId);

        return ProductMapper.toResponse(product, campaignsCount);
    }

    @Transactional(readOnly = true)
    public ProductResponse get(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        long campaignsCount = campaignRepository.countByProduct_Id(productId);

        return ProductMapper.toResponse(product, campaignsCount);
    }


}
