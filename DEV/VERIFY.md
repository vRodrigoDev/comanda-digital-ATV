# Verificação — Endpoints REST de Catálogo e Tags

Data: 2026-11-18 (task_23fd7ee147c8 / dispatch ctx_fbc1e8c19353)

## Contexto

Ao iniciar o PASSO 3, o workspace continha apenas as entidades de domínio e a classe principal
(7 fontes). Não havia controllers, DTOs, repositories, services nem o starter web no `pom.xml` —
ou seja, a implementação atribuída ao worker anterior (PASSO 1 + PASSO 2) não estava presente.
Para cumprir os critérios de aceite, a camada REST foi implementada nesta tarefa.

## 1. Build

Comando: `mvn -B -ntp clean compile` (JAVA_HOME = JDK 21 do IntelliJ JBR, Maven 3.9.16 do wrapper `.m2`).

```
[INFO] Compiling 18 source files with javac [debug parameters release 21] to target\classes
[INFO] BUILD SUCCESS
[INFO] Total time:  15.838 s
```

## 2. Subida da aplicação

Comando: `mvn -B -ntp spring-boot:run`

```
 :: Spring Boot ::                (v4.1.2)
 Starting ComandadigitalApplication using Java 21.0.8 ...
 Found 2 JPA repository interfaces.
 Tomcat started on port 8080 (http) with context path '/'
 Started ComandadigitalApplication in 5.442 seconds
```

## 3. Fluxo curl.exe (Windows)

Corpo JSON enviado via arquivo temporário com `--data-binary @arquivo`, porque o PowerShell
remove as aspas quando o JSON é passado diretamente na linha de comando.

### 1) POST /produtos — 201 + Location

```
HTTP/1.1 201
Location: http://localhost:8080/produtos/1
Content-Type: application/json

{"id":1,"nome":"Peixada Completa","preco":85.00,"disponivel":true,"tags":[]}
```

### 2) POST /produtos — 201 + Location

```
HTTP/1.1 201
Location: http://localhost:8080/produtos/2
Content-Type: application/json

{"id":2,"nome":"Cerveja 600ml","preco":14.00,"disponivel":true,"tags":[]}
```

### 3) POST /produtos/1/tags — associar tag

```
HTTP/1.1 200
Content-Type: application/json

{"id":1,"nome":"Peixada Completa","preco":85.00,"disponivel":true,"tags":["BEBIDA GELADA"]}
```

### 4) PATCH /produtos/2/pausar — pausar venda

```
HTTP/1.1 200
Content-Type: application/json

{"id":2,"nome":"Cerveja 600ml","preco":14.00,"disponivel":false,"tags":[]}
```

### 5) GET /produtos — apenas disponíveis

```
HTTP/1.1 200
Content-Type: application/json

[{"id":1,"nome":"Peixada Completa","preco":85.00,"disponivel":true,"tags":["BEBIDA GELADA"]}]
```

O produto 2 (pausado) não aparece na listagem. Critério atendido.

## 4. Arquivos criados/modificados

Modificado:
- `pom.xml` (adição de `spring-boot-starter-web`)

Criados:
- `src/main/resources/application.properties`
- `src/main/java/br/com/comandadigital/controller/ProdutoController.java`
- `src/main/java/br/com/comandadigital/controller/TagController.java`
- `src/main/java/br/com/comandadigital/dto/ProdutoRequest.java`
- `src/main/java/br/com/comandadigital/dto/ProdutoResponse.java`
- `src/main/java/br/com/comandadigital/dto/TagRequest.java`
- `src/main/java/br/com/comandadigital/dto/TagResponse.java`
- `src/main/java/br/com/comandadigital/exception/ApiExceptionHandler.java`
- `src/main/java/br/com/comandadigital/exception/ResourceNotFoundException.java`
- `src/main/java/br/com/comandadigital/repository/ProdutoRepository.java`
- `src/main/java/br/com/comandadigital/repository/TagProdutoRepository.java`
- `src/main/java/br/com/comandadigital/service/CatalogoService.java`
- `DEV/VERIFY.md` (este relatório)

## 5. Git

Nenhum commit ou push foi feito. `git status -sb` mostra
`## feature/catalogo-produtos` sem ahead/behind; os três últimos commits permanecem
`971b92b`, `e39494b`, `75af9e3`. As alterações seguem como untracked/modified no working tree.

## 6. Estado final

A aplicação de teste foi encerrada após a validação (porta 8080 liberada). O `BUILD FAILURE`
reportado pelo shell em background é consequência esperada do `Stop-Process` do PID 16404.
