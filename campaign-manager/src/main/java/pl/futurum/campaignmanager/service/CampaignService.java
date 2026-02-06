package pl.futurum.campaignmanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.futurum.campaignmanager.account.model.Campaign;
import pl.futurum.campaignmanager.account.model.CampaignStatus;
import pl.futurum.campaignmanager.account.model.EmeraldAccount;
import pl.futurum.campaignmanager.account.repository.EmeraldAccountRepository;
import pl.futurum.campaignmanager.campaign.repository.CampaignRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final EmeraldAccountRepository emeraldAccountRepository;

    public Campaign createCampaign(Campaign campaign, Long emeraldAccountId) {
        EmeraldAccount account = emeraldAccountRepository.findById(emeraldAccountId)
                .orElseThrow(() -> new RuntimeException("emerald account not found"));

        account.deduct(campaign.getFund());

        if (account.getBalance().signum() <= 0) {
            campaign.setStatus(CampaignStatus.OFF);
        }

        campaign.setEmeraldAccount(account);

        return campaignRepository.save(campaign);
    }

    public Campaign updateCampaign(Long campaignId, Campaign updatedCampaign) {
        Campaign existing = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new RuntimeException("campaign not found"));

        existing.setName(updatedCampaign.getName());
        existing.setKeywords(updatedCampaign.getKeywords());
        existing.setBidAmount(updatedCampaign.getBidAmount());
        existing.setTown(updatedCampaign.getTown());
        existing.setRadiusKm(updatedCampaign.getRadiusKm());

        if (updatedCampaign.getFund().compareTo(existing.getFund()) != 0) {
            var diff = updatedCampaign.getFund().subtract(existing.getFund());
            EmeraldAccount account = existing.getEmeraldAccount();

            if (diff.signum() > 0) {
                account.deduct(diff);
            } else {
                account.refund(diff.abs());
            }

            existing.setFund(updatedCampaign.getFund());
        }

        if (existing.getFund().signum() <= 0) {
            existing.setStatus(CampaignStatus.OFF);
        } else {
            existing.setStatus(updatedCampaign.getStatus());
        }

        return existing;
    }

    public void deleteCampaign(Long campaignId) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found"));

        EmeraldAccount account = campaign.getEmeraldAccount();
        account.refund(campaign.getFund());

        campaignRepository.delete(campaign);
    }

    @Transactional(readOnly = true)
    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Campaign getCampaign(Long id) {
        return campaignRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found"));
    }
}
