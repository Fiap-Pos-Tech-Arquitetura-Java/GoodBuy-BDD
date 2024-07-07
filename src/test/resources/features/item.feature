#language: pt

Funcionalidade: Item
  @smoke @high
  Cenario: Registrar Item
    Dado que um user adm ja foi registrado para registrar um item
    Quando registrar um novo item
    Entao o item e registrado com sucesso
    E item deve ser apresentado
  @smoke @low
  Cenario: Buscar Item
    Dado que um user adm ja foi registrado para registrar um item
    Dado que um user ja foi registrado para registrar um item
    Dado que um item ja foi registrado
    Quando efetuar a busca do item
    Entao o item e exibido com sucesso
  @smoke
  Cenario: Alterar Item
    Dado que um user adm ja foi registrado para registrar um item
    Dado que um item ja foi registrado
    Quando efetuar uma requisicao para alterar item
    Entao o item e atualizado com sucesso
    E item deve ser apresentado
  @high
  Cenario: Remover Item
    Dado que um user adm ja foi registrado para registrar um item
    Dado que um item ja foi registrado
    Quando requisitar a remocao do item
    Entao o item e removido com sucesso