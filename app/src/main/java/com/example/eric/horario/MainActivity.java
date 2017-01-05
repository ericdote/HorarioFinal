package com.example.eric.horario;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    boolean preferenciesGuardades;

    /**
     * Metode onCreate on es recupera les SharedPreferences, en cas de tenir algun valor guardat
     * es slta la main i es va directament a la seguent activity, en cas contrari s'espera a omplir les dades.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences settings = getSharedPreferences("PreferenciasHorari", 0);
        if(settings.getBoolean("guardado", false) != false){
            String grup = settings.getString("grup", "A1");
            saltarMain(grup);
        }
        Button btn = (Button) findViewById(R.id.btnHorari);
        btn.setOnClickListener(this);
    }

    /**
     * Metode que guarda les SharedPreferences amb el nom, grup i un boolean per que es sapiga que ja hi ha sharedpreferences guardades amb dades
     * @param tvNom
     * @param spin
     */
    public void guardarPreferences(TextView tvNom, Spinner spin){
        SharedPreferences prefs = getSharedPreferences("PreferenciasHorari", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("nom", tvNom.getText().toString());
        editor.putString("grup", spin.getSelectedItem().toString());
        editor.putBoolean("guardado", true);
        editor.commit();
    }

    /**
     * Metode utilitzat per saltar del Main a un altre activity
     * @param grup
     */
    public void saltarMain(String grup){
        intent = new Intent(this, HorariActivity.class);
        intent.putExtra("grup", grup);
        startActivity(intent);
    }


    /**
     * Metode onClick on nomes hi ha un opcio, la qual es que quan donem a el botó de l'activity
     * els camps han d'estar complets i en cas de ser aixi, es crida al metode guardar preferences
     * i es va a la seguent activity.
     * @param view
     */
    @Override
    public void onClick(View view) {
        intent = new Intent(this, HorariActivity.class);
        TextView tvNom = (TextView) findViewById(R.id.etNom);
        Spinner spin = (Spinner) findViewById(R.id.spinnerCurs);
        if (!(tvNom.getText().toString().equals(""))) {
            guardarPreferences(tvNom, spin);
            intent.putExtra("grup", spin.getSelectedItem().toString());
            startActivity(intent);
        } else {
            Toast.makeText(this, "Introdueix els valors", Toast.LENGTH_SHORT).show();
        }
    }
}
