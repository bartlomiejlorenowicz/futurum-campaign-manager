package pl.futurum.campaignmanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.futurum.campaignmanager.account.model.EmeraldAccount;
import pl.futurum.campaignmanager.exception.NotFoundException;
import pl.futurum.campaignmanager.mapper.ProductMapper;
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

    public ProductResponse create(ProductRequest request) {
        EmeraldAccount account = emeraldAccountRepository.findById(request.emeraldAccountId())
                .orElseThrow(() -> new NotFoundException("Emerald account not found"));

        Product product = ProductMapper.toEntity(request,  account);

        Product saved = productRepository.save(product);

        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> list() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }
}
