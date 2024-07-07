#language: pt

  Funcionalidade: Payment
    @smoke @low
    Cenario: Exibir o Summary de um ShopCart
      Dado que existe um user adm registrado para adicionar um item em um shop-cart para um payment
      Dado que existe um item cadastrado para adicionar a um shop-cart para um payment
      Dado que existe um user registrado para adicionar a um shop-cart  para um payment
      Dado que existe um shop-cart cadastrado para um payment
      Quando exibir o Summary de um ShopCart
      Entao o summary e consultado com sucesso
      E summary deve ser apresentado
    @smoke @low
    Cenario: Realizar um payment
      Dado que existe um user adm registrado para adicionar um item em um shop-cart para um payment
      Dado que existe um item cadastrado para adicionar a um shop-cart para um payment
      Dado que existe um user registrado para adicionar a um shop-cart  para um payment
      Dado que existe um shop-cart cadastrado para um payment
      Quando realizar o payment de um ShopCart
      Entao o payment e registrado com sucesso
      E payment deve ser apresentado