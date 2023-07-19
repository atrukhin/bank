package bank;

import java.math.BigDecimal;

public abstract class CreditableAccount extends DepositableAccount implements Creditable, DebtAware {
    private BigDecimal limit;

    protected CreditableAccount(String name, Currency ccy, BigDecimal limit) {
        super(name, ccy);
        this.limit = limit;
    }

    @Override
    public BigDecimal getBalance() {
        return super.getBalance().add(limit);
    }

    @Override
    public BigDecimal getDebt() {
        return getDebt(super.getBalance());
    }

    @Override
    public void close() {
        super.close();
        limit = BigDecimal.ZERO;
    }
}
