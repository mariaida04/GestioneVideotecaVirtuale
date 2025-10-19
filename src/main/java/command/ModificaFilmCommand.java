package command;

import builder.Film;
import singleton.Videoteca;

public class ModificaFilmCommand implements Command {
    private final Videoteca videoteca;
    private final Film film;
    private final String titolo;
    private final String regista;
    private final int anno;

    public ModificaFilmCommand(Videoteca videoteca, Film film, String titolo, String regista, int anno) {
        this.videoteca = videoteca;
        this.film = film;
        this.titolo = titolo;
        this.regista = regista;
        this.anno = anno;
    }

    @Override
    public void esegui() {
        videoteca.modificaFilm(titolo,regista,anno, film);
    }
}
