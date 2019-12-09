package com.example.fitapp.adapters;

import com.example.core.entities.Namirnica;
import com.example.core.entities.NamirniceObroka;

public class FoodDiaryAdapter {
    private static Namirnica namirnica;
    private static NamirniceObroka namirnicaObroka;

    public static NamirniceObroka getNamirnicaObroka() {
        return namirnicaObroka;
    }

    public static void setNamirnicaObroka(NamirniceObroka namirnicaObroka) {
        FoodDiaryAdapter.namirnicaObroka = namirnicaObroka;
    }

    public static Namirnica getNamirnica() {
        return namirnica;
    }
    public static void setNamirnica(Namirnica namirnica) {
        FoodDiaryAdapter.namirnica = namirnica;
    }
}
