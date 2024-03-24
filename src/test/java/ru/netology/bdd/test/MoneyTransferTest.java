package ru.netology.bdd.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.bdd.data.DataHelper;
import ru.netology.bdd.page.LoginPageV1;

import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferTest {
    @Test
    void shouldTransferMoneyFrom1CardTo2Card() throws InterruptedException {
        open("http://localhost:9999");

        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardValue = dashboardPage.getFirstCardBalance();
        var secondCardValue = dashboardPage.getSecondCardBalance();
        var transferPage = dashboardPage.clickOnSecondButton();
        var transfer = DataHelper.getValidTransfer(firstCardValue, DataHelper.cardNumber1 );
        transferPage.shouldTransfer(transfer);
        var newBalanceOfFirstCard = dashboardPage.getFirstCardBalance();
        var newBalanceOfSecondCard = dashboardPage.getSecondCardBalance();

        Assertions.assertEquals(secondCardValue + transfer.getAmount(), newBalanceOfSecondCard);
        Assertions.assertEquals(firstCardValue - transfer.getAmount(), newBalanceOfFirstCard);

        Thread.sleep(5000);
    }
    @Test
    void shouldTransferMoneyFrom2CardTo1Card() throws InterruptedException {
        open("http://localhost:9999");

        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardValue = dashboardPage.getFirstCardBalance();
        var secondCardValue = dashboardPage.getSecondCardBalance();
        var transferPage = dashboardPage.clickOnFirstButton();
        var transfer = DataHelper.getValidTransfer(secondCardValue, DataHelper.cardNumber2 );
        transferPage.shouldTransfer(transfer);
        var newBalanceOfFirstCard = dashboardPage.getFirstCardBalance();
        var newBalanceOfSecondCard = dashboardPage.getSecondCardBalance();

        Assertions.assertEquals(firstCardValue + transfer.getAmount(), newBalanceOfFirstCard);
        Assertions.assertEquals(secondCardValue - transfer.getAmount(), newBalanceOfSecondCard);

        Thread.sleep(5000);

    }
    @Test
    void shouldNotTransferIfNull() throws InterruptedException {
        open("http://localhost:9999");

        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.getFirstCardBalance();
        dashboardPage.getSecondCardBalance();
        var transferPage = dashboardPage.clickOnFirstButton();
        transferPage.shouldNotTransferIfNull("Ошибка");

        Thread.sleep(5000);
    }
    @Test
    void shouldNotTransferIfCanceled() {
        open("http://localhost:9999");

        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardValue = dashboardPage.getFirstCardBalance();
        var secondCardValue = dashboardPage.getSecondCardBalance();
        var transferPage = dashboardPage.clickOnFirstButton();
        var transfer = DataHelper.getValidTransfer(firstCardValue, DataHelper.cardNumber1 );
        transferPage.ifTransferWasCanceled(transfer);
        var newBalanceOfFirstCard = dashboardPage.getFirstCardBalance();
        var newBalanceOfSecondCard = dashboardPage.getSecondCardBalance();

        Assertions.assertEquals(newBalanceOfFirstCard, firstCardValue);
        Assertions.assertEquals(newBalanceOfSecondCard, secondCardValue);
    }
    @Test
    void shouldNotTransferFromOneCardToOneCard() throws InterruptedException {
        open("http://localhost:9999");

        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardValue = dashboardPage.getFirstCardBalance();
        var secondCardValue = dashboardPage.getSecondCardBalance();
        var transferPage = dashboardPage.clickOnFirstButton();
        transferPage.shouldNotTransferOneToOneCard("Ошибка");

        Thread.sleep(5000);

    }
    @Test
    void shouldNotTransferIfOverLimit() {
        open("http://localhost:9999");

        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardValue = dashboardPage.getFirstCardBalance();
        var secondCardValue = dashboardPage.getSecondCardBalance();
        var transferPage = dashboardPage.clickOnFirstButton();
        var transfer = DataHelper.getValidTransfer(firstCardValue, DataHelper.cardNumber1 );
        transferPage.shouldNotTransferIfAmountIsMore("Ошибка");
    }

}
