package bank;

import java.math.BigDecimal;

public abstract class OverdraftableAccount extends CreditableAccount {
    protected OverdraftableAccount(String name, Currency ccy) {
        super(name, ccy, BigDecimal.ZERO);
    }
}
