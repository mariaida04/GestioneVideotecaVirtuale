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
        for (Film f : lista) {
            if (normalizza(f.getGenere()).contains(normalizza(genere))) {
                ret.add(f);
            }
        }
        return ret;
    }

    private String normalizza(String s) {
        if (s == null) {
            return "";
        }
        return s.toLowerCase().replaceAll("\\p{Punct}", "").replaceAll("[\\s.-]", "");
    }
}
