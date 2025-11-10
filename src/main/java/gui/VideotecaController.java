package gui;

import builder.Film;
import builder.StatoVisione;
import builder.Valutazione;
import command.AggiungiFilmCommand;
import command.Command;
import command.ModificaFilmCommand;
import command.RimuoviFilmCommand;
import factoryMethod.FilmConcreteFactory;
import factoryMethod.FilmFactory;
import singleton.Videoteca;
import strategy.*;
import javax.swing.*;
import java.util.List;

public class VideotecaController {
    private Frame frame;
    private final FilmFactory filmFactory;

    public VideotecaController(Frame frame) {
        this.frame = frame;
        this.filmFactory = new FilmConcreteFactory();
    }

    public void aggiunta(String titolo, String regista, Integer anno, String genere, String valutazioneStr, String statoStr) {
        try {
            String genereFinale = (genere != null && !genere.isBlank()) ? genere : null;
            Valutazione valutazione = (valutazioneStr != null && !valutazioneStr.equals("Seleziona")) ? Valutazione.valueOf(valutazioneStr.replace(" ","_")) : null;
            StatoVisione stato = (statoStr != null && !statoStr.equals("Seleziona")) ? StatoVisione.valueOf(statoStr.replace(" ","_")) : null;

            Film film = filmFactory.creaFilm(titolo, regista, anno, genereFinale, valutazione, stato);

            Command comando = new AggiungiFilmCommand(Videoteca.getInstance(), film);
            comando.esegui();

            mostraVideoteca();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Errore nella creazione del film: " + e.getMessage(),
                    "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void rimozione(String titolo, String regista, Integer anno) {
        Videoteca videoteca = Videoteca.getInstance();
        Film daRimuovere = videoteca.ottieniFilmDaId(titolo, regista, anno);

        if (daRimuovere != null) {
            Command command = new RimuoviFilmCommand(videoteca, daRimuovere);
            command.esegui();
            mostraVideoteca();
        }
        else {
            JOptionPane.showMessageDialog(frame, "Nessun film trovato con i parametri inseriti.", "Film non trovato", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void modifica(String titoloOrig, String registaOrig, Integer annoOrig, String nuovoTitolo, String nuovoRegista,
                         Integer nuovoAnno, String nuovoGenere, String valutazioneStr, String statoStr) {
        Videoteca videoteca = Videoteca.getInstance();
        Film filmEsistente = videoteca.ottieniFilmDaId(titoloOrig,registaOrig,annoOrig);

        if (filmEsistente == null) {
            JOptionPane.showMessageDialog(frame, "Film da modificare non trovato.");
            return;
        }

        try {
            Valutazione valutazione = (valutazioneStr != null) ? Valutazione.valueOf(valutazioneStr.replace(" ","_")) : null;
            StatoVisione stato = (statoStr != null) ? StatoVisione.valueOf(statoStr.replace(" ","_")) : null;

            Film modificato = filmFactory.creaFilm(nuovoTitolo, nuovoRegista, nuovoAnno, nuovoGenere, valutazione, stato);

            //controllo se esiste già un film con stesso titolo, regista e anno nella collezione
            Film duplicato = videoteca.ottieniFilmDaId(nuovoTitolo,nuovoRegista,nuovoAnno);
            boolean identificativiDiversi = !(titoloOrig.equalsIgnoreCase(nuovoTitolo) &&
                    registaOrig.equalsIgnoreCase(nuovoRegista) &&
                    annoOrig.equals(nuovoAnno));

            if(identificativiDiversi && duplicato != null) {
                JOptionPane.showMessageDialog(frame, "Modifica non effettuata: esiste già un film con gli stessi campi principali.");
                return;
            }

            Command command = new ModificaFilmCommand(videoteca, modificato, titoloOrig, registaOrig, annoOrig);
            command.esegui();

            mostraVideoteca();
        }
        catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(frame, "Errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void ricerca(String attributo, String valore) {
        if (attributo == null || attributo.equals("Seleziona")) {
            JOptionPane.showMessageDialog(frame, "Seleziona un attributo valido per la ricerca.");
            return;
        }
        if (valore == null || valore.isBlank()) {
            JOptionPane.showMessageDialog(frame, "Inserisci un valore da cercare.");
            return;
        }

        RicercaStrategy strategia;

        switch (attributo) {
            case "Titolo":
                strategia = new RicercaPerTitolo(valore);
                break;
            case "Regista":
                strategia = new RicercaPerRegista(valore);
                break;
            case "Anno di uscita":
                strategia = new RicercaPerAnno(Integer.parseInt(valore));
                break;
            default:
                JOptionPane.showMessageDialog(frame, "Attributo di ricerca non valido.");
                return;
        }
        List<Film> risultati = Videoteca.getInstance().ricercaStrategy(strategia);
        if (risultati.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Nessun film trovato.", "Risultato ricerca", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        frame.mostraFilm(risultati);
    }

    public void ordina(String criterio) {
        if (criterio == null || criterio.equals("Seleziona")) {
            JOptionPane.showMessageDialog(frame, "Seleziona un criterio valido per l'ordinamento.");
            return;
        }

        OrdinamentoStrategy strategia;

        switch (criterio) {
            case "Titolo":
                strategia = new OrdinaPerTitolo();
                break;
            case "Anno di uscita":
                strategia = new OrdinaPerAnno();
                break;
            case "Valutazione":
                strategia = new OrdinaPerValutazione();
                break;
            default:
                JOptionPane.showMessageDialog(frame, "Criterio di ordinamento non valido.");
                return;
        }
        List<Film> ordinati = Videoteca.getInstance().ordinamentoStrategy(strategia);
        frame.mostraFilm(ordinati);
    }

    public void filtra(String criterio, String valore) {
        if (criterio == null || criterio.equals("Seleziona")) {
            JOptionPane.showMessageDialog(frame, "Seleziona un criterio valido per il filtraggio.");
            return;
        }

        if (valore == null || valore.isBlank()) {
            JOptionPane.showMessageDialog(frame, "Inserisci un valore per cui filtrare.");
            return;
        }

        FiltroStrategy strategia;

        switch (criterio) {
            case "Genere":
                strategia = new FiltraPerGenere(valore);
                break;
            case "Stato di visione":
                //controllo se il valore è valido
                StatoVisione stato = convertiStato(valore);
                if (stato == null) {
                    JOptionPane.showMessageDialog(frame, "Stato di visione non valido.");
                    return;
                }
                strategia = new FiltraPerStatoVisione(stato);
                break;
            default:
                JOptionPane.showMessageDialog(frame,"Criterio di filtro non valido");
                return;
        }

        List<Film> risultato = Videoteca.getInstance().filtroStrategy(strategia);
        if (risultato.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Nessun film trovato.", "Risultato filtraggio",JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        frame.mostraFilm(risultato);
    }

    //per controllare se il valore inserito manualmente dall'utente ha corrispondenza con uno degli stati
    private StatoVisione convertiStato(String input) {
        input = input.trim().toLowerCase().replaceAll("\\s+","").replaceAll("_","");

        switch (input) {
            case "davedere":
                return StatoVisione.DA_VEDERE;
            case "invisione":
                return StatoVisione.IN_VISIONE;
            case "visto":
                return StatoVisione.VISTO;
            default:
                return null;
        }
    }

    public void mostraVideoteca() {
        frame.mostraFilm(Videoteca.getInstance().getCollezione());
    }
}
