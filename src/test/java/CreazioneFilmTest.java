import builder.StatoVisione;
import builder.Valutazione;
import factoryMethod.FilmConcreteFactory;
import factoryMethod.FilmFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreazioneFilmTest {
    @Test
    public void testCreazioneFilmAnnoNonValido() {
        FilmFactory factory = new FilmConcreteFactory();
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            factory.creaFilm("Titanic", "James Cameron", 0, "Drammatico",Valutazione.CINQUE_STELLE, StatoVisione.IN_VISIONE);
        });

        assertEquals("Anno non valido: 0", e.getMessage());
    }

    @Test
    public void testCreazioneFilmSenzaTitolo() {
        FilmFactory factory = new FilmConcreteFactory();
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            factory.creaFilm(" ", "James Cameron", 1997,"Drammatico",Valutazione.CINQUE_STELLE, StatoVisione.IN_VISIONE);
        });

        assertEquals("Titolo obbligatorio", e.getMessage());
    }

    @Test
    public void testCreazioneFilmSenzaRegista() {
        FilmFactory factory = new FilmConcreteFactory();
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            factory.creaFilm("Titanic", " ",1997,"Drammatico",Valutazione.CINQUE_STELLE, StatoVisione.IN_VISIONE);
        });

        assertEquals("Regista obbligatorio", e.getMessage());
    }

    @Test
    public void testCreazioneFilmTitoloNull() {
        FilmFactory factory = new FilmConcreteFactory();
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            factory.creaFilm(null, "James Cameron", 1997,"Drammatico",Valutazione.CINQUE_STELLE, StatoVisione.IN_VISIONE);
        });

        assertEquals("Titolo obbligatorio", e.getMessage());
    }

    @Test
    public void testCreazioneFilmRegistaNull() {
        FilmFactory factory = new FilmConcreteFactory();
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            factory.creaFilm("Titanic", null, 1997,"Drammatico",Valutazione.CINQUE_STELLE, StatoVisione.IN_VISIONE);
        });

        assertEquals("Regista obbligatorio", e.getMessage());
    }
}
