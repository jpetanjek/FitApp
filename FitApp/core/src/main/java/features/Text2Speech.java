package features;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class Text2Speech extends TextToSpeech {

    private static Text2Speech INSTANCE;
    private static float pitch;
    private static float speed;
    private static int QueueFlushParameter = TextToSpeech.QUEUE_FLUSH;

    public Text2Speech(Context context, OnInitListener listener) {
        super(context, listener);
        Log.e("TTS", "Konstruktor!");
    }
    private static void setTTS(){
        pitch = (float) 60 / 50;
        speed = (float) 50 / 50;
        INSTANCE.setPitch(pitch);
        INSTANCE.setSpeechRate(speed);
        Log.e("TTS","setTTS()");
    }

    public static Text2Speech getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new Text2Speech(context, new OnInitListener() {
                @Override
                public void onInit(int status) {
                    if(status == Text2Speech.SUCCESS){
                        setTTS();
                        Log.e("TTS","onInit");
                        int result = INSTANCE.setLanguage(Locale.ENGLISH);
                        if(result == Text2Speech.LANG_MISSING_DATA || result == Text2Speech.LANG_NOT_SUPPORTED){
                            Log.e("TTS","Language not supported!");
                        }
                    }else{
                        Log.e("TTS","Initialization failed!");
                    }
                }
            });
        }
        return (Text2Speech) INSTANCE;
    }
    public static void govori(String text){
        INSTANCE.speak(text,QueueFlushParameter,null);
        Log.e("TTS","Speaking "+text);
    }
    public static void zatvoriTTS(){
        if(INSTANCE != null){
            INSTANCE.stop();
            INSTANCE.shutdown();
        }
    }
}
