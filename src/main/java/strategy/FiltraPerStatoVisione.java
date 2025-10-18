package strategy;

import builder.Film;
import builder.StatoVisione;
import java.util.ArrayList;
import java.util.List;

public class FiltraPerStatoVisione implements FiltroStrategy {
    private StatoVisione stato;

    public FiltraPerStatoVisione(StatoVisione stato) {
        this.stato = stato;
    }

    @Override
    public List<Film> filtra(List<Film> lista) {
        List<Film> ret = new ArrayList<>();
        for (Film f : lista) {
            if (f.getStato() != null && f.getStato() == stato) {
                ret.add(f);
            }
        }
        return ret;
    }
}
