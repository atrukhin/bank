package bank;

import java.math.BigDecimal;

public interface Depositable extends BalanceAware {
    default BigDecimal deposit(BigDecimal amount) {
        return addBalance(amount);
    }
}
