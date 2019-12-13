package dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.barkod.R;

public class NumberOfServingsDialog extends Dialog implements View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button Ok,Cancel;
    public int broj;
    public TextView tekst;
    public ImageView plus;
    public ImageView minus;
    OnMyDialogResult mDialogResult;

    public NumberOfServingsDialog(Activity a, int br) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.broj=br;
        this.d=this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.number_of_servings_layout);
        Cancel = (Button) findViewById(R.id.btn_cancel);
        Ok = (Button) findViewById(R.id.btn_ok);
        tekst = (TextView) findViewById(R.id.tekst);
        plus = (ImageView) findViewById(R.id.add);
        minus = (ImageView) findViewById(R.id.minus);
        tekst.setText(String.valueOf(broj));
        plus.setOnClickListener(this);
        minus.setOnClickListener(this);
        Ok.setOnClickListener(this);
        Cancel.setOnClickListener(this);
    }

    private class OKListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

                    if( mDialogResult != null ){
                        mDialogResult.finish(String.valueOf(tekst.getText()));
                    }
                    NumberOfServingsDialog.this.dismiss();

        }
    }

    public void setDialogResult(OnMyDialogResult dialogResult){
        mDialogResult = dialogResult;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.add) {
            broj++;
            tekst.setText(String.valueOf(broj));
        } else if (id==R.id.minus) {
            if (broj > 0)
                broj--;
            tekst.setText(String.valueOf(broj));
        } else if (id == R.id.btn_ok) {
            new OKListener().onClick(v);
        } else if (id == R.id.btn_cancel) {
            d.dismiss();
        }
    }
    public interface OnMyDialogResult{
        void finish(String result);
    }
}