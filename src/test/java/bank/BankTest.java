package bank;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.platform.suite.api.Suite;

import java.math.BigDecimal;

@Suite
public class BankTest {

    @Test
    public void testDeposit() {
        // open
        Deposit depo = new Deposit("depo", Currency.RUB);
        Assertions.assertEquals("depo", depo.getName());
        Assertions.assertEquals(Currency.RUB, depo.getCcy());
        Assertions.assertEquals(BigDecimal.ZERO, depo.getBalance());
        Assertions.assertFalse(depo.isClosed());

        // deposit
        Assertions.assertEquals(new BigDecimal(50000), depo.deposit(new BigDecimal(50000)));

        // close
        Assertions.assertDoesNotThrow(depo::close);
        Assertions.assertEquals(BigDecimal.ZERO, depo.getBalance());
        Assertions.assertTrue(depo.isClosed());
    }

    @ParameterizedTest
    @EnumSource(Currency.class)
    public void testDebitCard(Currency ccy) {
        // open
        DebitCard card = new DebitCard("debit", ccy);
        Assertions.assertEquals("debit", card.getName());
        Assertions.assertEquals(ccy, card.getCcy());
        Assertions.assertEquals(BigDecimal.ZERO, card.getBalance());
        Assertions.assertFalse(card.isClosed());

        // deposit
        Assertions.assertEquals(new BigDecimal(50000), card.deposit(new BigDecimal(50000)));

        // withdraw
        Assertions.assertEquals(new BigDecimal(40000), card.withdraw(new BigDecimal(10000)));

        // try to withdraw more than balance
        Assertions.assertThrowsExactly(IllegalStateException.class,
                () -> card.withdraw(new BigDecimal(40001)));
        Assertions.assertEquals(new BigDecimal(40000), card.getBalance());

        // try to close with balance remaining
        Assertions.assertThrowsExactly(IllegalStateException.class, card::close);
        Assertions.assertEquals(new BigDecimal(40000), card.getBalance());
        Assertions.assertFalse(card.isClosed());

        // withdraw remaining
        Assertions.assertEquals(BigDecimal.ZERO, card.withdraw(new BigDecimal(40000)));

        // close
        Assertions.assertDoesNotThrow(card::close);
        Assertions.assertEquals(BigDecimal.ZERO, card.getBalance());
        Assertions.assertTrue(card.isClosed());
    }

    @Test
    public void testOverdraftCard() {
        // open
        OverdraftCard card = new OverdraftCard("overdraft", Currency.RUB);
        Assertions.assertEquals("overdraft", card.getName());
        Assertions.assertEquals(Currency.RUB, card.getCcy());
        Assertions.assertEquals(BigDecimal.ZERO, card.getBalance());
        Assertions.assertFalse(card.isClosed());

        // deposit
        Assertions.assertEquals(new BigDecimal(50000), card.deposit(new BigDecimal(50000)));

        // withdraw
        Assertions.assertEquals(new BigDecimal(40000), card.withdraw(new BigDecimal(10000)));

        // go overdraft
        Assertions.assertEquals(new BigDecimal(-10000), card.withdraw(new BigDecimal(50000)));
        Assertions.assertEquals(new BigDecimal(10000), card.getDebt());

        // try to close while in overdraft
        Assertions.assertThrowsExactly(IllegalStateException.class, card::close);
        Assertions.assertEquals(new BigDecimal(-10000), card.getBalance());
        Assertions.assertEquals(new BigDecimal(10000), card.getDebt());
        Assertions.assertFalse(card.isClosed());

        // pay overdraft
        Assertions.assertEquals(BigDecimal.ZERO, card.deposit(new BigDecimal(10000)));

        // close
        Assertions.assertDoesNotThrow(card::close);
        Assertions.assertEquals(BigDecimal.ZERO, card.getBalance());
        Assertions.assertTrue(card.isClosed());
    }

    @Test
    public void testCreditCard() {
        // open with 50000 limit
        CreditCard card = new CreditCard("credit", Currency.RUB,
                new BigDecimal(50000), new BigDecimal(1));
        Assertions.assertEquals("credit", card.getName());
        Assertions.assertEquals(Currency.RUB, card.getCcy());
        Assertions.assertEquals(new BigDecimal(1), card.getRate());
        Assertions.assertEquals(new BigDecimal(50000), card.getBalance());
        Assertions.assertEquals(BigDecimal.ZERO, card.getDebt());
        Assertions.assertFalse(card.isClosed());

        // deposit
        Assertions.assertEquals(new BigDecimal(60000), card.deposit(new BigDecimal(10000)));
        Assertions.assertEquals(BigDecimal.ZERO, card.getDebt());

        // withdraw taking 10000 of limit
        Assertions.assertEquals(new BigDecimal(40000), card.withdraw(new BigDecimal(20000)));
        Assertions.assertEquals(new BigDecimal(10000), card.getDebt());

        // withdraw taking more than limit
        Assertions.assertEquals(new BigDecimal(-10000), card.withdraw(new BigDecimal(50000)));
        Assertions.assertEquals(new BigDecimal(60000), card.getDebt());

        // try to close
        Assertions.assertThrowsExactly(IllegalStateException.class, card::close);
        Assertions.assertEquals(new BigDecimal(-10000), card.getBalance());
        Assertions.assertEquals(new BigDecimal(60000), card.getDebt());
        Assertions.assertFalse(card.isClosed());

        // pay some debt
        Assertions.assertEquals(BigDecimal.ZERO, card.deposit(new BigDecimal(10000)));
        Assertions.assertEquals(new BigDecimal(50000), card.getDebt());

        // try to close
        Assertions.assertThrowsExactly(IllegalStateException.class, card::close);
        Assertions.assertEquals(BigDecimal.ZERO, card.getBalance());
        Assertions.assertEquals(new BigDecimal(50000), card.getDebt());
        Assertions.assertFalse(card.isClosed());

        // pay full debt
        Assertions.assertEquals(new BigDecimal(50000), card.deposit(new BigDecimal(50000)));
        Assertions.assertEquals(BigDecimal.ZERO, card.getDebt());

        // close
        Assertions.assertDoesNotThrow(card::close);
        Assertions.assertEquals(BigDecimal.ZERO, card.getBalance());
        Assertions.assertEquals(BigDecimal.ZERO, card.getDebt());
        Assertions.assertTrue(card.isClosed());
    }
}
