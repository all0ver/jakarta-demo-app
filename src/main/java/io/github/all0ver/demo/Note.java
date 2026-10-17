package io.github.all0ver.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Encja JPA - jedna notatka. Mapowana na tabele "notes" w bazie dostepnej
 * przez zasob JNDI jdbc/maria (patrz persistence.xml).
 */
@Entity
@Table(name = "notes")
public class Note {

    static final int MAX_TITLE_LENGTH = 200;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = MAX_TITLE_LENGTH)
    private String title;

    @Column(length = 2000)
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Sprawdza poprawnosc notatki przed zapisem. Logika bez zaleznosci od
     * serwera i bazy, dlatego da sie ja przetestowac zwyklym testem jednostkowym.
     *
     * @throws IllegalArgumentException gdy tytul jest pusty lub zbyt dlugi
     */
    public void validate() {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title is required");
        }
        if (title.length() > MAX_TITLE_LENGTH) {
            throw new IllegalArgumentException("title is too long (max " + MAX_TITLE_LENGTH + ")");
        }
    }
}
