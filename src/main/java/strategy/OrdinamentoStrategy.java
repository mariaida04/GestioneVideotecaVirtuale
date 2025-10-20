package strategy;

import builder.Film;
import java.util.List;

public interface OrdinamentoStrategy {
    List<Film> ordina(List<Film> lista);
}
