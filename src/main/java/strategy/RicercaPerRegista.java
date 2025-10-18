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
            if ((f.getRegista().toLowerCase().replaceAll("\\p{Punct}", "")
                    .replaceAll("[\\s.-]", "")
                    .contains(regista.toLowerCase().replaceAll("\\p{Punct}", "")
                            .replaceAll("[\\s.-]", "")))) {
                ret.add(f);
            }
        }
        return ret;
    }
}
