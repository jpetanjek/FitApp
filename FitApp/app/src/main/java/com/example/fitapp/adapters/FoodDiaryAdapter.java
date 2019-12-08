package com.example.fitapp.adapters;

import com.example.core.entities.Namirnica;

public class FoodDiaryAdapter {
    private static Namirnica namirnica;

    public static Namirnica getNamirnica() {
        return namirnica;
    }
    public static void setNamirnica(Namirnica namirnica) {
        FoodDiaryAdapter.namirnica = namirnica;
    }
}
