package pl.futurum.campaignmanager.product.model;

import jakarta.persistence.*;
import lombok.*;
import pl.futurum.campaignmanager.account.model.EmeraldAccount;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "emerald_account_id", nullable = false)
    private EmeraldAccount emeraldAccount;
}
