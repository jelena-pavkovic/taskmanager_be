# 📋 Task Manager — Backend

REST API za aplikaciju za upravljanje zadacima, izgrađen sa Spring Boot-om. Podržava autentifikaciju korisnika putem JWT tokena, upravljanje zadacima i siguran pristup resursima.

---

## 🛠️ Tehnološki stack

| Tehnologija | Verzija |
|---|---|
| Java | 17 |
| Spring Boot | 4.0.6 |
| Spring Security | ✅ |
| Spring Data JPA | ✅ |
| JWT (jjwt) | 0.12.6 |
| MySQL | ✅ |
| Lombok | ✅ |
| Maven | ✅ |

---

## 🚀 Pokretanje projekta

### Preduslovi

- Java 17+
- Maven
- MySQL baza podataka

### Konfiguracija baze podataka

Kreiraj bazu podataka u MySQL-u:

```sql
CREATE DATABASE taskmanager_db;
```

U `src/main/resources/application.properties` (ili `application.yml`) podesi konekciju:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/taskmanager_db
spring.datasource.username=tvoj_username
spring.datasource.password=tvoja_lozinka
spring.jpa.hibernate.ddl-auto=update
```

### Pokretanje

```bash
# Kloniranje repozitorijuma
git clone https://github.com/jelena-pavkovic/taskmanager_be.git
cd taskmanager_be

# Build i pokretanje
./mvnw spring-boot:run
```

API će biti dostupan na: `http://localhost:8080`

---

## 🔗 Povezani repozitorijumi

- **Frontend:** [taskmanager_fe](https://github.com/jelena-pavkovic/taskmanager_fe)

---

## 👩‍💻 Autor

[jelena-pavkovic](https://github.com/jelena-pavkovic)
