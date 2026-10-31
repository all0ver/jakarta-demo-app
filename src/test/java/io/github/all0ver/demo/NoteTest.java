package io.github.all0ver.demo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class NoteTest {

    private static Note note(String title) {
        Note n = new Note();
        n.setTitle(title);
        n.setContent("tresc");
        return n;
    }

    @Test
    void validNoteIsAccepted() {
        assertDoesNotThrow(() -> note("Zakupy").validate());
    }

    @Test
    void blankTitleIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> note("   ").validate());
    }

    @Test
    void tooLongTitleIsRejected() {
        String longTitle = "x".repeat(Note.MAX_TITLE_LENGTH + 1);
        assertThrows(IllegalArgumentException.class, () -> note(longTitle).validate());
    }
}
