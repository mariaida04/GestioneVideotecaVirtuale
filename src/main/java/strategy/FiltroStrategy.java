package strategy;

import builder.Film;
import java.util.List;

public interface FiltroStrategy {
    List<Film> filtra(List<Film> lista);
}
