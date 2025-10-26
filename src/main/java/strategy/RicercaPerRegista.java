package strategy;

import builder.Film;
import java.util.ArrayList;
import java.util.List;

public class RicercaPerRegista implements RicercaStrategy {
    private String regista;

    public RicercaPerRegista(String regista) {
        this.regista = regista;
    }

    @Override
    public List<Film> cerca(List<Film> lista) {
        List<Film> ret = new ArrayList<>();
        for (Film f : lista) {
            if (normalizza(f.getRegista()).contains(normalizza(regista))) {
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
