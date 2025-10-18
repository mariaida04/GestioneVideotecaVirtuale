package strategy;

import builder.Film;
import builder.Valutazione;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OrdinaPerValutazione implements OrdinamentoStrategy {

    public OrdinaPerValutazione() {}

    @Override
    public List<Film> ordina(List<Film> lista) {
        List<Film> copia = new ArrayList<>(lista);
        copia.sort(new Comparator<Film>() {
            @Override
            public int compare(Film f1, Film f2) {
                Valutazione v1 = f1.getValutazione();
                Valutazione v2 = f2.getValutazione();

                if (v1 == null && v2 == null) return 0;
                if (v1 == null) return 1;
                if (v2 == null) return -1;
                return v1.compareTo(v2);
            }
        });
        return copia;
    }
}
