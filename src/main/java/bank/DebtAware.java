package bank;

import java.math.BigDecimal;

public interface DebtAware {
    BigDecimal getDebt();

    default BigDecimal getDebt(BigDecimal amount) {
        return hasDebt(amount) ? amount.negate() : BigDecimal.ZERO;
    }

    default boolean hasDebt(BigDecimal amount) {
        return BigDecimal.ZERO.compareTo(amount) > 0;
    }
}
