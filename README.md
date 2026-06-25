# Receptų API

## Paleidimas lokaliai

```bash
mvn spring-boot:run
```

Aplikacija pasileis ant `http://localhost:8080`.

## Testų paleidimas

```bash
mvn test
```

## Endpoint'ai

### Autentifikacija

| Metodas | Kelias | Rolė |
|---------|--------|------|
| `POST` | `/api/auth/register` | Vieša |
| `POST` | `/api/auth/login` | Vieša |
| `GET` | `/api/auth/me` | Authenticated |

### Kategorijos

| Metodas | Kelias | Rolė |
|---------|--------|------|
| `GET` | `/api/categories` | Authenticated |
| `GET` | `/api/categories/{id}` | Authenticated |
| `POST` | `/api/categories` | ADMIN |
| `PUT` | `/api/categories/{id}` | ADMIN |
| `DELETE` | `/api/categories/{id}` | ADMIN |

### Receptai

| Metodas | Kelias | Rolė |
|---------|--------|------|
| `GET` | `/api/recipes` | Authenticated |
| `GET` | `/api/recipes/{id}` | Authenticated |
| `POST` | `/api/recipes` | Authenticated |
| `PUT` | `/api/recipes/{id}` | Savininkas |
| `DELETE` | `/api/recipes/{id}` | Savininkas arba ADMIN |

`/api/recipes` query parametrai: `categoryName`, `userId` (ADMIN only), `sort`, `page`, `size`.
