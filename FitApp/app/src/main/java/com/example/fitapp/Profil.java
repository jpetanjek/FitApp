package com.example.fitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.Group;

import static com.example.database.MyDatabase.getInstance;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.core.entities.Korisnik;
import com.example.database.MyDatabase;
import com.example.registracija.Registracija;
import com.example.repository.KorisnikDAL;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;


public class Profil extends AppCompatActivity {
    GoogleApiClient mGoogleApiClient;

    TextView email;
    ImageView imageView;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    Float height;
    Float weight;
    Integer weightGoal;
    Float weightGainLossGoal;
    String gender;
    String datumRodenja;

    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_profile);

            Toolbar toolbar = findViewById(R.id.toolbar1);
            setSupportActionBar(toolbar);

            View logoView = getToolbarLogoView(toolbar);
            logoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        Intent i = new Intent(Profil.this, Glavni_Izbornik.class);
                        startActivity(i);
                    }
                    catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }
            });

            Button button = findViewById(R.id.btnSignout);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // ...
                    if (v.getId() == R.id.btnSignout) {
                        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                new ResultCallback<Status>() {
                                    @Override
                                    // Odjava iz aplikacije
                                    public void onResult(@NonNull Status status) {
                                        MyDatabase myDatabase = getInstance(Profil.this);
                                        myDatabase.getKorisnikDAO().brisanjeKorisnika();
                                        Intent intent = new Intent(Profil.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                }
                        );
                        // ...
                    }

                }
            });


            //kod iz registracije on create

            final Button uiPickDate = findViewById(com.example.registracija.R.id.btnDateOfBirth);

            uiPickDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePicker = new DatePickerDialog(Profil.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener,year,month,day);
                    datePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    datePicker.show();
                }
            });

            mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    uiPickDate.setText(dayOfMonth+"."+(month+1)+"."+year);
                    datumRodenja = dayOfMonth+"/"+(month+1)+"/"+year;

                    KorisnikDAL.Azuriraj(getApplicationContext(),"datum_rodenja",datumRodenja);

                }
            };

            final Button uiPickGender = findViewById(com.example.registracija.R.id.btnGender);
            uiPickGender.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(v.getId()== com.example.registracija.R.id.btnGender) {
                        final String[] genderStrings = {"M","F"};
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Profil.this);

                        alertDialogBuilder.setTitle("Odabir spola").setItems(genderStrings, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                uiPickGender.setText(genderStrings[which]);
                                gender=uiPickGender.getText().toString();

                                KorisnikDAL.Azuriraj(getApplicationContext(),"spol",gender);
                            }
                        });

                        AlertDialog dialog = alertDialogBuilder.create();
                        dialog.show();
                    }
                }
            });

            final Button uiPickCurrentWeight = findViewById(com.example.registracija.R.id.btnCurrentWeight);
            uiPickCurrentWeight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(v.getId()== com.example.registracija.R.id.btnCurrentWeight) {
                        final NumberPicker numberPicker = new NumberPicker(Profil.this);
                        numberPicker.setMaxValue(200);
                        numberPicker.setMinValue(20);
                        numberPicker.setValue(65);

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Profil.this).setView(numberPicker);
                        alertDialogBuilder.setTitle("Odabir težine (KG)");

                        alertDialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                uiPickCurrentWeight.setText(""+numberPicker.getValue()+" kg");
                                weight= Float.valueOf(numberPicker.getValue());

                                KorisnikDAL.Azuriraj(getApplicationContext(),"masa",weight.toString());
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

            final Button uiPickHeight = findViewById(com.example.registracija.R.id.btnHeight);
            uiPickHeight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(v.getId()== com.example.registracija.R.id.btnHeight) {

                        final NumberPicker numberPicker = new NumberPicker(Profil.this);
                        numberPicker.setMaxValue(250);
                        numberPicker.setMinValue(80);
                        numberPicker.setValue(165);

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Profil.this).setView(numberPicker);
                        alertDialogBuilder.setTitle("Odabir visine (CM)");

                        alertDialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                uiPickHeight.setText(""+numberPicker.getValue()+" cm");
                                height=Float.valueOf(numberPicker.getValue());

                                KorisnikDAL.Azuriraj(getApplicationContext(),"visina",height.toString());

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

            final Button uiPickWeightGoal = findViewById(com.example.registracija.R.id.btnGoalWeight);
            uiPickWeightGoal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(v.getId()== com.example.registracija.R.id.btnGoalWeight) {

                        final NumberPicker numberPicker = new NumberPicker(Profil.this);
                        numberPicker.setMaxValue(200);
                        numberPicker.setMinValue(20);
                        numberPicker.setValue(65);

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Profil.this).setView(numberPicker);
                        alertDialogBuilder.setTitle("Odabir težine (KG)");

                        alertDialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                uiPickWeightGoal.setText(""+numberPicker.getValue()+" kg");
                                weightGoal=Integer.valueOf(numberPicker.getValue());

                                KorisnikDAL.Azuriraj(getApplicationContext(),"cilj_mase",weightGoal.toString());

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

            final Button uiPickLossGainGoal = findViewById(com.example.registracija.R.id.btnWeightLossOrGainGoal);
            uiPickLossGainGoal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(v.getId()== com.example.registracija.R.id.btnWeightLossOrGainGoal) {
                        //TODO - popraviti unose
                        final NumberPicker lossGainPick = new NumberPicker(Profil.this);
                        final String[] inputValues = {"-0.25","-0.5","-1.0","1.0","0.5","0.25"};
                        lossGainPick.setDisplayedValues(inputValues);
                        lossGainPick.setMaxValue(5);
                        lossGainPick.setMinValue(0);

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Profil.this).setView(lossGainPick);
                        alertDialogBuilder.setTitle("Odabir Željene težine (KG)");

                        alertDialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                uiPickLossGainGoal.setText(""+inputValues[lossGainPick.getValue()]+" kg");
                                weightGainLossGoal=Float.valueOf(inputValues[lossGainPick.getValue()]);

                                KorisnikDAL.Azuriraj(getApplicationContext(),"cilj_tjednog_mrsavljenja",weightGainLossGoal.toString());

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
        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    public static View getToolbarLogoView(Toolbar toolbar){
        boolean hadContentDescription = android.text.TextUtils.isEmpty(toolbar.getLogoDescription());
        String contentDescription = String.valueOf(!hadContentDescription ? toolbar.getLogoDescription() : "logoContentDescription");
        toolbar.setLogoDescription(contentDescription);
        ArrayList<View> potentialViews = new ArrayList<View>();
        toolbar.findViewsWithText(potentialViews,contentDescription, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        View logoIcon = null;

        if(potentialViews.size() > 0){
            logoIcon = potentialViews.get(0);
        }

        if(hadContentDescription)
            toolbar.setLogoDescription(null);

        return logoIcon;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
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
    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.search){
            Toast.makeText(getApplicationContext(), "You can click search", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.settings){
            Toast.makeText(getApplicationContext(), "You can click settings", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

 */
}