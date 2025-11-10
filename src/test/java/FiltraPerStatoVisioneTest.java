import builder.Film;
import builder.StatoVisione;
import builder.Valutazione;
import factoryMethod.FilmConcreteFactory;
import factoryMethod.FilmFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import strategy.FiltraPerStatoVisione;
import strategy.FiltroStrategy;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FiltraPerStatoVisioneTest {

    private FilmFactory factory;
    private Film f1, f2, f3, f4, f5;
    private List<Film> lista;

    @BeforeEach
    public void listaFilm() {
        factory = new FilmConcreteFactory();
        f1 = factory.creaFilm("La La Land", "Damien Chazelle", 2016,"Romantico", Valutazione.CINQUE_STELLE, StatoVisione.IN_VISIONE);
        f2 = factory.creaFilm("Titanic", "James Cameron", 1997,"Drammatico",Valutazione.CINQUE_STELLE,StatoVisione.VISTO);
        f3 = factory.creaFilm("Avatar", "James Cameron", 2009," Fantascienza ",Valutazione.TRE_STELLE,StatoVisione.VISTO);
        f4 = factory.creaFilm("Jurassic Park", "Steven Spielberg", 1993,"fantascienza",Valutazione.QUATTRO_STELLE,StatoVisione.IN_VISIONE);
        f5 = factory.creaFilm("Oppenheimer", "Christopher Nolan", 2023,"Storico",Valutazione.QUATTRO_STELLE,StatoVisione.IN_VISIONE);

        lista = new ArrayList<>();
        lista.add(f1);
        lista.add(f2);
        lista.add(f3);
        lista.add(f4);
        lista.add(f5);
    }

    //verifica che solo i film corrispondenti allo stato scelto siano contenuti nella lista
    @Test
    public void testFiltroStato() {
        FiltroStrategy strategy = new FiltraPerStatoVisione(StatoVisione.IN_VISIONE);
        List<Film> risultato = strategy.filtra(lista);

        assertEquals(3,risultato.size(), "Il numero di film in visione non corrisponde.");
        assertTrue(risultato.contains(f1));
        assertFalse(risultato.contains(f2));
        assertFalse(risultato.contains(f3));
        assertTrue(risultato.contains(f4));
        assertTrue(risultato.contains(f5));
    }

    //verifica se la lista dei film corrispondenti a uno stato non presente è vuota
    @Test
    public void testFiltroStatoNonPresente() {
        FiltroStrategy strategy = new FiltraPerStatoVisione(StatoVisione.DA_VEDERE);
        List<Film> risultato = strategy.filtra(lista);

        assertTrue(risultato.isEmpty(), "La lista dovrebbe essere vuota in quanto lo stato non è presente");
    }
}
