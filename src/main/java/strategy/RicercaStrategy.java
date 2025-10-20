package strategy;

import builder.Film;
import java.util.List;

public interface RicercaStrategy {
    List<Film> cerca(List<Film> lista);
}
