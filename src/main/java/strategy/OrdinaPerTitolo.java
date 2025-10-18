package strategy;

import builder.Film;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OrdinaPerTitolo implements OrdinamentoStrategy {

    public OrdinaPerTitolo() {}

    @Override
    public List<Film> ordina(List<Film> lista)
    {   List<Film> copia = new ArrayList<>(lista);
        copia.sort(new Comparator<Film>() {
            @Override
            public int compare(Film f1, Film f2) {
                return f1.getTitolo().compareToIgnoreCase(f2.getTitolo());
            }
        });
        return copia;
    }
}
