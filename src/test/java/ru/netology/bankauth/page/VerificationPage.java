package ru.netology.bankauth.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement codeField = $("[data-test-id=code] input");
    private final SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private final SelenideElement errorNotification =$ ("[data-test-id='error-notification'] .notification__content");

    public void verifyVerificationPageVisibility() {
        codeField.shouldBe(visible);
    } //метод проверяет видимоть поля вовода кода верификации
    public void verifyErrorNotification(String expectedText) {
        errorNotification.shouldHave(exactText(expectedText)).shouldBe(visible);
    } //метод проверяет появления сооб об ошибке
    public DashboardPage validVerify(String verificationCode) {
        verify(verificationCode);
        return new DashboardPage();
    } //метод используется для реализации позитивного сценария
    public void verify(String verificationCode) {
        codeField.setValue(verificationCode);
        verifyButton.click();
    } //метод который заполняет поля ввода кода верификации и нажимает кнопку
}