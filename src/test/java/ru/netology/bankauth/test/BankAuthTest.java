package ru.netology.bankauth.test;

import org.junit.jupiter.api.*;
import ru.netology.bankauth.data.DataHelper;
import ru.netology.bankauth.data.SQLHelper;
import ru.netology.bankauth.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.bankauth.data.SQLHelper.cleanAuthCode;
import static ru.netology.bankauth.data.SQLHelper.cleanDataBase;

public class BankAuthTest {
    LoginPage loginPage;

    @AfterEach
    void tearDown() {
        cleanAuthCode();
    }//метод чистит код ауентификации

    @AfterAll
    static void tearDownAll() {
        cleanDataBase();
    } //после всех тестов вызываем очистку БД

    @BeforeEach
    void setUp() {
        loginPage = open("http://localhost:9999", LoginPage.class);
    }

    @Test
    @DisplayName("Should successfully login to dashboard with exist login and password from sut test data")
    void shouldSuccessfulLogin() {
        var authInfo = DataHelper.getAuthInfoWithTest();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisibility();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    @DisplayName("Should get error notification if user is not exist in base")
    void shouldGetErrorNotificationIfLoginWithRandomUser() {
        var authInfo = DataHelper.generateRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotification("Ошибка! Неверно указан логин или пароль");
    }

    @Test
    @DisplayName("Should get error notification if login with exist in base and active user and random verification code")
    void shouldGetErrorNotificationIfLoginWithExistUserAndRandomVerificationCode() {
        var authInfo = DataHelper.getAuthInfoWithTest();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisibility();
        var verificationCode = DataHelper.generateRandomVerificationCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.verifyErrorNotification("Ошибка! Неверно указан код! Попробуйте ещё раз.");
    }
}