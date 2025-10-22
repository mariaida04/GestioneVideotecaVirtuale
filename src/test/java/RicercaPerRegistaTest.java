import builder.Film;
import builder.StatoVisione;
import builder.Valutazione;
import factoryMethod.FilmConcreteFactory;
import factoryMethod.FilmFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import strategy.RicercaPerRegista;
import strategy.RicercaStrategy;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class RicercaPerRegistaTest {
    private FilmFactory factory;
    private Film f1, f2, f3, f4, f5;
    private List<Film> lista;

    @BeforeEach
    public void listaFilm() {
        factory = new FilmConcreteFactory();
        f1 = factory.creaFilm("La La Land", "Damien Chazelle", 2016,"Romantico", Valutazione.CINQUE_STELLE, StatoVisione.IN_VISIONE);
        f2 = factory.creaFilm("Titanic", " James Cameron ", 1997,"Drammatico",Valutazione.CINQUE_STELLE,StatoVisione.VISTO);
        f3 = factory.creaFilm("Avatar", "james Cameron", 2009," Fantascienza ",Valutazione.TRE_STELLE,StatoVisione.VISTO);
        f4 = factory.creaFilm("jurassic Park", "Steven Spielberg", 1993,"fantascienza",Valutazione.QUATTRO_STELLE,StatoVisione.IN_VISIONE);
        f5 = factory.creaFilm("Oppenheimer", "Christopher Nolan", 2023,"Storico",Valutazione.QUATTRO_STELLE,StatoVisione.IN_VISIONE);

        lista = new ArrayList<>();
        lista.add(f1);
        lista.add(f2);
        lista.add(f3);
        lista.add(f4);
        lista.add(f5);
    }

    @Test
    public void testRicercaRegista() {
        RicercaStrategy strategy = new RicercaPerRegista("james cameron");
        List<Film> risultato = strategy.cerca(lista);

        assertEquals(2, risultato.size());
        assertFalse(risultato.contains(f1));
        assertTrue(risultato.contains(f2));
        assertTrue(risultato.contains(f3));
        assertFalse(risultato.contains(f4));
        assertFalse(risultato.contains(f5));
    }

    @Test
    public void testRicercaRegistaNonPresente() {
        RicercaStrategy strategy = new RicercaPerRegista("Quentin Tarantino");
        List<Film> risultato = strategy.cerca(lista);

        assertTrue(risultato.isEmpty(), "La lista dovrebbe essere vuota se il regista non è presente.");
    }
}
