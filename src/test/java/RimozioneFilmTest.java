import builder.Film;
import builder.StatoVisione;
import builder.Valutazione;
import factoryMethod.FilmConcreteFactory;
import factoryMethod.FilmFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import singleton.Videoteca;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RimozioneFilmTest {

    private FilmFactory factory;

    @BeforeEach
    public void svuota() {
        factory = new FilmConcreteFactory();
        Videoteca.getInstance().reset();
    }

    @Test
    public void testRimuoviFilm() {
        Videoteca videoteca = Videoteca.getInstance();
        Film film = factory.creaFilm("Titanic", "James Cameron", 1997,"Drammatico", Valutazione.CINQUE_STELLE, StatoVisione.VISTO);

        videoteca.aggiungiFilm(film);

        assertEquals(1, videoteca.getCollezione().size());

        videoteca.rimuoviFilm(film);

        assertEquals(0, videoteca.getCollezione().size());
        assertFalse(videoteca.getCollezione().contains(film));
    }

    @Test
    public void testRimuoviFilmNonPresente() {
        Videoteca videoteca = Videoteca.getInstance();
        Film film = factory.creaFilm("Titanic", "James Cameron", 1997,"Drammatico",Valutazione.CINQUE_STELLE,StatoVisione.VISTO);

        videoteca.rimuoviFilm(film);

        assertEquals(0,videoteca.getCollezione().size());
    }
}
