# jakarta-demo-app

Prosta aplikacja demonstracyjna Jakarta EE (JAX-RS + JPA) do testowania
środowiska skonteneryzowanego (praca inżynierska). Zapisuje i odczytuje
notatki w bazie dostępnej przez zasób JNDI `jdbc/maria`.

- Java 21, Maven, pakowanie `war` (`demo.war`, kontekst `/demo`)
- `POST /demo/api/notes` - zapis notatki (JSON: `title`, `content`)
- `GET  /demo/api/notes` - lista notatek
- Baza: tylko nazwa JNDI w `persistence.xml`; host, hasło i rodzaj bazy
  konfiguruje środowisko, nie aplikacja.

Build i testy: `mvn verify` (wynik: `target/demo.war`).
