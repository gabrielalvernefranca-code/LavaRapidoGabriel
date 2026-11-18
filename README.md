# LavaRapidoGabriel — Sistema de Lava Rápido (Java Swing / MVC)

Projeto acadêmico de cadastro de clientes, serviços, veículos e agendamentos.

## Requisitos
- Java 17 ou superior
- NetBeans com suporte a Maven (ou terminal com Maven instalado)

## Executar no NetBeans
1. Extraia o ZIP e abra a pasta que contém `pom.xml` em **File > Open Project**.
2. Execute a classe `br.com.lavarapido.Main` (ou escolha essa classe ao clicar em Run).

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
- Documentação: `docs/Documentacao_Lava_Rapido_ABNT.docx`.
- Diagramas: `docs/diagramas-uml.md`.
