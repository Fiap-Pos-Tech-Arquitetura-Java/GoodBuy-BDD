#language: pt

  Funcionalidade: ShopCart
    @smoke @high
    Cenario: Adicionar Itens a um ShopCart
      Dado que existe um user adm registrado para adicionar um item
      Dado que existe um item cadastrado para adicionar a um shop-cart
      Dado que existe um user registrado para adicionar a um shop-cart
      Quando adicionar itens a um shop-cart
      Entao o shop-cart e registrado com sucesso
      E shop-cart deve ser apresentado
    @smoke @high
    Cenario: Remover Itens de um ShopCart
      Dado que existe um user adm registrado para adicionar um item
      Dado que existe um item cadastrado para adicionar a um shop-cart
      Dado que existe um user registrado para adicionar a um shop-cart
      Dado que existe um shop-cart cadastrado
      Quando remover itens de um shop-cart
      Entao o shop-cart e registrado com sucesso
      E shop-cart deve ser apresentado
    @smoke @low
    Cenario: Exibir Itens de um ShopCart
      Dado que existe um user adm registrado para adicionar um item
      Dado que existe um item cadastrado para adicionar a um shop-cart
      Dado que existe um user registrado para adicionar a um shop-cart
      Dado que existe um shop-cart cadastrado
      Quando exibir itens de um shop-cart
      Entao o shop-cart e registrado com sucesso
      E shop-cart deve ser apresentado