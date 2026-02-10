package pl.futurum.campaignmanager.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.futurum.campaignmanager.product.dto.request.ProductRequest;
import pl.futurum.campaignmanager.product.dto.request.ProductUpdateRequest;
import pl.futurum.campaignmanager.product.dto.response.ProductResponse;
import pl.futurum.campaignmanager.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductResponse> list() {
        return productService.list();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(
            @Valid @RequestBody ProductRequest request
    ) {
        return productService.create(request);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long productId) {
        productService.delete(productId);
    }

    @PutMapping("/{productId}")
    public ProductResponse update(
            @PathVariable Long productId,
            @Valid @RequestBody ProductUpdateRequest request
    ) {
        return productService.update(productId, request);
    }

    @GetMapping("/{productId}")
    public ProductResponse get(@PathVariable Long productId) {
        return productService.get(productId);
    }

}
