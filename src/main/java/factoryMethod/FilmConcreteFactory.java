package factoryMethod;

import builder.Film;
import builder.StatoVisione;
import builder.Valutazione;

public class FilmConcreteFactory implements FilmFactory {

    @Override
    public Film creaFilm(String titolo, String regista, int anno, String genere,
                          Valutazione valutazione, StatoVisione stato)
    {   return new Film.Builder(titolo, regista, anno).genere(genere).valutazione(valutazione).stato(stato).build();
    }
}
