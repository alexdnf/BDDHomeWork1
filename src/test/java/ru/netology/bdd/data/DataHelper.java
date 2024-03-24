package ru.netology.bdd.data;

import lombok.Value;

import java.util.Random;

public class DataHelper {

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo() {
        return new AuthInfo("petya", "123qwerty");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }
    public static String cardNumber1 = "5559 0000 0000 0001";
    public static String cardNumber2 = "5559 0000 0000 0002";

    @Value
    public static class Transfer {
        private Integer amount;
        private String CardForTransferFrom;
    }

    public static Transfer getValidTransfer(Integer maxValue, String card) {
        return new Transfer(new Random().nextInt(maxValue), card);
    }
}
