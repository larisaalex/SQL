package ru.netology.bankauth.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();
    //с помощью QUERY_RUNNER выполняюся запросы
    private SQLHelper() {
    } //утилитный приватный конструктор
    private static Connection getConnect() throws SQLException {
        return DriverManager.getConnection(System.getProperty("db.url"),"app","pass");
    } //метод который умеет возвращать подрлючение к БД
    @SneakyThrows
    public static DataHelper.VerificationCode getVerificationCode() {
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        var conn = getConnect();
        var code = QUERY_RUNNER.query(conn, codeSQL, new ScalarHandler<String>());
        return new DataHelper.VerificationCode(code);
    }
    @SneakyThrows
    public static void cleanDataBase() {
        var connection = getConnect();
        QUERY_RUNNER.execute(connection,"DELETE FROM auth_codes");
        QUERY_RUNNER.execute(connection,"DELETE FROM card_transactions");
        QUERY_RUNNER.execute(connection,"DELETE FROM cards");
        QUERY_RUNNER.execute(connection,"DELETE FROM users");
    } //запрос для очистки таблиц всех БД
    @SneakyThrows
    public static void cleanAuthCode() {
        var connection = getConnect();
        QUERY_RUNNER.execute(connection,"DELETE FROM auth_codes");
    } //метод для очистки 1 таблицы, сбрасываем счетчик валидных логинов. 4 раз логин не работает
}