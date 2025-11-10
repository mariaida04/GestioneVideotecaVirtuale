import builder.Film;
import builder.StatoVisione;
import builder.Valutazione;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import factoryMethod.FilmConcreteFactory;
import factoryMethod.FilmFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArchivioVideotecaTest {
    private static File tempFile;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @BeforeAll
    static void setup() throws IOException {
        tempFile = File.createTempFile("test_videoteca", ".json");
        tempFile.deleteOnExit();
    }

    //verifica che i film vengano correttamente salvati su un file JSON temporaneo e caricati
    @Test
    public void testSalvaECarica() throws IOException {
        List<Film> filmDaSalvare = new ArrayList<>();
        FilmFactory factory = new FilmConcreteFactory();
        Film f1 = factory.creaFilm("Oppenheimer", "Christopher Nolan", 2023, "Storico",
                Valutazione.QUATTRO_STELLE, StatoVisione.IN_VISIONE);
        Film f2 = factory.creaFilm("La La Land", "Damien Chazelle", 2016, "Romantico",
                Valutazione.CINQUE_STELLE, StatoVisione.VISTO);
        filmDaSalvare.add(f1);
        filmDaSalvare.add(f2);

        try (Writer writer = new FileWriter(tempFile)) {
            gson.toJson(filmDaSalvare, writer);
        }

        List<Film> filmCaricati;
        try (Reader reader = new FileReader(tempFile)) {
            Type tipo = new TypeToken<List<Film>>() {
            }.getType();
            filmCaricati = gson.fromJson(reader, tipo);
        }

        assertEquals(filmDaSalvare.size(), filmCaricati.size());
        assertEquals(filmDaSalvare.getFirst().getTitolo(), filmCaricati.getFirst().getTitolo());
        assertEquals(filmDaSalvare.getFirst().getRegista(), filmCaricati.getFirst().getRegista());
        assertEquals(filmDaSalvare.getFirst().getAnno(), filmCaricati.getFirst().getAnno());
        assertEquals(filmDaSalvare.getFirst().getGenere(), filmCaricati.getFirst().getGenere());
        assertEquals(filmDaSalvare.getFirst().getValutazione(), filmCaricati.getFirst().getValutazione());
        assertEquals(filmDaSalvare.getFirst().getStato(), filmCaricati.getFirst().getStato());
    }
}
