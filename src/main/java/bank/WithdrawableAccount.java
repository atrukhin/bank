package bank;

public abstract class WithdrawableAccount extends DepositableAccount implements Withdrawable {
    protected WithdrawableAccount(String name, Currency ccy) {
        super(name, ccy);
    }
}
