package repository;

import builder.Film;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class ArchivioVideoteca {
    private static final String nomeFile = "data/videoteca.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void salva(List<Film> collezione) {
        try {
            File file = new File(nomeFile);
            File dir = file.getParentFile();
            if (dir != null && !dir.exists()) {
                dir.mkdirs();
            }

            try (Writer writer = new FileWriter(file)) {
                gson.toJson(collezione, writer);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Film> carica() {
        try (FileReader reader = new FileReader(nomeFile)) {
            Type tipo = new TypeToken<List<Film>>() {}.getType();
            return gson.fromJson(reader, tipo);
        }
        catch (IOException e) {
            return new java.util.ArrayList<>(); //se il file non esiste viene caricata una lista vuota
        }
    }
}
