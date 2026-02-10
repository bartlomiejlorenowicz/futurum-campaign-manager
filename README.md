# Campaign Manager

Aplikacja full-stack do zarządzania produktami oraz kampaniami marketingowymi.

System umożliwia:
- tworzenie i zarządzanie produktami
- przypisywanie kampanii do produktów
- zarządzanie budżetem kampanii
- śledzenie liczby kampanii przypisanych do produktu

---

## Architektura

Projekt został podzielony na dwie części:
- **backend/** – REST API zbudowane w oparciu o Spring Boot
- **frontend/** – aplikacja SPA zbudowana w Angularze

Frontend komunikuje się z backendem poprzez REST API.

---

## Tech Stack

### Backend
- Java 21
- Spring Boot
- Spring Data JPA
- H2 Database (środowisko developerskie)
- JUnit 5
- Mockito

### Frontend
- Angular
- TypeScript
- HTML / CSS

---

## Struktura projektu

campaign-manager/
├── backend/     # Spring Boot REST API
├── frontend/    # Angular SPA
└── README.md    # Dokumentacja projektu

---

## Uruchomienie projektu

### Backend
```bash

cd backend
./mvnw spring-boot:run
```

Backend uruchomi się pod adresem:

http://localhost:8080

## Dane początkowe

Po uruchomieniu backendu baza danych jest automatycznie inicjalizowana
na podstawie pliku:

- backend/src/main/resources/data.sql

Plik data.sql tworzy przykładowe konta EmeraldAccount, m.in.:

- konto o ID 1 z saldem 1000.00
- konto o ID 2 z saldem 2.000

Dane te mogą być wykorzystane podczas testowania tworzenia produktów
i kampanii.

Frontend

```Bash

cd frontend
npm install
ng serve
```

http://localhost:4200

Testy

Backend zawiera testy jednostkowe logiki biznesowej:

Uruchomienie testów:

```Bash

cd backend
./mvnw test
```

## Dokumentacja API (Swagger)

Backend udostępnia interaktywną dokumentację REST API
za pomocą **Swagger UI**.

Po uruchomieniu backendu dokumentacja jest dostępna pod adresem:

http://localhost:8080/swagger-ui/index.html

Swagger umożliwia:
- przegląd dostępnych endpointów
- testowanie zapytań HTTP bezpośrednio z przeglądarki
- podgląd struktur requestów i response’ów

