package strategy;

import builder.Film;
import java.util.ArrayList;
import java.util.List;

public class RicercaPerTitolo implements RicercaStrategy {
    private String titolo;

    public RicercaPerTitolo(String titolo) {
        this.titolo = titolo;
    }

    @Override
    public List<Film> cerca(List<Film> lista) {
        List<Film> ret = new ArrayList<>();
        for (Film f : lista) {
            if (f.getTitolo().toLowerCase().replaceAll("\\p{Punct}", "").replaceAll("[\\s.-]", "")
                    .contains(titolo.toLowerCase().replaceAll("\\p{Punct}", "").replaceAll("[\\s.-]", ""))) {
                ret.add(f);
            }
        }
        return ret;
    }
}
