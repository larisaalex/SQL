package ru.netology.bankauth.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;

public class DataHelper {
    private static final Faker FAKER = new Faker(new Locale("en"));
    private DataHelper() {
    } //приватный конструктор

    public static AuthInfo getAuthInfoWithTest() {
        return new AuthInfo("vasya", "qwerty123");
    } // метод который возвращвет данные об аунтификации

    private static String generateRandomLogin() {
        return FAKER.name().username();
    } //методы умеет с помощью Faker генерировать случайного пользователя

    private static String generateRandomPassword() {
        return FAKER.internet().password();
    } //метод умеет с помощью Faker генерировать случайный пароль

    public static AuthInfo generateRandomUser() {
        return new AuthInfo(generateRandomLogin(), generateRandomPassword());
    } //метод который умеет возвращать случайного юзера со случайным логином и паролем

    public static VerificationCode generateRandomVerificationCode() {
        return new VerificationCode(FAKER.numerify("######"));
    } //метод который умеет генерировать рандомный код верификации, генерирует цифры по какому-то шаблону

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }
    //класс для обьекта с информацией об аутефикации

    @Value
    public static class VerificationCode {
        String code;
    }
    //класс для кода верификации
}