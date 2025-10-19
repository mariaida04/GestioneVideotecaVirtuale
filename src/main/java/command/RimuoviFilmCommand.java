package command;

import builder.Film;
import singleton.Videoteca;

public class RimuoviFilmCommand implements Command {
    private final Videoteca videoteca;
    private final Film film;

    public RimuoviFilmCommand(Videoteca videoteca, Film film) {
        this.videoteca = videoteca;
        this.film = film;
    }

    @Override
    public void esegui() {
        videoteca.rimuoviFilm(film);
    }
}
