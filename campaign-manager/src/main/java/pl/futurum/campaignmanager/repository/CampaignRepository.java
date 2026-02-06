package pl.futurum.campaignmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.futurum.campaignmanager.account.model.EmeraldAccount;

public interface CampaignRepository extends JpaRepository<EmeraldAccount, Long> {
}
