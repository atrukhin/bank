package bank;

import java.math.BigDecimal;

public interface Withdrawable extends BalanceAware {
    default BigDecimal withdraw(BigDecimal amount) {
        if (compareTo(amount) < 0) {
            throw new IllegalStateException(
                    String.format("Insufficient funds: balance = {%s}, requested = {%s}",
                            getBalance(), amount));
        }
        return subBalance(amount);
    }
}
