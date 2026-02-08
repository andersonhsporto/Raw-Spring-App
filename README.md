# Raw Spring App - API REST

API REST desenvolvida com Spring Framework (sem Spring Boot), utilizando configuração XML e arquitetura multi-módulo Maven.

## Estrutura do Projeto

```
raw-spring-app/
├── pom.xml                     # POM parent
├── deploy.sh                   # Script de deploy automatizado
├── start-tomcat-dev.sh         # Script para iniciar Tomcat (dev)
├── start-tomcat-prod.sh        # Script para iniciar Tomcat (prod)
├── docker-compose.yaml         # Configuração Docker Compose
├── Dockerfile                  # Multi-stage build Docker
├── mysql/                      # Scripts SQL para inicialização
│   ├── 01_schema.sql
│   ├── 02_CreateRoom.sql
│   └── ...
├── configuration/              # Módulo de configuração
│   ├── pom.xml
│   └── src/main/
│       ├── java/
│       │   └── dev/spring/config/
│       │       └── EnvInitializer.java
│       └── resources/
│           ├── application.properties
│           └── spring/
│               ├── applicationContext.xml
│               ├── database-dev.xml
│               └── database-prod.xml
└── application/                # Módulo web (WAR)
    ├── pom.xml
    └── src/main/
        ├── java/
        │   └── dev/spring/
        │       ├── config/
        │       ├── controller/
        │       ├── mapper/
        │       ├── model/
        │       ├── repository/
        │       └── service/
        └── webapp/
            └── WEB-INF/
                ├── web.xml
                └── dispatcher-servlet.xml
```

## Profiles Maven

O projeto possui 2 profiles configurados:

### Profile: dev (padrão)
- Banco de dados: MySQL (base de desenvolvimento)
- URL: jdbc:mysql://localhost:3306/app_db
- Usuário: app_user
- Senha: app_pass

### Profile: prod
- Banco de dados: MySQL (base de produção)
- URL: jdbc:mysql://localhost:3306/app_db
- Usuário: app_user
- Senha: app_pass

## Configuração de Variáveis de Ambiente

As propriedades podem ser sobrescritas via variáveis de ambiente:

```bash
export SPRING_PROFILES_ACTIVE=prod
export DB_URL=jdbc:mysql://servidor:3306/banco
export DB_USERNAME=usuario
export DB_PASSWORD=senha
```

## Build

### Compilar com profile dev (padrão):
```bash
mvn clean package
```

### Compilar com profile prod:
```bash
mvn clean package -Pprod
```

### Compilar com variáveis de ambiente:
```bash
mvn clean package -Dspring.profiles.active=prod -Ddb.url=jdbc:mysql://... -Ddb.username=user -Ddb.password=pass
```

## Deploy

O arquivo WAR gerado estará em:
```
application/target/raw-spring-app-application.war
```

### Deploy Automatizado

Use o script de deploy fornecido:

```bash
# Deploy com profile dev (padrão)
./deploy.sh

# Deploy com profile prod
./deploy.sh prod
```

### Deploy Manual no Tomcat:

1. Copiar o WAR para o diretório webapps do Tomcat:
```bash
cp application/target/raw-spring-app-application.war $TOMCAT_HOME/webapps/
```

2. (Opcional) Definir variáveis de ambiente no Tomcat:

Editar `$TOMCAT_HOME/bin/setenv.sh`:
```bash
export SPRING_PROFILES_ACTIVE=prod
export DB_URL=jdbc:mysql://localhost:3306/app_db
export DB_USERNAME=app_user
export DB_PASSWORD=app_pass
```

3. Iniciar o Tomcat:
```bash
$TOMCAT_HOME/bin/startup.sh
```

### Scripts de Inicialização

O projeto inclui scripts para inicializar o Tomcat com o profile correto:

```bash
# Iniciar com profile dev
./start-tomcat-dev.sh

# Iniciar com profile prod
./start-tomcat-prod.sh
```


## Docker

O projeto inclui suporte para execução via Docker Compose com **Multi-stage Build**, que compila o projeto e sobe o banco de dados MySQL e a aplicação Tomcat automaticamente.

### Pré-requisitos

- Docker e Docker Compose instalados

