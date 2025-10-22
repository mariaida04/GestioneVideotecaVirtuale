import builder.Film;
import builder.StatoVisione;
import builder.Valutazione;
import factoryMethod.FilmConcreteFactory;
import factoryMethod.FilmFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import singleton.Videoteca;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModificaFilmTest {
    private FilmFactory factory;

    @BeforeEach
    public void svuota() {
        factory = new FilmConcreteFactory();
        Videoteca.getInstance().reset();
    }

    @Test
    public void testModificaFilm() {
        Videoteca videoteca = Videoteca.getInstance();

        Film originale = factory.creaFilm("Titanic", "James Cameron", 1997,"Drammatico",Valutazione.QUATTRO_STELLE, StatoVisione.IN_VISIONE);
        videoteca.aggiungiFilm(originale);

        Film modificato = factory.creaFilm("Titanic", "James Cameron", 1997,"Drammatico",Valutazione.CINQUE_STELLE, StatoVisione.VISTO);
        boolean modifica = videoteca.modificaFilm("Titanic", "James Cameron", 1997,modificato);

        assertTrue(modifica);
        assertEquals(1,videoteca.getCollezione().size());
        Film presente = videoteca.getCollezione().getFirst();
        assertEquals(Valutazione.CINQUE_STELLE, presente.getValutazione());
        assertEquals(StatoVisione.VISTO, presente.getStato());
    }

    //verifica che non sia possibile modificare un film non presente nella collezione
    @Test
    public void testModificaFilmNonPresente() {
        Videoteca videoteca = Videoteca.getInstance();

        Film nuovo = factory.creaFilm("Titanic", "James Cameron", 1997,"Drammatico",Valutazione.CINQUE_STELLE, StatoVisione.VISTO);

        boolean modifica = videoteca.modificaFilm("Titanic", "James Cameron", 1997, nuovo);

        assertFalse(modifica);
        assertEquals(0,videoteca.getCollezione().size());
    }
}
