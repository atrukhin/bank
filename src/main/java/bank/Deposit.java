package bank;

public final class Deposit extends DepositableAccount {
    public Deposit(String name, Currency ccy) {
        super(name, ccy);
    }

    @Override
    public void close() {
        subBalance(getBalance());
        super.close();
    }
}
