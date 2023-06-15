package com.serverest.api.testcases;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class ProductTest {

    String body = "{\n" +
            "  \"nome\": \"Mouse Gamer\",\n" +
            "  \"preco\": 320,\n" +
            "  \"descricao\": \"Mouse\",\n" +
            "  \"quantidade\": 200\n" +
            "}";

    String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImJlbGdhQHFhLmNvbS5iciIsInBhc3N3b3JkIjoiMTIzNDU2IiwiaWF0IjoxNjg2Nzg5Mzc5LCJleHAiOjE2ODY3ODk5Nzl9.WIMM_duimGgfwa6wzcLyYxZxv9NM7eUCZmVQ93TKvGo";

    @Test
    public void shouldBeAbleToAddProduct() {

        given()
                .baseUri("https://serverest.dev")
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(body)
                .when().post("/produtos")
                .then()
                .log().all()
                .assertThat().statusCode(201)
                .assertThat().body("message", equalTo("Cadastro realizado com sucesso"))
                .assertThat().body("_id", not(equalTo(null)));
    }

    @Test
    public void shouldNotBeAbleToAddProductIfIsCompletedIsMissing() {

        String body = "{\n" +
                "  \"nome\": \"Mouse Gamer\",\n" +
                "  \"preco\": 320,\n" +
                "  \"descricao\": \"Mouse\",\n" +
                "  \"quantidade\": 200\n" +
                "}";

        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImJlbGdhQHFhLmNvbS5iciIsInBhc3N3b3JkIjoiMTIzNDU2IiwiaWF0IjoxNjg2Nzg5Mzc5LCJleHAiOjE2ODY3ODk5Nzl9.WIMM_duimGgfwa6wzcLyYxZxv9NM7eUCZmVQ93TKvGo";

        given()
                .baseUri("https://serverest.dev")
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(body)
                .when().post("/produtos")
                .then()
                .log().all()
                .assertThat().statusCode(400)
                .assertThat().body("message", equalTo("Já existe produto com esse nome"));
    }
}
