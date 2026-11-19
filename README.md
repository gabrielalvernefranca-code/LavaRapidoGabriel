# LavaRapidoGabriel — Sistema de Lava Rápido (Java Swing / MVC)

Projeto acadêmico de cadastro de clientes, serviços, veículos e agendamentos.

## Requisitos
- Java 17 ou superior
- NetBeans com suporte a Maven (ou terminal com Maven instalado)

##### Como executar o projeto no NetBeans

O sistema foi desenvolvido em Java, utilizando o NetBeans e o Maven para organizar o projeto.

Para executar, primeiro é necessário baixar os arquivos disponíveis neste repositório do GitHub. Depois, basta abrir o NetBeans, selecionar a opção **File > Open Project** e escolher a pasta do projeto, onde está localizado o arquivo `pom.xml`.

Com o projeto aberto, é só executar a classe `Main`, que fica no pacote `br.com.lavarapido`. O programa abrirá a tela principal, permitindo acessar os cadastros de clientes, serviços, veículos e a agenda de atendimentos.

## Executar pelo terminal
```bash
mvn compile
java -cp target/classes br.com.lavarapido.Main
```

## Funcionalidades
- Clientes: adicionar, editar, excluir e listar.
- Serviços: adicionar, editar, excluir e listar.
- Veículos (extensão proposta para ponto extra): cadastrar, editar, excluir e associar a clientes.
- Agenda: criar, editar, concluir e cancelar agendamentos.
- Filtro por dia ou semana (semana de segunda a domingo).
- Bloqueio de dois agendamentos ativos no mesmo horário.

## Observações
- Os dados ficam apenas **em memória** e são perdidos ao fechar o programa.
- A placa continua aparecendo no cadastro do cliente por compatibilidade com o projeto original. A aba Veículos oferece cadastro adicional e vínculo com cliente.
- Não há persistência em banco de dados. As regras de negócio estão nas classes do pacote `service`. Cada módulo possui uma classe View; a `TelaPrincipal` coordena os eventos e chama os controllers.
- Documentação: docs/LavaRapidoGabriel.pdf
- Diagramas: docs/Diagrama_Classes_LavaRapidoGabriel.drawio
- docs/Diagrama_Sequencia_LavaRapidoGabriel.drawio
