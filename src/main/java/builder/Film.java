package builder;

import java.util.Objects;

public class Film {
    private String titolo;
    private String regista;
    private int anno;
    private String genere;
    private Valutazione valutazione;
    private StatoVisione stato;

    public static class Builder {

        //parametri obbligatori
        private String titolo;
        private String regista;
        private int anno;

        //parametri opzionali
        private String genere;
        private Valutazione valutazione;
        private StatoVisione stato;

        //costruttore Builder
        public Builder(String titolo, String regista, Integer anno) {
            if (titolo == null || titolo.isBlank())
                throw new IllegalArgumentException("Titolo obbligatorio");
            if (regista == null || regista.isBlank())
                throw new IllegalArgumentException("Autore obbligatorio");
            if (anno == null)
                throw new IllegalArgumentException("Anno obbligatorio");
            int annoCorrente = java.time.Year.now().getValue();
            if(anno < 1880 || anno > annoCorrente)
                throw new IllegalArgumentException("Anno non valido: " + anno);
            this.titolo = titolo;
            this.regista = regista;
            this.anno = anno;
        }

        public Builder genere(String genere) {
            this.genere = genere;
            return this;
        }

        public Builder valutazione(Valutazione valutazione) {
            this.valutazione = valutazione;
            return this;
        }

        public Builder stato(StatoVisione stato) {
            this.stato = stato;
            return this;
        }

        public Film build() {
            return new Film(this);
        }
    }

    //costruttore Film
    private Film(Builder builder) {
        this.titolo = builder.titolo;
        this.regista = builder.regista;
        this.anno = builder.anno;
        this.genere = builder.genere;
        this.valutazione = builder.valutazione;
        this.stato = builder.stato;
    }

    //metodi getter
    public String getTitolo() {
        return titolo;
    }

    public String getRegista() {
        return regista;
    }

    public int getAnno() {
        return anno;
    }

    public String getGenere() {
        return genere;
    }

    public Valutazione getValutazione() {
        return valutazione;
    }

    public StatoVisione getStato() {
        return stato;
    }

    //metodi setter
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setRegista(String regista) {
        this.regista = regista;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public void setValutazione(Valutazione valutazione) {
        this.valutazione = valutazione;
    }

    public void setStato(StatoVisione stato) {
        this.stato = stato;
    }

    @Override
    public String toString() {
        return "Film: {" +
                "titolo: '" + titolo + '\'' +
                ", regista = " + regista +
                ", anno = " + anno +
                ", genere = " + genere +
                ", valutazione = " + valutazione +
                ", stato = " + stato +
                '}';
    }

    @Override
    public boolean equals(Object x)
    {   if (x == null)
            return false;
        if (this == x)
            return true;
        if (!(x instanceof Film))
            return false;
        Film f = (Film) x;
        return anno == f.anno && titolo.equalsIgnoreCase(f.titolo) &&
                regista.equalsIgnoreCase(f.regista);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titolo.toLowerCase(), regista.toLowerCase(), anno);
    }
}
