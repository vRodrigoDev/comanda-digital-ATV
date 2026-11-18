# ComandaDigital — Modelagem de Domínio e Arquitetura

Sistema de gestão de mesas, comandas e cardápio digital. Esta etapa contempla a modelagem rica do domínio com JPA/Hibernate, o diagrama de classes UML e a definição arquitetural do projeto.

## Arquitetura Escolhida: Arquitetura em Camadas (Layered Architecture)

Foi adotada a **Arquitetura em Camadas** tradicional do Spring Boot:
1. **Camada de Domínio (`domain`)**: Entidades JPA, enums, regras de negócio intrínsecas e validações de estado.
2. **Camada de Persistência (`repository`)**: Interfaces Spring Data JPA (etapa futura).
3. **Camada de Serviço (`service`)**: Orquestração de casos de uso e transações (etapa futura).
4. **Camada de Apresentação/API (`controller`)**: Endpoints REST e DTOs (etapa futura).

### Justificativa da Escolha
* **Necessidades do Negócio:** Exige consistência transacional forte (cálculo automático de totais, bloqueio de edição em comandas fechadas e controle de ocupação de mesas), atendida pelo modelo de domínio rico com JPA.
* **Complexidade do Sistema:** Sistema de porte enxuto, onde arquiteturas distribuídas ou muito cerimoniosas (Hexagonal/Clean) adicionariam complexidade desnecessária.
* **Características da Equipe:** Desenvolvimento individual, priorizando produtividade, manutenção simples e aderência aos padrões do Spring Boot.

## Organização do Código

* `domain/enums/StatusComanda.java`: Estados da comanda (`ABERTA`, `FECHADA`, `CANCELADA`).
* `domain/Mesa.java`: Controle de ocupação física (`@OneToOne` com `Comanda`).
* `domain/Comanda.java`: Agregado principal e cálculo de total (`@OneToMany` com `ItemComanda`).
* `domain/ItemComanda.java`: Registro de preço e quantidade (`@ManyToOne` com `Produto` e `Comanda`).
* `domain/Produto.java`: Catálogo de itens (`@ManyToMany` com `TagProduto`).
* `domain/TagProduto.java`: Etiquetas de categorização do cardápio.

## Diagrama de Classes UML

Disponível em [`docs/diagrama-classes.png`](docs/diagrama-classes.png).

## Instruções para Execução

1. Clonar o repositório: `git clone https://github.com/vRodrigoDev/comanda-digital-ATV.git`
2. Abrir em uma IDE Java (IntelliJ, Eclipse ou VS Code) com suporte a Maven e Java 17+.
