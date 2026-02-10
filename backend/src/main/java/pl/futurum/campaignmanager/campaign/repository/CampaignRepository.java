package pl.futurum.campaignmanager.campaign.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.futurum.campaignmanager.account.model.Campaign;

import java.util.List;
import java.util.Optional;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    List<Campaign> findAllByProduct_Id(Long productId);

    Optional<Campaign> findByIdAndProduct_Id(Long id, Long productId);

    long countByProduct_Id(Long productId);
}
