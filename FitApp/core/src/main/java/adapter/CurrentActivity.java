package adapter;

import androidx.appcompat.app.AppCompatActivity;

public class CurrentActivity {
    private static AppCompatActivity activity;

    public static AppCompatActivity getActivity() {
        return activity;
    }

    public static void setActivity(AppCompatActivity activity) {
        CurrentActivity.activity = activity;
    }
}
