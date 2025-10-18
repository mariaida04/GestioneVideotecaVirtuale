package factoryMethod;

import builder.Film;
import builder.StatoVisione;
import builder.Valutazione;

public interface FilmFactory {
    //factory method
    Film creaFilm(String titolo, String regista, int anno, String genere,
                  Valutazione valutazione, StatoVisione stato);
}
