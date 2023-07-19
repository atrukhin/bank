package bank;

import java.math.BigDecimal;

public interface Creditable extends BalanceAware {
    default BigDecimal withdraw(BigDecimal amount) {
        return subBalance(amount);
    }
}
