package singleton;

import builder.Film;
import factoryMethod.FilmConcreteFactory;
import factoryMethod.FilmFactory;
import repository.ArchivioVideoteca;
import strategy.FiltroStrategy;
import strategy.OrdinamentoStrategy;
import strategy.RicercaStrategy;
import java.util.ArrayList;
import java.util.List;

public class Videoteca {
    private static Videoteca instance = null;
    private List<Film> collezione;

    private Videoteca() {
        this.collezione = ArchivioVideoteca.carica();
    }

    public static synchronized Videoteca getInstance(){
        if (instance == null) {
            instance = new Videoteca();
        }
        return instance;
    }

    public void aggiungiFilm(Film film) {
        if (collezione.contains(film)) {
            throw new IllegalArgumentException("Film già presente: " + film);
            }
        collezione.add(film);
        ArchivioVideoteca.salva(collezione);
    }

    public void rimuoviFilm(Film film) {
        if (collezione.contains(film)) {
            collezione.remove(film);
            ArchivioVideoteca.salva(collezione);
        }
    }

    public boolean modificaFilm(String titolo, String regista, int anno, Film nuovo) {
        Film filmDaModificare = ottieniFilmDaId(titolo,regista,anno);

        if (filmDaModificare == null) {
            return false;
        }

        int index = collezione.indexOf(filmDaModificare);
        collezione.set(index,nuovo);
        ArchivioVideoteca.salva(collezione);
        return true;
    }

    public List<Film> getCollezione() {
        return new ArrayList<>(collezione);  //per sicurezza viene restituita la copia
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Film f : collezione) {
            sb.append(f.toString()).append("\n");
        }
        return sb.toString();
    }

    public List<Film> filtroStrategy(FiltroStrategy strategia) {
        return strategia.filtra(collezione);
    }

    public List<Film> ordinamentoStrategy(OrdinamentoStrategy strategia) {
        return strategia.ordina(collezione);
    }

    public List<Film> ricercaStrategy(RicercaStrategy strategia) {
        return strategia.cerca(collezione);
    }

    public void reset() {
        collezione.clear();
        ArchivioVideoteca.salva(collezione);
    }

    public Film ottieniFilmDaId(String titolo, String regista, int anno) {
        FilmFactory factory = new FilmConcreteFactory();
        Film cercato = factory.creaFilm(titolo,regista,anno,"",null,null);
        for (Film f : collezione) {
            if (f.equals(cercato)) {
                return f;
            }
        }
        return null;
    }
}
