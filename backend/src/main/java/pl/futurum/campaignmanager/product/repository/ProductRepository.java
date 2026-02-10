package pl.futurum.campaignmanager.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.futurum.campaignmanager.product.model.Product;


public interface ProductRepository extends JpaRepository<Product, Long> {

}
