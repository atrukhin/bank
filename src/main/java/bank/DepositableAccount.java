package bank;

public abstract class DepositableAccount extends BankAccount implements Depositable {
    protected DepositableAccount(String name, Currency ccy) {
        super(name, ccy);
    }
}
