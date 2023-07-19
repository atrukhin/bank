package bank;

import java.math.BigDecimal;

public final class CreditCard extends CreditableAccount {
    private final BigDecimal rate;

    public CreditCard(String name, Currency ccy, BigDecimal limit, BigDecimal rate) {
        super(name, ccy, limit);
        this.rate = rate;
    }

    public BigDecimal getRate() {
        return rate;
    }
}
