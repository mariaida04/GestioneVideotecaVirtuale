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
            if (normalizza(f.getTitolo()).contains(normalizza(titolo))) {
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
