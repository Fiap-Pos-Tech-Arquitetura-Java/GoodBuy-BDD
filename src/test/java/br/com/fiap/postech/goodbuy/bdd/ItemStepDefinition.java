package br.com.fiap.postech.goodbuy.bdd;

import br.com.fiap.postech.goodbuy.helper.ItemHelper;
import br.com.fiap.postech.goodbuy.helper.UserHelper;
import br.com.fiap.postech.goodbuy.model.Item;
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
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ItemStepDefinition extends BaseStepDefinition {

    private Response response;
    private User userAdmResposta;
    private User userResposta;
    private Item itemResposta;

    public User registrar_um_novo_user(UserRole userRole) {
        var userRequisicao = UserHelper.getUser(false, userRole);
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(userRequisicao)
                .when()
                .post(ENDPOINT_API_USER);
        return response.then().extract().as(User.class);
    }

    @Dado("que um user adm ja foi registrado para registrar um item")
    public void que_um_user_adm_ja_foi_registrado_para_registrar_um_item() {
        userAdmResposta = registrar_um_novo_user(UserRole.ADMIN);
    }

    @Dado("que um user ja foi registrado para registrar um item")
    public void que_um_user_ja_foi_registrado_para_registrar_um_item() {
        userResposta = registrar_um_novo_user(UserRole.ADMIN);
    }

    @Quando("registrar um novo item")
    public Item registrar_um_novo_item() {
        var item = ItemHelper.getItem(false);
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, UserHelper.getToken(userAdmResposta))
                .body(item)
                .when()
                .post(ENDPOINT_API_ITEM);
        return response.then().extract().as(Item.class);
    }
    @Entao("o item e registrado com sucesso")
    public void o_item_e_registrado_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.CREATED.value());
    }
    @Entao("item deve ser apresentado")
    public void item_deve_ser_apresentado() {
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/item.schema.json"));
    }

    @Dado("que um item ja foi registrado")
    public void que_um_item_ja_foi_registrado() {
        itemResposta = registrar_um_novo_item();
    }

    @Quando("efetuar a busca do item")
    public void efetuar_a_busca_do_item() {
        given()
                .header(HttpHeaders.AUTHORIZATION, UserHelper.getToken(userResposta))
        .when()
                .get(ENDPOINT_API_ITEM + "/{id}", itemResposta.id());
    }
    @Entao("o item e exibido com sucesso")
    public void o_item_e_exibido_com_sucesso() {
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/item.schema.json"));
    }

    @Quando("efetuar uma requisicao para alterar item")
    public void efetuar_uma_requisicao_para_alterar_item() {
        var novoItem = new Item(
                itemResposta.id(),
                itemResposta.nome(),
                itemResposta.preco(),
                itemResposta.descricao() + "XXX",
                itemResposta.categoria(),
                itemResposta.urlImagem(),
                itemResposta.quantidade()
        );
        response =
                given()
                        .header(HttpHeaders.AUTHORIZATION, UserHelper.getToken(userAdmResposta))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(novoItem)
                .when()
                        .put(ENDPOINT_API_ITEM + "/{id}", itemResposta.id());
    }
    @Entao("o item e atualizado com sucesso")
    public void o_item_e_atualizado_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.ACCEPTED.value());
    }

    @Quando("requisitar a remocao do item")
    public void requisitar_a_remocao_do_item() {
        response =
                given()
                        .header(HttpHeaders.AUTHORIZATION, UserHelper.getToken(userAdmResposta))
                .when()
                        .delete(ENDPOINT_API_ITEM + "/{id}", itemResposta.id());
    }
    @Entao("o item e removido com sucesso")
    public void o_item_e_removido_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
