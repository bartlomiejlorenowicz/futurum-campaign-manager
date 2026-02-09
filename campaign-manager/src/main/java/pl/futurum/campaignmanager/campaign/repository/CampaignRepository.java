package pl.futurum.campaignmanager.campaign.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.futurum.campaignmanager.account.model.Campaign;

import java.util.List;
import java.util.Optional;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    List<Campaign> findAllByProductId(Long productId);
    Optional<Campaign> findByIdAndProductId(Long id, Long productId);
}
