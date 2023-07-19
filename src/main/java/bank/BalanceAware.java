package bank;

import java.math.BigDecimal;

public interface BalanceAware {
    BigDecimal getBalance();

    BigDecimal addBalance(BigDecimal amount);

    BigDecimal subBalance(BigDecimal amount);

    int compareTo(BigDecimal amount);
}
