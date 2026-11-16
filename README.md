# CifraVernam

API REST em Java (Spring Boot) que cifra e decifra mensagens usando a **Cifra de Vernam**.

**Integrantes:** Enzo Eiji Banin e Mateus Gois

## Como funciona

A cifra aplica a operação XOR, bit a bit, entre cada caractere do texto e o caractere correspondente da chave.

- A **chave deve ter exatamente o mesmo tamanho do texto**, como na definição da Cifra de Vernam.
- O texto cifrado é devolvido como uma sequência de bits (`0` e `1`), com 8 bits por caractere, ou seja, 8 vezes o tamanho da chave.
- Use apenas caracteres ASCII (letras sem acento, números e símbolos do teclado).

Exemplo: o texto `OLA` com a chave `CHT` gera `000011000000010000010101`.

## Requisitos

- Java 17 ou superior
- Internet na primeira execução (o Maven Wrapper baixa o Maven e as dependências)
- Porta `8080` livre

Não é necessário instalar o Maven: o projeto inclui o Maven Wrapper (`mvnw`).

## Como executar a API

1. Abra um terminal na pasta `segi` (a pasta que contém o `pom.xml`).
2. Execute:

   **Windows**

   ```
   .\mvnw.cmd spring-boot:run
   ```

   **Linux / macOS**

   ```
   ./mvnw spring-boot:run
   ```

3. Quando aparecer a linha `Started SegiApplication`, a API estará no ar em `http://localhost:8080`.
4. Para encerrar, pressione `Ctrl + C`.

> Se aparecer erro como `release version 17 not supported` ou `JAVA_HOME`, o Java do computador é anterior ao 17 ou a variável `JAVA_HOME` aponta para uma versão antiga. Aponte `JAVA_HOME` para um JDK 17 ou superior e tente novamente.

## Endpoints

Os dois endpoints aceitam apenas **POST** com corpo em JSON (`Content-Type: application/json`). Abrir os endereços no navegador mostra `405 Method Not Allowed`, o que é esperado, pois o navegador usa GET.

### POST `/cifrar`

Recebe o texto claro e a chave (mesmo tamanho, máximo 255 caracteres) e devolve o texto cifrado em bits.

```json
{ "textoClaro": "OLA", "chave": "CHT" }
```

Resposta (`200 OK`):

```json
{ "textoCifrado": "000011000000010000010101" }
```

### POST `/decifrar`

Recebe o texto cifrado em bits (máximo 2048 caracteres) e a chave, e devolve o texto claro.

```json
{ "textoCifrado": "000011000000010000010101", "chave": "CHT" }
```

Resposta (`200 OK`):

```json
{ "textoClaro": "OLA" }
```

## Exemplos de chamada

**PowerShell (Windows)**

```powershell
Invoke-RestMethod -Uri http://localhost:8080/cifrar -Method Post -ContentType "application/json" -Body '{"textoClaro":"OLA","chave":"CHT"}'

Invoke-RestMethod -Uri http://localhost:8080/decifrar -Method Post -ContentType "application/json" -Body '{"textoCifrado":"000011000000010000010101","chave":"CHT"}'
```

**curl (Linux, macOS ou Git Bash)**

```bash
curl -X POST http://localhost:8080/cifrar \
  -H "Content-Type: application/json" \
  -d '{"textoClaro":"OLA","chave":"CHT"}'

curl -X POST http://localhost:8080/decifrar \
  -H "Content-Type: application/json" \
  -d '{"textoCifrado":"000011000000010000010101","chave":"CHT"}'
```

## Documentação (Swagger UI)

Com a API em execução, a documentação interativa fica disponível em:

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- JSON OpenAPI: `http://localhost:8080/v3/api-docs`

A versão hospedada também está no ar, com a mesma documentação:

- Swagger UI: https://segi-cifravernam.onrender.com/swagger-ui/index.html
- API: https://segi-cifravernam.onrender.com

## Erros

Requisições inválidas retornam **400 Bad Request** com um corpo JSON no formato:

```json
{ "erro": "mensagem explicando o problema" }
```

Situações que geram erro 400:

| Situação | Endpoint |
|---|---|
| Texto e chave com tamanhos diferentes | `/cifrar` |
| Texto cifrado com tamanho diferente de 8 bits por caractere da chave | `/decifrar` |
| Texto cifrado com caracteres diferentes de `0` e `1` | `/decifrar` |
| Campo vazio ou ausente (`textoClaro`, `textoCifrado` ou `chave`) | ambos |
| `textoClaro` ou `chave` com mais de 255 caracteres | ambos |
| `textoCifrado` com mais de 2048 caracteres | `/decifrar` |

## Testes automatizados

Na pasta `segi`:

```
.\mvnw.cmd test
```

(no Linux ou macOS: `./mvnw test`)

Os testes cobrem a cifragem, o ciclo completo cifrar/decifrar e os casos de erro.

## Estrutura do projeto

```
segi/
├── pom.xml
└── src/
    ├── main/java/com/segi/
    │   ├── SegiApplication.java      # classe principal
    │   ├── config/OpenApiConfig.java # configuração do OpenAPI/Swagger
    │   ├── controller/               # endpoints REST e tratamento de erros
    │   ├── dto/                      # objetos de requisição e resposta
    │   └── service/CifraService.java # lógica de cifrar e decifrar (XOR)
    └── test/java/com/segi/service/   # testes unitários
```
