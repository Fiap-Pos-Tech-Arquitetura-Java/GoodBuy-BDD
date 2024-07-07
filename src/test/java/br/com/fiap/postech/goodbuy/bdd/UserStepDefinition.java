package br.com.fiap.postech.goodbuy.bdd;

import br.com.fiap.postech.goodbuy.helper.UserHelper;
import br.com.fiap.postech.goodbuy.model.User;
import br.com.fiap.postech.goodbuy.security.enums.UserRole;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class UserStepDefinition extends BaseStepDefinition {

    private Response response;
    private User userResposta;

    @Quando("registrar um novo user")
    public User registrar_um_novo_user() {
        var userRequisicao = UserHelper.getUser(false, UserRole.ADMIN);
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(userRequisicao)
                .when()
                .post(ENDPOINT_API_USER);
        return response.then().extract().as(User.class);
    }
    @Entao("o user e registrado com sucesso")
    public void o_user_e_registrado_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.CREATED.value());
    }
    @Entao("user deve ser apresentado")
    public void deve_ser_apresentado() {
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/user.schema.json"));
    }

    @Dado("que um user ja foi registrado")
    public void que_um_user_ja_foi_publicado() {
        userResposta = registrar_um_novo_user();
    }

    @Quando("efetuar a busca do user")
    public void efetuar_a_busca_do_user() {
        when()
                .get(ENDPOINT_API_USER + "/{id}", userResposta.id());
    }
    @Entao("o user e exibido com sucesso")
    public void o_user_e_exibido_com_sucesso() {
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/user.schema.json"));
    }

    @Quando("efetuar uma requisicao para alterar user")
    public void efetuar_uma_requisicao_para_alterar_user() {
        var novoUser = new User(
                userResposta.id(),
                userResposta.login(),
                userResposta.name() + "XXX",
                userResposta.cpf(),
                userResposta.password(),
                userResposta.role()
        );
        response =
                given()
                        .header(HttpHeaders.AUTHORIZATION, UserHelper.getToken(userResposta))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(novoUser)
                .when()
                        .put(ENDPOINT_API_USER + "/{id}", userResposta.id());
    }
    @Entao("o user e atualizado com sucesso")
    public void o_user_e_atualizado_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.ACCEPTED.value());
    }

    @Quando("requisitar a remocao do user")
    public void requisitar_a_remocao_do_user() {
        response =
                given()
                        .header(HttpHeaders.AUTHORIZATION, UserHelper.getToken(userResposta))
                .when()
                        .delete(ENDPOINT_API_USER + "/{id}", userResposta.id());
    }
    @Entao("o user e removido com sucesso")
    public void o_user_e_removido_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
