package com.serverest.api.testcases;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class UserTest {

    @Test
    public void shouldBeAbleToRegister() {

        String body = "{\n" +
                "  \"nome\": \"Falcao Bell\",\n" +
                "  \"email\": \"bell@qa.com.br\",\n" +
                "  \"password\": \"123456\",\n" +
                "  \"administrador\": \"true\"\n" +
                "}";

        given()
                .baseUri("https://serverest.dev")
                .contentType(ContentType.JSON)
                .body(body)
                .when().post("/usuarios")
                .then()
                .log().all()
                .assertThat().statusCode(201)
                .assertThat().body("message", equalTo("Cadastro realizado com sucesso"))
                .assertThat().body("_id", not(equalTo(null)));;
    }

    @Test
    public void shouldNotBeAbleToRegisterWithTheSameEmail() {

        String body = "{\n" +
                "  \"nome\": \"Falcao Bell\",\n" +
                "  \"email\": \"bell@qa.com.br\",\n" +
                "  \"password\": \"123456\",\n" +
                "  \"administrador\": \"true\"\n" +
                "}";

        given()
                .baseUri("https://serverest.dev")
                .contentType(ContentType.JSON)
                .body(body)
                .when().post("/usuarios")
                .then()
                .log().all()
                .assertThat().statusCode(400)
                .assertThat().body("message", equalTo("Este email já está sendo usado"));
    }

    @Test
    public void shouldBeAbleToLogin() {

        String body = "{\n" +
                "  \"email\": \"bell@qa.com.br\",\n" +
                "  \"password\": \"123456\"\n" +
                "}";

        given()
                .baseUri("https://serverest.dev")
                .contentType(ContentType.JSON)
                .body(body)
                .when().post("/login")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .assertThat().body("message", equalTo("Login realizado com sucesso"))
                .assertThat().body("authorization", not(equalTo(null)));
    }

    @Test
    public void shouldNotBeAbleToLoginIfThePasswordIsNotCorrect() {

        String body = "{\n" +
                "  \"email\": \"bell@qa.com.br\",\n" +
                "  \"password\": \"12345\"\n" +
                "}";

        given()
                .baseUri("https://serverest.dev")
                .contentType(ContentType.JSON)
                .body(body)
                .when().post("/login")
                .then()
                .log().all()
                .assertThat().statusCode(401)
                .assertThat().body("message", equalTo("Email e/ou senha inválidos"));
    }
}
