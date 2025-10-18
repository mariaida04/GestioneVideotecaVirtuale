package strategy;

import builder.Film;
import java.util.ArrayList;
import java.util.List;

public class RicercaPerAnno implements RicercaStrategy {
    private int anno;

    public RicercaPerAnno(int anno) {
        this.anno = anno;
    }

    @Override
    public List<Film> cerca(List<Film> lista) {
        List<Film> ret = new ArrayList<>();
        for (Film f : lista) {
            if (f.getAnno() == anno) {
                ret.add(f);
            }
        }
        return ret;
    }
}
