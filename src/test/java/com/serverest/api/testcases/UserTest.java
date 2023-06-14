package com.serverest.api.testcases;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserTest {

    @Test
    public void shouldBeAbleToRegister() {

        String body = "{\n" +
                "  \"nome\": \"Falcao Belga\",\n" +
                "  \"email\": \"belga@qa.com.br\",\n" +
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
                .assertThat().body("message", equalTo("Cadastro realizado com sucesso"));
    }
}
