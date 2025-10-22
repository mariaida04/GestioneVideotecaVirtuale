import builder.Film;
import builder.StatoVisione;
import builder.Valutazione;
import factoryMethod.FilmConcreteFactory;
import factoryMethod.FilmFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import singleton.Videoteca;

import static org.junit.jupiter.api.Assertions.*;

public class AggiuntaFilmTest {
    private FilmFactory factory;

    @BeforeEach
    public void svuota() {
        factory = new FilmConcreteFactory();
        Videoteca.getInstance().reset();
    }

    //verifica che l'aggiunta di un film vada a buon fine
    @Test
    public void testAggiuntaFilm() {
        Videoteca videoteca = Videoteca.getInstance();

        Film film = factory.creaFilm("Interstellar", "Christopher Nolan", 2014,"Fantascienza",null,null);
        videoteca.aggiungiFilm(film);

        assertEquals(1, videoteca.getCollezione().size());
        assertTrue(videoteca.getCollezione().contains(film));
    }

    //verifica che non possano essere aggiunti alla collezione due film che hanno gli stessi campi identificativi
    @Test
    public void testAggiuntaFilmUgualiID() {
        Videoteca videoteca = Videoteca.getInstance();

        Film f1 = factory.creaFilm("La La Land", "Damien Chazelle", 2016,"Romantico", Valutazione.QUATTRO_STELLE, StatoVisione.VISTO);
        Film f2 = factory.creaFilm("La La Land", "Damien Chazelle", 2016,"Romantico",Valutazione.CINQUE_STELLE,StatoVisione.VISTO);

        videoteca.aggiungiFilm(f1);

        assertThrows(IllegalArgumentException.class, () -> { videoteca.aggiungiFilm(f2);
            } , "L'aggiunta di un film duplicato deve lanciare un'eccezione.");
        assertEquals(1, videoteca.getCollezione().size(), "La collezione non deve cambiare dopo un tentativo di aggiunta duplicata.");
    }
}
