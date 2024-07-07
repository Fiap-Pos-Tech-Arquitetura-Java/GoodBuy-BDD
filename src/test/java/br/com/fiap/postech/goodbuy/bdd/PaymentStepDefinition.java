package br.com.fiap.postech.goodbuy.bdd;

import br.com.fiap.postech.goodbuy.helper.ItemHelper;
import br.com.fiap.postech.goodbuy.helper.UserHelper;
import br.com.fiap.postech.goodbuy.model.*;
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

public class PaymentStepDefinition extends BaseStepDefinition {

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

    public ShopCart adicionar_itens_a_um_shop_cart() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, UserHelper.getToken(userResposta))
                .body(itemResposta)
                .when()
                .post(ENDPOINT_API_SHOPCART + "/addItem");
        return response.then().extract().as(ShopCart.class);
    }

    @Dado("que existe um user adm registrado para adicionar um item em um shop-cart para um payment")
    public void que_existe_um_user_adm_registrado_para_adicionar_um_item_em_um_shop_cart_para_um_payment() {
        userAdmResposta = registrar_um_novo_user(UserRole.ADMIN);
    }
    @Dado("que existe um item cadastrado para adicionar a um shop-cart para um payment")
    public void que_existe_um_item_cadastrado_para_adicionar_a_um_shop_cart_para_um_payment() {
        itemResposta = registrar_um_novo_item();
    }
    @Dado("que existe um user registrado para adicionar a um shop-cart  para um payment")
    public void que_existe_um_user_registrado_para_adicionar_a_um_shop_cart_para_um_payment() {
        userResposta = registrar_um_novo_user(UserRole.USER);
    }
    @Dado("que existe um shop-cart cadastrado para um payment")
    public void que_existe_um_shop_cart_cadastrado_para_um_payment() {
        shopCartResposta = adicionar_itens_a_um_shop_cart();
    }

    @Quando("exibir o Summary de um ShopCart")
    public Summary exibir_o_summary_de_um_shop_cart() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, UserHelper.getToken(userResposta))
                .when()
                .get(ENDPOINT_API_PAYMENT);
        return response.then().extract().as(Summary.class);
    }

    @Entao("o summary e consultado com sucesso")
    public void o_summary_e_consultado_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value());
    }
    @Entao("summary deve ser apresentado")
    public void summary_deve_ser_apresentado() {
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/summary.schema.json"));
    }

    @Quando("realizar o payment de um ShopCart")
    public Payment realizar_o_payment_de_um_shop_cart() {
        var payment = new Payment(null, "PIX", null);
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, UserHelper.getToken(userResposta))
                .body(payment)
                .when()
                .post(ENDPOINT_API_PAYMENT);
        return response.then().extract().as(Payment.class);
    }
    @Entao("o payment e registrado com sucesso")
    public void o_payment_e_registrado_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value());
    }
    @Entao("payment deve ser apresentado")
    public void payment_deve_ser_apresentado() {
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/payment.schema.json"));
    }
}
