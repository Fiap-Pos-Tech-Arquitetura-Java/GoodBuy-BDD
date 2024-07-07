package br.com.fiap.postech.goodbuy.bdd;

import br.com.fiap.postech.goodbuy.helper.ItemHelper;
import br.com.fiap.postech.goodbuy.helper.UserHelper;
import br.com.fiap.postech.goodbuy.model.Item;
import br.com.fiap.postech.goodbuy.model.ShopCart;
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

public class ShopCartStepDefinition extends BaseStepDefinition {

    private Response response;
    private User userAdmResposta;
    private User userResposta;
    private Item itemResposta;
    private ShopCart shopCartResposta;

    public User registrar_um_novo_user(UserRole userRole) {
        var userRequisicao = UserHelper.getUser(false, userRole);
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(userRequisicao)
                .when()
                .post(ENDPOINT_API_USER);
        return response.then().extract().as(User.class);
    }

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

    @Dado("que existe um user adm registrado para adicionar um item")
    public void que_existe_um_user_adm_registrado_para_adicionar_um_item() {
        userAdmResposta = registrar_um_novo_user(UserRole.ADMIN);
    }
    @Dado("que existe um item cadastrado para adicionar a um shop-cart")
    public void que_existe_um_item_cadastrado_para_adicionar_a_um_shop_cart() {
        itemResposta = registrar_um_novo_item();
    }
    @Dado("que existe um user registrado para adicionar a um shop-cart")
    public void que_existe_um_user_registrado_para_adicionar_a_um_shop_cart() {
        userResposta = registrar_um_novo_user(UserRole.USER);
    }
    @Quando("adicionar itens a um shop-cart")
    public ShopCart adicionar_itens_a_um_shop_cart() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, UserHelper.getToken(userResposta))
                .body(itemResposta)
                .when()
                .post(ENDPOINT_API_SHOPCART + "/addItem");
        return response.then().extract().as(ShopCart.class);
    }
    @Entao("o shop-cart e registrado com sucesso")
    public void o_shop_cart_e_registrado_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value());
    }
    @Entao("shop-cart deve ser apresentado")
    public void shop_cart_deve_ser_apresentado() {
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/shop-cart.schema.json"));
    }

    @Dado("que existe um shop-cart cadastrado")
    public void que_existe_um_shop_cart_cadastrado() {
        shopCartResposta = adicionar_itens_a_um_shop_cart();
    }

    @Quando("remover itens de um shop-cart")
    public ShopCart remover_itens_de_um_shop_cart() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, UserHelper.getToken(userResposta))
                .body(itemResposta)
                .when()
                .post(ENDPOINT_API_SHOPCART + "/removeItem");
        return response.then().extract().as(ShopCart.class);
    }

    @Quando("exibir itens de um shop-cart")
    public ShopCart exibir_itens_de_um_shop_cart() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, UserHelper.getToken(userResposta))
                .when()
                .get(ENDPOINT_API_SHOPCART);
        return response.then().extract().as(ShopCart.class);
    }
}
