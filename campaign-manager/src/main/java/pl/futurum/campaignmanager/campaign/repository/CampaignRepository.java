package pl.futurum.campaignmanager.campaign.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.futurum.campaignmanager.account.model.Campaign;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
}