### Executando com Docker

1. Inicie os containers (o build do WAR será feito automaticamente dentro do container):
   ```bash
   docker-compose up -d --build
   ```

2. A aplicação estará disponível em:
   ```
   http://localhost:8080/room_api/api/rooms
   ```

### Parando os containers

```bash
docker-compose down
```

## Endpoints da API

A Base URL depende do método de deploy:
- **Local (Tomcat/Maven):** `http://localhost:8080/raw-spring-app-application`
- **Docker:** `http://localhost:8080/room_api`

(Os exemplos abaixo utilizam a URL Local)

### Rooms API (Queries/JDBC)

Endpoints que utilizam implementação com queries SQL diretas via JDBC.

- **GET** `/api/rooms` - Listar todos os quartos
- **GET** `/api/rooms/{id}` - Obter quarto por ID
- **POST** `/api/rooms` - Criar novo quarto
- **PUT** `/api/rooms/{id}` - Atualizar quarto
- **DELETE** `/api/rooms/{id}` - Deletar quarto

#### Exemplo (Queries/JDBC):
```bash
curl -X POST http://localhost:8080/raw-spring-app-application/api/rooms \
  -H "Content-Type: application/json" \
  -d '{
    "number": "101",
    "type": "SINGLE",
    "capacity": 1,
    "pricePerNight": 100.00,
    "status": "AVAILABLE"
  }'
```

### Rooms API (Stored Procedures)

Endpoints que utilizam implementação baseada em Stored Procedures no banco de dados.

- **GET** `/api/rooms/stored-procedure` - Listar todos os quartos
- **GET** `/api/rooms/stored-procedure/{id}` - Obter quarto por ID
- **POST** `/api/rooms/stored-procedure` - Criar novo quarto
- **PUT** `/api/rooms/stored-procedure/{id}` - Atualizar quarto
- **DELETE** `/api/rooms/stored-procedure/{id}` - Deletar quarto

#### Exemplo (Stored Procedures):
```bash
curl -X POST http://localhost:8080/raw-spring-app-application/api/rooms/stored-procedure \
  -H "Content-Type: application/json" \
  -d '{
    "number": "202",
    "type": "DOUBLE",
    "capacity": 2,
    "pricePerNight": 150.00,
    "status": "AVAILABLE"
  }'
```

## Tecnologias Utilizadas

- Spring Framework 7.0.3
- Spring MVC
- Spring JDBC
- Jackson 2.15.2 (JSON)
- MySQL Connector 9.3.0
- Jakarta Servlet API 6.0
- Maven 3.x

## Configuração XML

O projeto utiliza configuração XML pura (sem anotações de configuração):

- **web.xml**: Configuração do servlet container
- **applicationContext.xml**: Contexto raiz (Services, Repositories, DataSource)
- **dispatcher-servlet.xml**: Contexto web (Controllers)
- **database-{profile}.xml**: Configuração específica por profile

## Resource Filtering

O Maven realiza resource filtering no arquivo `application.properties`, substituindo placeholders `${...}` pelos valores definidos nos profiles ou variáveis de ambiente.

## Troubleshooting

### Erro: "Could not resolve placeholder"
- Certifique-se de que o profile está ativo
- Verifique se as variáveis de ambiente estão definidas
- Use `-Pdev` ou `-Pprod` no build Maven

### Erro 404 ao acessar endpoints
- Verifique o context path da aplicação
- URL Local: `http://localhost:8080/raw-spring-app-application/api/rooms`
- URL Docker: `http://localhost:8080/room_api/api/rooms`

### Erro de conexão com banco de dados
- Certifique-se de que o MySQL está rodando e acessível
- Verifique se os bancos app_db (prod) e app_db_dev (dev) existem
- Verifique credenciais em `application.properties` ou variáveis de ambiente


# TODO

- [ ] Implementar autenticação e autorização (Spring Security)
- [ ] Adicionar testes unitários e de integração
- [ ] Configurar CI/CD para deploy automático
- [ ] Implementar cache para melhorar performance
- [ ] Adicionar container com a documentação da API (Apidog)
- [ ] Refatorar código para melhor organização e manutenção
- [ ] Implementar logging e monitoramento (Log4j)
