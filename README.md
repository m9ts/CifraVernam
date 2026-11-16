# CifraVernam

API REST em Java (Spring Boot) que cifra e decifra mensagens usando a **Cifra de Vernam**.

**Integrantes:** Enzo Eiji Banin e Mateus Gois

## Como funciona

A cifra aplica a operação XOR, bit a bit, entre cada caractere do texto e o caractere correspondente da chave.

- A **chave deve ter exatamente o mesmo tamanho do texto**, como na definição da Cifra de Vernam.
- O texto cifrado é devolvido como uma sequência de bits (`0` e `1`), com 8 bits para cada caractere. Por isso, o texto cifrado tem sempre 8 vezes o tamanho da chave.
- Use apenas caracteres da tabela ASCII (letras sem acento, números e símbolos do teclado). Caracteres fora do ASCII, como `ç` ou `é`, não são suportados.

Exemplo: o texto `OLA` com a chave `CHT` gera `000011000000010000010101`.

## Requisitos

- Java 17 ou superior
- Conexão com a internet na primeira execução (o Maven Wrapper baixa o Maven e as dependências)
- Porta `8080` livre

Não é necessário instalar o Maven, o projeto já inclui o Maven Wrapper (`mvnw`).

## Como executar a API

Abra um terminal na pasta `segi` (a pasta que contém o arquivo `pom.xml`) e execute:

**Windows**

```
.\mvnw.cmd spring-boot:run
```

**Linux / macOS**

```
./mvnw spring-boot:run
```

Quando aparecer a linha `Started SegiApplication`, a API estará disponível em `http://localhost:8080`.

Para encerrar, pressione `Ctrl + C` no terminal.

> Se aparecer um erro como `release version 17 not supported` ou `JAVA_HOME`, o Java do computador é anterior ao 17 ou a variável `JAVA_HOME` aponta para uma versão antiga. Aponte `JAVA_HOME` para um JDK 17 ou superior e execute o comando novamente.

## Endpoints

Os dois endpoints aceitam apenas o método **POST**, com corpo em JSON (`Content-Type: application/json`). Abrir os endereços no navegador mostra um erro `405 Method Not Allowed`, o que é esperado, pois o navegador usa GET.

### POST `/cifrar`

Recebe o texto claro e a chave (mesmo tamanho) e devolve o texto cifrado em bits.

Requisição:

```json
{
  "textoClaro": "OLA",
  "chave": "CHT"
}
```

Resposta (`200 OK`):

```json
{
  "textoCifrado": "000011000000010000010101"
}
```

### POST `/decifrar`

Recebe o texto cifrado (em bits) e a chave e devolve o texto claro.

Requisição:

```json
{
  "textoCifrado": "000011000000010000010101",
  "chave": "CHT"
}
```

Resposta (`200 OK`):

```json
{
  "textoClaro": "OLA"
}
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

## Erros

Requisições inválidas retornam o status **400 Bad Request** com um corpo JSON no formato:

```json
{
  "erro": "mensagem explicando o problema"
}
```

Situações que geram erro 400:

| Situação | Endpoint |
|---|---|
| Texto e chave com tamanhos diferentes | `/cifrar` |
| Texto cifrado com tamanho diferente de 8 bits para cada caractere da chave | `/decifrar` |
| Texto cifrado com caracteres diferentes de `0` e `1` | `/decifrar` |
| Campo vazio ou ausente (`textoClaro`, `textoCifrado` ou `chave`) | ambos |
| `textoClaro` ou `chave` com mais de 255 caracteres | ambos |
| `textoCifrado` com mais de 2048 caracteres | `/decifrar` |

Exemplo (texto com 3 caracteres e chave com 2):

```json
{
  "textoClaro": "OLA",
  "chave": "CH"
}
```

Resposta (`400 Bad Request`):

```json
{
  "erro": "O texto claro e a chave devem ter o mesmo tamanho!"
}
```

Exemplo (texto claro vazio):

```json
{
  "textoClaro": "",
  "chave": "A"
}
```

Resposta (`400 Bad Request`):

```json
{
  "erro": "O texto claro é obrigatório!"
}
```

## Testes automatizados

Na pasta `segi`:

```
.\mvnw.cmd test
```

(no Linux ou macOS: `./mvnw test`)

Os testes cobrem a cifragem, o ciclo completo cifrar e decifrar e os casos de erro.

## Estrutura do projeto

```
segi/
├── pom.xml
└── src/
    ├── main/java/com/segi/
    │   ├── SegiApplication.java      # classe principal
    │   ├── controller/               # endpoints REST e tratamento de erros
    │   ├── dto/                      # objetos de requisição e resposta
    │   └── service/CifraService.java # lógica de cifrar e decifrar (XOR)
    └── test/java/com/segi/service/   # testes unitários
```