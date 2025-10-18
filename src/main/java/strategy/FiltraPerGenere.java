package strategy;

import builder.Film;
import java.util.ArrayList;
import java.util.List;

public class FiltraPerGenere implements FiltroStrategy {
    private String genere;

    public FiltraPerGenere(String genere) {
        this.genere = genere;
    }

    @Override
    public List<Film> filtra(List<Film> lista) {
        List<Film> ret = new ArrayList<>();
        for(Film f : lista) {
            if (f.getGenere().toLowerCase().replaceAll("\\p{Punct}", "").replaceAll("[\\s.-]", "")
                    .contains(genere.toLowerCase().replaceAll("\\p{Punct}", "").replaceAll("[\\s.-]", ""))) {
                ret.add(f);
            }
        }
        return ret;
    }
}
