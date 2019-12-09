package adapter;

import com.example.core.entities.Namirnica;
import com.example.core.entities.NamirniceObroka;

public class CurrentFood {
    private static Namirnica namirnica;
    private static NamirniceObroka namirnicaObroka;

    public static Namirnica getNamirnica() {
        return namirnica;
    }

    public static void setNamirnica(Namirnica namirnica) {
        CurrentFood.namirnica = namirnica;
    }

    public static NamirniceObroka getNamirnicaObroka() {
        return namirnicaObroka;
    }

    public static void setNamirnicaObroka(NamirniceObroka namirnicaObroka) {
        CurrentFood.namirnicaObroka = namirnicaObroka;
    }
}
