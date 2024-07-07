#language: pt

  Funcionalidade: User
    @smoke @high
    Cenario: Registrar User
      Quando registrar um novo user
      Entao o user e registrado com sucesso
      E user deve ser apresentado
    @smoke @low
    Cenario: Buscar User
      Dado que um user ja foi registrado
      Quando efetuar a busca do user
      Entao o user e exibido com sucesso
    @smoke
    Cenario: Alterar User
      Dado que um user ja foi registrado
      Quando efetuar uma requisicao para alterar user
      Entao o user e atualizado com sucesso
      E user deve ser apresentado
    @high
    Cenario: Remover User
      Dado que um user ja foi registrado
      Quando requisitar a remocao do user
      Entao o user e removido com sucesso