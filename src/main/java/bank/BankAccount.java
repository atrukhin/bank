package bank;

import java.math.BigDecimal;

public abstract class BankAccount extends AbstractAccount implements BalanceAware {
    private final Currency ccy;
    private BigDecimal balance = BigDecimal.ZERO;

    protected BankAccount(String name, Currency ccy) {
        super(name);
        this.ccy = ccy;
    }

    public Currency getCcy() {
        return ccy;
    }

    @Override
    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public BigDecimal addBalance(BigDecimal amount) {
        balance = balance.add(amount);
        return getBalance();
    }

    @Override
    public BigDecimal subBalance(BigDecimal amount) {
        balance = balance.subtract(amount);
        return getBalance();
    }

    @Override
    public int compareTo(BigDecimal amount) {
        return balance.compareTo(amount);
    }

    @Override
    public void close() {
        if (compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalStateException(
                    String.format("Can not close {%s} {%s} account, balance remaining: {%s}",
                            getName(), ccy, balance));
        }
        super.close();
    }
}
