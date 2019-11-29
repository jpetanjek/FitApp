package adapter;

import com.example.core.entities.Obrok;

public class ObrokAdapter {
    public static String parseObrok(Obrok obrok){
        String returnValue = "";
        switch (obrok){
            case Breakfast:
                returnValue = "Breakfast";
                break;
            case Lunch:
                returnValue = "Lunch";
                break;
            case Dinner:
                returnValue = "Dinner";
                break;
            case Snack:
                returnValue = "Snack";
                break;
        }
        return returnValue;
    }
}
