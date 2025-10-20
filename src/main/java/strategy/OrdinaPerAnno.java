package strategy;

import builder.Film;
import builder.Valutazione;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OrdinaPerAnno implements OrdinamentoStrategy {

    public OrdinaPerAnno() {}

    @Override
    public List<Film> ordina(List<Film> lista) {
        List<Film> copia = new ArrayList<>(lista);
        copia.sort(new Comparator<Film>() {
            @Override
            public int compare(Film f1, Film f2) {
                return Integer.compare(f1.getAnno(),f2.getAnno());
            }
        });
        return copia;
    }
}
