package pl.futurum.campaignmanager.account.model;

import jakarta.persistence.*;
import lombok.*;
import pl.futurum.campaignmanager.exception.InsufficientFundsException;

import java.math.BigDecimal;

@Entity
@Table(name = "emerald_accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmeraldAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal balance;

    public void deduct(BigDecimal amount) {
        if (balance.compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient emerald balance");
        }
        balance = balance.subtract(amount);
    }

    public void refund(BigDecimal amount) {
        balance = balance.add(amount);
    }
}
