package gui;

import builder.Film;
import singleton.Videoteca;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class Frame extends JFrame {
    private JTable tabella;
    private DefaultTableModel tableModel;
    private JButton aggiungi;
    private JButton rimuovi;
    private JButton modifica;
    private VideotecaController controller;
    private JComboBox<String> ricercaBox;
    private JTextField campoRicerca;
    private JComboBox<String> ordinaBox;
    private JComboBox<String> filtraBox;
    private JTextField campoFiltra;

    public Frame() {
        super("Gestione Videoteca Virtuale");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //per far sì che la x faccia terminare il processo
        setSize(800, 600);
        setLocationRelativeTo(null);  //per far apparire al centro la finestra
        setLayout(new BorderLayout());

        //COMANDI
        //layout
        JPanel comandiPanel = new JPanel();
        comandiPanel.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));

        //pulsante per visualizzare la videoteca
        JButton visualizzaButton = new JButton("Visualizza videoteca");
        visualizzaButton.addActionListener(e -> mostraVideoteca());

        aggiungi = new JButton("Aggiungi film");
        rimuovi = new JButton("Rimuovi film");
        modifica = new JButton("Modifica film");

        comandiPanel.add(visualizzaButton);
        comandiPanel.add(aggiungi);
        comandiPanel.add(rimuovi);
        comandiPanel.add(modifica);

        add(comandiPanel, BorderLayout.CENTER);

        //AREA TESTUALE
        String[] colonne = {"Titolo", "Regista", "Anno di uscita", "Genere", "Valutazione", "Stato di visione"};

        tableModel = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabella = new JTable(tableModel);
        JTableHeader header = tabella.getTableHeader();
        header.setBackground(Color.LIGHT_GRAY);
        header.setFont(new Font("Arial", Font.BOLD, 14));
        JScrollPane scrollPane = new JScrollPane(tabella);
        scrollPane.setPreferredSize(new Dimension(800,300));
        add(scrollPane, BorderLayout.SOUTH);


        //CAMPO PER LA RICERCA
        JPanel opPanel = new JPanel();
        opPanel.setLayout(new BoxLayout(opPanel,BoxLayout.Y_AXIS));

        //pannello ricerca
        JPanel ricerca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        campoRicerca = new JTextField(15);

        //menù a tendina per selezionare l'attributo per cui cercare
        String[] attributi = {"Seleziona","Titolo","Regista","Anno di uscita"};
        ricercaBox = new JComboBox<>(attributi);

        //bottone ricerca
        JButton cercaButton = new JButton("Cerca");

        ricerca.add(new Label("Cerca per:"));
        ricerca.add(ricercaBox);
        ricerca.add(campoRicerca);
        ricerca.add(cercaButton);

        //pannello filtra
        JPanel filtra = new JPanel(new FlowLayout(FlowLayout.LEFT));
        campoFiltra = new JTextField(15);

        //menù a tendina per selezionare l'attributo per cui filtrare
        String[] filtri = {"Seleziona","Genere","Stato di visione"};
        filtraBox = new JComboBox<>(filtri);

        //bottone filtra
        JButton filtraButton = new JButton("Filtra");

        filtra.add(new Label("Filtra per:"));
        filtra.add(filtraBox);
        filtra.add(campoFiltra);
        filtra.add(filtraButton);

        //pannello ordina
        JPanel ordina = new JPanel(new FlowLayout(FlowLayout.LEFT));

        //menù a tendina per selezionare l'attributo per cui ordinare
        String[] ordinamento = {"Seleziona","Titolo","Anno di uscita","Valutazione"};
        ordinaBox = new JComboBox<>(ordinamento);

        //bottone ordina
        JButton ordinaButton = new JButton("Ordina");

        ordina.add(new Label("Ordina per:"));
        ordina.add(ordinaBox);
        ordina.add(ordinaButton);

        opPanel.add(ricerca);
        opPanel.add(filtra);
        opPanel.add(ordina);

        add(opPanel, BorderLayout.NORTH);

        setVisible(true);

        aggiungi.addActionListener((e -> aggiungiFilm()));
        rimuovi.addActionListener(e -> rimuoviFilm());
        modifica.addActionListener(e -> modificaFilm());
        cercaButton.addActionListener(e -> ricercaFilm());
        ordinaButton.addActionListener(e -> gestioneOrdinamento());
        filtraButton.addActionListener(e -> gestioneFiltro());

        mostraVideoteca();
    }

    public void setController(VideotecaController controller) {
        this.controller = controller;
    }

    private void aggiungiFilm() {
        FilmFormResult dati = mostraDialogoFilm(null,null,null,null,null,null);
        if (dati == null)
            return;

        if (dati.titolo.isEmpty() || dati.regista.isEmpty() || dati.anno == null) {
            JOptionPane.showMessageDialog(this,"Titolo, regista ed anno devono essere compilati.");
            return;
        }

        String valEff = dati.valutazione.equals("Seleziona") ? null : dati.valutazione;
        String statoEff = dati.stato.equals("Seleziona") ? null : dati.stato;

        controller.aggiunta(dati.titolo,dati.regista,dati.anno,dati.genere,valEff,statoEff);
    }

    private void rimuoviFilm() {
        int rigaSelezionata = tabella.getSelectedRow();
        if (rigaSelezionata == -1) {
            JOptionPane.showMessageDialog(this, "Seleziona prima un film dalla tabella.");
            return;
        }

        String titolo= (String) tableModel.getValueAt(rigaSelezionata, 0);
        String regista = (String) tableModel.getValueAt(rigaSelezionata, 1);
        int anno = (int) tableModel.getValueAt(rigaSelezionata, 2);

        int conferma = JOptionPane.showConfirmDialog(this,
                "Sei sicuro di voler rimuovere il film:\n" + titolo + " (" + anno
                + ") di " + regista + "?", "Conferma rimozione", JOptionPane.YES_NO_OPTION);

        if(conferma == JOptionPane.YES_OPTION) {
            controller.rimozione(titolo,regista,anno);
        }
    }

    private void modificaFilm() {
        int rigaSelezionata = tabella.getSelectedRow();
        if (rigaSelezionata == -1) {
            JOptionPane.showMessageDialog(this, "Seleziona prima un film dalla tabella.");
            return;
        }

        //Recupero i dati del film originale
        String titoloOrig = (String) tableModel.getValueAt(rigaSelezionata, 0);
        String registaOrig = (String) tableModel.getValueAt(rigaSelezionata, 1);
        int annoOrig = (int) tableModel.getValueAt(rigaSelezionata, 2);

        FilmFormResult dati = mostraDialogoFilm(
                titoloOrig,
                registaOrig,
                annoOrig,
                (String) tableModel.getValueAt(rigaSelezionata, 3),
                (String) tableModel.getValueAt(rigaSelezionata, 4),
                (String) tableModel.getValueAt(rigaSelezionata, 5)
        );

        if (dati == null)
            return;

        if (dati.titolo.isEmpty() || dati.regista.isEmpty() || dati.anno == null) {
            JOptionPane.showMessageDialog(this,"Titolo, regista e anno devono essere compilati.");
            return;
        }

        String valEff = dati.valutazione.equals("Seleziona") ? null : dati.valutazione;
        String statoEff = dati.stato.equals("Seleziona") ? null : dati.stato;

        controller.modifica(titoloOrig, registaOrig, annoOrig, dati.titolo, dati.regista, dati.anno, dati.genere, valEff, statoEff);
    }

    private void ricercaFilm() {
        String attributo = (String) ricercaBox.getSelectedItem();
        String valore = campoRicerca.getText().trim();

        if (!valore.isEmpty()) {
            controller.ricerca(attributo,valore);
        }
        else {
            JOptionPane.showMessageDialog(this, "Inserisci un valore da cercare.");
        }
    }

    private void gestioneOrdinamento() {
        String criterio = (String) ordinaBox.getSelectedItem();
        controller.ordina(criterio);
    }

    private void gestioneFiltro() {
        String criterio = (String) filtraBox.getSelectedItem();
        String valore = campoFiltra.getText().trim();

        if (valore.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Inserisci un valore per cui filtrare.");
            return;
        }
        controller.filtra(criterio, valore);
    }

    //per mostrare una lista filtrata o ordinata
    public void mostraFilm(List<Film> lista) {
        tableModel.setRowCount(0);

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La videoteca è vuota.");
        }
        else {
            for (Film f : lista) {
                Object[] riga = {
                        f.getTitolo(),
                        f.getRegista(),
                        f.getAnno(),
                        f.getGenere(),
                        f.getValutazione() != null ? f.getValutazione().toString().replace("_"," ") : "",
                        f.getStato() != null ? f.getStato().toString().replace("_"," ") : ""
                };
                tableModel.addRow(riga);
            }
        }
    }

    //per mostrare tutta la videoteca
    public void mostraVideoteca() {
        mostraFilm(Videoteca.getInstance().getCollezione());
    }

    //metodo ausiliario per costruire il form
    private FilmFormResult mostraDialogoFilm(String titoloIn, String registaIn, Integer annoIn, String genereIn, String valutazioneIn, String statoIn) {
        JTextField titoloField = new JTextField(titoloIn != null ? titoloIn : "");
        JTextField registaField = new JTextField(registaIn != null ? registaIn : "");
        JTextField annoField = new JTextField(annoIn != null ? String.valueOf(annoIn) : "");
        JTextField genereField = new JTextField(genereIn != null ? genereIn : "");

        String[] valutazioniRaw = {"UNA_STELLA", "DUE_STELLE", "TRE_STELLE", "QUATTRO_STELLE", "CINQUE_STELLE"};
        String[] valutazioniFormattate = new String[valutazioniRaw.length+1];
        valutazioniFormattate[0] = "Seleziona";
        for (int i=0; i<valutazioniRaw.length; i++) {
            valutazioniFormattate[i+1] = valutazioniRaw[i].replace("_"," ");
        }
        JComboBox<String> valutazioneBox = new JComboBox<>(valutazioniFormattate);
        valutazioneBox.setSelectedItem(valutazioneIn != null ? valutazioneIn : "Seleziona");

        String[] statiRaw = {"VISTO","DA_VEDERE","IN_VISIONE"};
        String[] statiFormattati = new String[statiRaw.length+1];
        statiFormattati[0] = "Seleziona";
        for (int i=0; i<statiRaw.length; i++) {
            statiFormattati[i+1] = statiRaw[i].replace("_"," ");
        }
        JComboBox<String> statoBox = new JComboBox<>(statiFormattati);
        statoBox.setSelectedItem(statoIn != null ? statoIn : "Seleziona");

        JPanel form = new JPanel(new GridLayout(0,1));
        form.add(new JLabel("Titolo:"));
        form.add(titoloField);
        form.add(new JLabel("Regista:"));
        form.add(registaField);
        form.add(new JLabel("Anno di uscita:"));
        form.add(annoField);
        form.add(new JLabel("Genere:"));
        form.add(genereField);
        form.add(new JLabel("Valutazione:"));
        form.add(valutazioneBox);
        form.add(new JLabel("Stato di visione:"));
        form.add(statoBox);

        int risultato = JOptionPane.showConfirmDialog(this,form,"Dati film", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (risultato == JOptionPane.OK_OPTION) {
            int annoInserito;
            try {
                annoInserito = Integer.parseInt(annoField.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Anno non valido","Errore",JOptionPane.ERROR_MESSAGE);
                return null;
            }

            return new FilmFormResult(titoloField.getText().trim(),
                    registaField.getText().trim(),
                    annoInserito,
                    genereField.getText().trim(),
                    ((String) valutazioneBox.getSelectedItem()).replace(" ","_"),
                    ((String) statoBox.getSelectedItem()).replace(" ","_")
            );
        }
        return null;
    }

    //classe di supporto che contiene i dati
    private static class FilmFormResult {
        String titolo;
        String regista;
        Integer anno;
        String genere;
        String valutazione;
        String stato;

        public FilmFormResult(String titolo, String regista, Integer anno, String genere, String valutazione, String stato) {
            this.titolo = titolo;
            this.regista = regista;
            this.anno = anno;
            this.genere = genere;
            this.valutazione = valutazione;
            this.stato = stato;
        }
    }
}
