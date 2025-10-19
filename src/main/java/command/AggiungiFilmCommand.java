package command;

import builder.Film;
import singleton.Videoteca;

public class AggiungiFilmCommand implements Command {
    private final Videoteca videoteca;
    private final Film film;

    public AggiungiFilmCommand(Videoteca videoteca, Film film) {
        this.videoteca = videoteca;
        this.film = film;
    }

    @Override
    public void esegui() {
        videoteca.aggiungiFilm(film);
    }
}
