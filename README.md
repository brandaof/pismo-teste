# Teste
Teste feito para uma vaga de emprego na empresa Pismo

Este teste foi especificado, modelado e implementado em 6 dias.

Link do Teste : https://gist.github.com/leosilvadev/2699585825b2dad4ee348bca855595fe

# Breve descrição do projeto:

#### Núcleo

- core-api: Base para as APIs.
- persistence-api: Modulo de persistência
- persistence-api-hibernate: Implementação, hibernate, do modulo de persistência.

#### API compras

- compras-api-spec: Especificação da API compras.
- compras-api: API de compras.
- compras-api-client: Cliente da API de compras.
- compras-api-persistence: Modulo de persistência da API compras.
- compras-api-hibernate: Implementação do modulo de persistência da API compras.

#### API produtos

- produtos-api-spec: especificação da API produtos.
- produtos-api: API de produtos.
- produtos-api-client: Cliente da API de produtos.
- produtos-api-persistence: Modulo de persistência da API produtos.
- produtos-api-hibernate: Implementação do modulo de persistência da API produtos.

Se a API produtos estiver off-line e o cliente já estiver selecionado os produtos, a compra será feita com status pendente. Depois o sistema poderia dar continuidade no processo de compra sem prejuízo.

Se as APIs perderem acesso ao banco de dados, elas ficarão inoperantes. 

As APIs são independentes de banco de dados. Para usar um banco de dados NoSQL, por exemplo, basta implementar os módulos produtos-api-persistence e compras-api-persistence.

Existe um teste na API compras que executa as duas APIs, compras e produtos, simulando um processo real de compra. Para testar tem que usar algum banco de dados sql. Fiz o teste com mysql. O teste em questão é org.brandao.pismo.compras.teste.CompraAPIServerTest.testRegistryProduct().

