package com.example.registracija;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.Calendar;

public class Registracija extends AppCompatActivity {

    TextView email;
    ImageView imageView;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registracija);

        final Button date = (Button) findViewById(R.id.btnDateOfBirth);

        date.setOnClickListener(new View.OnClickListener() {
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
                date.setText(dayOfMonth+"."+(month+1)+"."+year);
            }
        };

        Button btnGender = (Button) findViewById(R.id.btnGender);
        btnGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.btnGender) {
                        final String[] sfv = new String[2];
                        sfv[0]="M";
                        sfv[1]="F";

                        AlertDialog.Builder builder = new AlertDialog.Builder(Registracija.this);

                        builder.setTitle("Odabir spola").setItems(sfv, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Button tv1 = (Button)findViewById(R.id.btnGender);
                                tv1.setText(sfv[which]);
                            }
                        });
                        AlertDialog dialog = builder.create();

                        dialog.show();
                }

            }
        });

        final Button numberPicker = (Button) findViewById(R.id.btnCurrentWeight);
        numberPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.btnCurrentWeight) {

                    final NumberPicker numPick = new NumberPicker(Registracija.this);
                    numPick.setMaxValue(200);
                    numPick.setMinValue(20);
                    NumberPicker.OnValueChangeListener myValListener = new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        }
                    };
                    numPick.setOnValueChangedListener(myValListener);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Registracija.this).setView(numPick);
                    builder.setTitle("Odabir težine (KG)");
                    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            numberPicker.setText(""+numPick.getValue()+" kg");

                        }
                    });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }

            }
        });

        final Button heightPicker = (Button) findViewById(R.id.btnHeight);
        heightPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.btnHeight) {

                    final NumberPicker heightPick = new NumberPicker(Registracija.this);
                    heightPick.setMaxValue(250);
                    heightPick.setMinValue(80);
                    NumberPicker.OnValueChangeListener myValListener = new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        }
                    };
                    heightPick.setOnValueChangedListener(myValListener);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Registracija.this).setView(heightPick);
                    builder.setTitle("Odabir visine (CM)");
                    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            heightPicker.setText(""+heightPick.getValue()+" cm");

                        }
                    });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }

            }
        });

        final Button weightGoalPicker = (Button) findViewById(R.id.btnGoalWeight);
        weightGoalPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.btnGoalWeight) {

                    final NumberPicker weightGoalPick = new NumberPicker(Registracija.this);
                    weightGoalPick.setMaxValue(200);
                    weightGoalPick.setMinValue(20);
                    NumberPicker.OnValueChangeListener myValListener = new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        }
                    };
                    weightGoalPick.setOnValueChangedListener(myValListener);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Registracija.this).setView(weightGoalPick);
                    builder.setTitle("Odabir težine (KG)");
                    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            weightGoalPicker.setText(""+weightGoalPick.getValue()+" kg");

                        }
                    });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }

            }
        });

        final Button lossGainPicker = (Button) findViewById(R.id.btnWeightLossOrGainGoal);
        lossGainPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.btnWeightLossOrGainGoal) {

                    final NumberPicker lossGainPick = new NumberPicker(Registracija.this);
                    String[] nums = {"1","2","3","4","5","6","7","8","9","10","-1","-2","-3","-4","-5","-6","-7","-8","-9","-10"};
                    lossGainPick.setDisplayedValues(nums);
                    lossGainPick.setMaxValue(19);
                    lossGainPick.setMinValue(0);
                    NumberPicker.OnValueChangeListener myValListener = new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        }
                    };
                    lossGainPick.setOnValueChangedListener(myValListener);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Registracija.this).setView(lossGainPick);
                    builder.setTitle("Odabir Željene težine (KG)");
                    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            lossGainPicker.setText(""+lossGainPick.getValue()+" kg");

                        }
                    });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }

            }
        });

        final Button register = (Button) findViewById(R.id.btnRgister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.btnRgister) {


                }

            }
        });

        email = findViewById(R.id.tvMail);
        //imageView = findViewById(R.id.IvMail);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personEmail = acct.getEmail();
            Uri personPhoto = acct.getPhotoUrl();

            email.setText(personEmail);
        }


    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] sfv = new String[3];
        sfv[0]="red";
        sfv[1]="blue";
        sfv[2]="green";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("asd")
                .setItems(sfv, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                    }
                });
        return builder.create();
    }

    //Po kliku gumba za registriranje, dodajemo novog korisnika sa
    /*
    Retrofit retrofit = RetrofitInstance.getInstance();
    JsonApi jApi = retrofit.create(JsonApi.class);
    RetroKorisnik noviKorisnik = new RetroKorisnik();
            noviKorisnik.setIme("Matej");
            noviKorisnik.setPrezime("Loncar");
            noviKorisnik.setEmail("mloncar@foi.hr");
            noviKorisnik.setVisina(Float.parseFloat("180.0"));
            noviKorisnik.setRazinaAktivnosti(1);
            noviKorisnik.setCiljMase(180);
            noviKorisnik.setCiljTjednogMrsavljenja(Float.parseFloat("0.5"));
            noviKorisnik.setSpol("M");
            noviKorisnik.setDatumRodenja("01/12/1998");
            Call<Void> poziv = jApi.unesiKorisnika(noviKorisnik.getIme(),noviKorisnik.getPrezime(),acct.getId(),noviKorisnik.getEmail(),
                    noviKorisnik.getVisina(),noviKorisnik.getRazinaAktivnosti(),noviKorisnik.getCiljMase(),
                    noviKorisnik.getCiljTjednogMrsavljenja(),noviKorisnik.getSpol(),noviKorisnik.getDatumRodenja());
            poziv.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.isSuccessful()){
                        System.out.println("Zapisan");
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    System.out.println("Ne radi");
                }
            });

            //još ga nadodati u lokalnu bazu¸¸
     */


}
