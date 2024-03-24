package ru.netology.bdd.page;

import ru.netology.bdd.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    public void shouldTransfer(DataHelper.Transfer info) {
        $("[data-test-id='amount'] input").setValue(info.getAmount().toString());
        $("[data-test-id='from'] input").setValue(info.getCardForTransferFrom());
        $("[data-test-id='action-transfer']").click();
    }
    public void shouldNotTransferIfNull(String error) {
        $("[data-test-id='amount'] input").setValue("");
        $("[data-test-id='from'] input").setValue("");
        $("[data-test-id='action-transfer']").click();
        $("[data-test-id='error-notification']").shouldBe(visible);
    }
    public void ifTransferWasCanceled(DataHelper.Transfer info) {
        $("[data-test-id='amount'] input").setValue(info.getAmount().toString());
        $("[data-test-id='from'] input").setValue(info.getCardForTransferFrom());
        $("[data-test-id='action-cancel']").click();
    }
    public void shouldNotTransferIfAmountIsMore(String error) {
        $("[data-test-id='amount'] input").setValue("11000");
        $("[data-test-id='from'] input").setValue(DataHelper.cardNumber2);
        $("[data-test-id='action-transfer']").click();
        $("[data-test-id='error-notification']").shouldBe(visible);
    }
    public void shouldNotTransferOneToOneCard(String error) {
        $("[data-test-id='amount'] input").setValue("1000");
        $("[data-test-id='from'] input").setValue(DataHelper.cardNumber1);
        $("[data-test-id='action-transfer']").click();
        $("[data-test-id='error-notification']").shouldBe(visible);
    }
}
