import builder.Film;
import builder.StatoVisione;
import builder.Valutazione;
import factoryMethod.FilmConcreteFactory;
import factoryMethod.FilmFactory;
import org.junit.jupiter.api.Test;
import strategy.OrdinaPerTitolo;
import strategy.OrdinamentoStrategy;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrdinaPerTitoloTest {

    @Test
    public void testOrdinamentoAlfabetico() {
        FilmFactory factory = new FilmConcreteFactory();

        Film f1 = factory.creaFilm("La La Land", "Damien Chazelle", 2016,"Romantico", Valutazione.CINQUE_STELLE, StatoVisione.IN_VISIONE);
        Film f2 = factory.creaFilm("Titanic", "James Cameron", 1997,"Drammatico",Valutazione.CINQUE_STELLE,StatoVisione.VISTO);
        Film f3 = factory.creaFilm("Avatar", "James Cameron", 2009," Fantascienza ",Valutazione.TRE_STELLE,StatoVisione.VISTO);
        Film f4 = factory.creaFilm("jurassic Park", "Steven Spielberg", 1993,"fantascienza",Valutazione.QUATTRO_STELLE,StatoVisione.IN_VISIONE);
        Film f5 = factory.creaFilm("Oppenheimer", "Christopher Nolan", 2023,"Storico",Valutazione.QUATTRO_STELLE,StatoVisione.IN_VISIONE);

        List<Film> lista = new ArrayList<>();
        lista.add(f1);
        lista.add(f2);
        lista.add(f3);
        lista.add(f4);
        lista.add(f5);

        OrdinamentoStrategy strategy = new OrdinaPerTitolo();
        List<Film> ordinati = strategy.ordina(lista);

        assertEquals(f3, ordinati.get(0));
        assertEquals(f4, ordinati.get(1));
        assertEquals(f1, ordinati.get(2));
        assertEquals(f5, ordinati.get(3));
        assertEquals(f2, ordinati.get(4));
    }
}
