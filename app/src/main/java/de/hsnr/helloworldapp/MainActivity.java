package de.hsnr.helloworldapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, View.OnClickListener {
    private TextView nachricht;
    private Button weiterFertig, wechselButton, zur_Kamera;
    private EditText eingabe;
    private boolean ersterKlick;

    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Konstruktor der Oberklasse aufrufen

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nachricht = findViewById(R.id.nachricht);
        weiterFertig = findViewById(R.id.weiter_fertig);

        //Button für Seite 2
        wechselButton = findViewById(R.id.wechsel);
        wechselButton.setOnClickListener(this);

        //Button um zur Kamera zu gelangen
        zur_Kamera = findViewById(R.id.zurKamera);
        zur_Kamera.setOnClickListener((v) -> {
            Intent intent = new Intent(
                this, Kamera.class);
                    //    MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                startActivity(intent);
                     });




        nachricht.setText(R.string.willkommen);
        weiterFertig.setText(R.string.weiter);
        wechselButton.setText("Nächste Seite");
        eingabe = findViewById(R.id.eingabe);

        eingabe.setOnEditorActionListener((v, actionId, event) -> {
            if (weiterFertig.isEnabled()) {
                weiterFertig.performClick();
            }
            return true;
        });

        ersterKlick = true;
        nachricht.setText(R.string.willkommen);
        weiterFertig.setText(R.string.weiter);
        weiterFertig.setOnClickListener(v -> {
            if (ersterKlick) {
                nachricht.setText(getString(R.string.hallo,
                        eingabe.getText()));
                tts = new TextToSpeech(this, this);
                eingabe.setVisibility(View.INVISIBLE);
                weiterFertig.setText(R.string.fertig);
                ersterKlick = false;
            } else {
                finish();
            }
        });

        eingabe.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                weiterFertig.setEnabled(s.length() > 0);
            }
        });
        weiterFertig.setEnabled(false);


    }

    @Override
    public void onInit(int status) {
        eingabe = findViewById(R.id.eingabe);
        tts.setLanguage(Locale.GERMAN);
        tts.speak("Hallo!"+eingabe.getText(), TextToSpeech.QUEUE_FLUSH, null);

       // Intent intent = new Intent (this, Seite2.class);
        //startActivity(intent);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent( this, Seite2.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),R.string.danke, Toast.LENGTH_LONG).show();
        finish();
    }


    }