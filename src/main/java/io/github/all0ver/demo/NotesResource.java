package io.github.all0ver.demo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

/**
 * Zasob REST: zapis (POST) i odczyt (GET) notatek.
 * Aplikacja nie zna hosta, hasla ani rodzaju bazy - korzysta z jednostki
 * persystencji "demoPU", ktora wskazuje wylacznie nazwe JNDI jdbc/maria.
 */
@Path("/notes")
@ApplicationScoped
public class NotesResource {

    @PersistenceContext(unitName = "demoPU")
    private EntityManager em;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Note> list() {
        return em.createQuery("SELECT n FROM Note n ORDER BY n.id", Note.class)
                .getResultList();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response create(Note note) {
        if (note == null) {
            throw new BadRequestException("request body is required");
        }
        try {
            note.validate();
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e.getMessage());
        }
        // id nadaje baza (IDENTITY), wiec ignorujemy ewentualne id z zadania
        note.setId(null);
        em.persist(note);
        em.flush();
        return Response.created(URI.create("notes/" + note.getId()))
                .entity(note)
                .build();
    }
}
