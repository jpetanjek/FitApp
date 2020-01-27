package com.example.registracija;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.webservice.JsonApi;
import com.example.webservice.RetrofitInstance;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;

import RetroEntities.RetroKorisnik;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Registracija extends AppCompatActivity {

    TextView email;
    ImageView imageView;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    Float height;
    Float weight;
    Float weightGoal;
    Float weightGainLossGoal;
    String gender;
    String datumRodenja;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registracija);


        final Button uiPickDate = (Button) findViewById(R.id.btnDateOfBirth);


        uiPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(Registracija.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener,year,month,day);
                datePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePicker.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                uiPickDate.setText(dayOfMonth+"."+(month+1)+"."+year);
                datumRodenja = dayOfMonth+"/"+(month+1)+"/"+year;
            }
        };

        final Button uiPickGender = findViewById(R.id.btnGender);
        uiPickGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.btnGender) {
                        final String[] genderStrings = {"M","F"};
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Registracija.this);

                        alertDialogBuilder.setTitle("Odabir spola").setItems(genderStrings, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                uiPickGender.setText(genderStrings[which]);
                                gender=uiPickGender.getText().toString();
                            }
                        });

                        AlertDialog dialog = alertDialogBuilder.create();
                        dialog.show();
                }
            }
        });

        final Button uiPickCurrentWeight = findViewById(R.id.btnCurrentWeight);
        uiPickCurrentWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.btnCurrentWeight) {
                    final NumberPicker numberPicker = new NumberPicker(Registracija.this);
                    numberPicker.setMaxValue(200);
                    numberPicker.setMinValue(20);
                    numberPicker.setValue(65);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Registracija.this).setView(numberPicker);
                    alertDialogBuilder.setTitle("Odabir težine (KG)");

                    alertDialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            uiPickCurrentWeight.setText(""+numberPicker.getValue()+" kg");
                            weight= Float.valueOf(numberPicker.getValue());
                        }
                    });

                    alertDialogBuilder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialogBuilder.show();

                    /*

                    NumberPicker.OnValueChangeListener myValListener = new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        }
                    };
                    numberPicker.setOnValueChangedListener(myValListener);
                    */
                }

            }
        });

        final Button uiPickHeight = findViewById(R.id.btnHeight);
        uiPickHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.btnHeight) {

                    final NumberPicker numberPicker = new NumberPicker(Registracija.this);
                    numberPicker.setMaxValue(250);
                    numberPicker.setMinValue(80);
                    numberPicker.setValue(165);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Registracija.this).setView(numberPicker);
                    alertDialogBuilder.setTitle("Odabir visine (CM)");

                    alertDialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            uiPickHeight.setText(""+numberPicker.getValue()+" cm");
                            height=Float.valueOf(numberPicker.getValue());
                        }
                    });

                    alertDialogBuilder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alertDialogBuilder.show();

                    /*
                    NumberPicker.OnValueChangeListener myValListener = new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        }
                    };
                    heightPick.setOnValueChangedListener(myValListener);
                    */
                }
            }
        });

        final Button uiPickWeightGoal = findViewById(R.id.btnGoalWeight);
        uiPickWeightGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.btnGoalWeight) {

                    final NumberPicker numberPicker = new NumberPicker(Registracija.this);
                    numberPicker.setMaxValue(200);
                    numberPicker.setMinValue(20);
                    numberPicker.setValue(65);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Registracija.this).setView(numberPicker);
                    alertDialogBuilder.setTitle("Odabir težine (KG)");

                    alertDialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            uiPickWeightGoal.setText(""+numberPicker.getValue()+" kg");
                            weightGoal=Float.valueOf(numberPicker.getValue());
                        }
                    });

                    alertDialogBuilder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alertDialogBuilder.show();

                    /*
                    NumberPicker.OnValueChangeListener myValListener = new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        }
                    };
                    numberPicker.setOnValueChangedListener(myValListener);
                    */
                }
            }
        });

        final Button uiPickLossGainGoal = findViewById(R.id.btnWeightLossOrGainGoal);
        uiPickLossGainGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.btnWeightLossOrGainGoal) {
                    //TODO - popraviti unose
                    final NumberPicker lossGainPick = new NumberPicker(Registracija.this);
                    final String[] inputValues = {"-0.25","-0.5","-1.0","1.0","0.5","0.25"};
                    lossGainPick.setDisplayedValues(inputValues);
                    lossGainPick.setMaxValue(5);
                    lossGainPick.setMinValue(0);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Registracija.this).setView(lossGainPick);
                    alertDialogBuilder.setTitle("Odabir Željene težine (KG)");

                    alertDialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            uiPickLossGainGoal.setText(""+inputValues[lossGainPick.getValue()]+" kg");
                            weightGainLossGoal=Float.valueOf(inputValues[lossGainPick.getValue()]);
                        }
                    });

                    alertDialogBuilder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alertDialogBuilder.show();

                    /*
                    NumberPicker.OnValueChangeListener myValListener = new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        }
                    };
                    lossGainPick.setOnValueChangedListener(myValListener);
                    */
                }
            }
        });

        final Button uiRegister = findViewById(R.id.btnRegister);
        uiRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.btnRegister && height!=null && datumRodenja!=null && weight!=null && weightGoal!=null && weightGainLossGoal!=null && gender!=null) {
                    //Po kliku gumba za registriranje, dodajemo novog korisnika sa

                    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(Registracija.this);

                    Retrofit retrofit = RetrofitInstance.getInstance();
                    JsonApi jApi = retrofit.create(JsonApi.class);
                    RetroKorisnik noviKorisnik = new RetroKorisnik();

                            noviKorisnik.setGoogle_id(account.getId());
                            noviKorisnik.setIme(account.getDisplayName());
                            noviKorisnik.setPrezime(account.getFamilyName());
                            noviKorisnik.setEmail(account.getEmail());
                            noviKorisnik.setVisina(height);
                            noviKorisnik.setDatumRodenja(datumRodenja);
                            noviKorisnik.setMasa(weight);
                            noviKorisnik.setCiljMase(weightGoal);
                            noviKorisnik.setCiljTjednogMrsavljenja(weightGainLossGoal);
                            noviKorisnik.setSpol(gender);

                            Call<Void> poziv = jApi.unesiKorisnika(noviKorisnik.getIme(),noviKorisnik.getPrezime(),noviKorisnik.getGoogle_id(),account.getEmail(),
                                    noviKorisnik.getVisina(),noviKorisnik.getMasa(),noviKorisnik.getCiljMase(),
                                    noviKorisnik.getCiljTjednogMrsavljenja(),noviKorisnik.getSpol(),noviKorisnik.getDatumRodenja());

                            poziv.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if(response.isSuccessful()){
                                        System.out.println("Zapisan");
                                            finishActivity(0);
                                            finish();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    System.out.println("Ne radi");
                                }
                            });
                }
                else{
                    System.out.println("error");
                    AlertDialog.Builder alertDialogBuilder= new AlertDialog.Builder(Registracija.this);
                    alertDialogBuilder.setMessage("Please fill out necessary information").setTitle("Registration alert");
                    alertDialogBuilder.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog dialog = alertDialogBuilder.create();
                    dialog.show();

                    final Button neutralButton = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);
                    LinearLayout.LayoutParams positiveButtonLL = (LinearLayout.LayoutParams) neutralButton.getLayoutParams();
                    positiveButtonLL.weight=10;
                    positiveButtonLL.gravity = Gravity.CENTER;
                    neutralButton.setLayoutParams(positiveButtonLL);

                }


            }
        });

        email = findViewById(R.id.tvMail);

        imageView = findViewById(R.id.profile_image);


        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personEmail = acct.getEmail();
            Uri personPhoto = acct.getPhotoUrl();
            //TODO - acct.getPhotoUrl() vraca null, testano bez slike na profilu i sa slikom na profilu
            email.setText(personEmail);

            Glide.with(this).load(String.valueOf(personPhoto)).into(imageView);
        }


        }



    public static Drawable LoadImageFromUrl (String url){
        try{
            InputStream inputStream = (InputStream) new URL(url).getContent();
            Drawable googleProfileImage = Drawable.createFromStream(inputStream, "google");
            return googleProfileImage;
        }
        catch(Exception e){
            return null;
        }
    }

}
