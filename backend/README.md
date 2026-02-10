## Backend

### Uruchomienie projektu

```Bash

cd backend
./mvnw spring-boot:run
```

## Baza danych

Aplikacja korzysta z wbudowanej bazy danych H2 (in-memory), która jest automatycznie inicjalizowana przy starcie 
na podstawie pliku data.sql (w tym dane konta emerald).

JDBC URL:

`jdbc:h2:mem:campaign-db`

Konsola H2:

`http://localhost:8080/h2-console/login.do`

W polu JDBC URL należy wpisać:

`jdbc:h2:mem:campaign-db`
