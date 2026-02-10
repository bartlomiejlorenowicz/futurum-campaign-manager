package pl.futurum.campaignmanager.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.futurum.campaignmanager.campaign.dto.request.CampaignRequest;
import pl.futurum.campaignmanager.campaign.dto.response.CampaignResponse;
import pl.futurum.campaignmanager.campaign.dto.response.CampaignUpsertResponse;
import pl.futurum.campaignmanager.service.CampaignService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products/{productId}/campaigns")
public class CampaignController {

    private final CampaignService campaignService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CampaignUpsertResponse create(
            @PathVariable Long productId,
            @Valid @RequestBody CampaignRequest request
    ) {
        return campaignService.createCampaign(productId, request);
    }

    @PutMapping("/{campaignId}")
    public CampaignUpsertResponse update(
            @PathVariable Long productId,
            @PathVariable Long campaignId,
            @Valid @RequestBody CampaignRequest request
    ) {
        return campaignService.updateCampaign(productId, campaignId, request);
    }

    @DeleteMapping("/{campaignId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long productId,
            @PathVariable Long campaignId
    ) {
        campaignService.deleteCampaign(productId, campaignId);
    }

    @GetMapping
    public List<CampaignResponse> list(@PathVariable Long productId) {
        return campaignService.listCampaigns(productId);
    }

    @GetMapping("/{campaignId}")
    public CampaignResponse get(
            @PathVariable Long productId,
            @PathVariable Long campaignId
    ) {
        return campaignService.getCampaign(productId, campaignId);
    }
}
