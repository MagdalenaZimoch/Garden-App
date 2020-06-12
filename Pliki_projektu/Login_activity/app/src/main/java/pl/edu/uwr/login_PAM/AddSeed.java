package pl.edu.uwr.login_PAM;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

public class AddSeed extends AppCompatActivity {

    int id_uz,czestotliwosc_int;

    Button wroc_b, dodaj_b;
    EditText nazwa_et,odmiana_et,osp_et,osk_et,ozp_et,ozk_et,czestotliwosc_et;
    String nazwa,odmiana,osp,osk,ozp,ozk,czestotliwosc;

    DatabasesOpenHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_seed);

        id_uz = getIntent().getIntExtra("id_uzytkownika",0);
        db = new DatabasesOpenHelper(this);

        nazwa_et = findViewById(R.id.editText_nazwa_as);
        odmiana_et = findViewById(R.id.editText_odmiana_as);
        osp_et = findViewById(R.id.editText_osp_as);
        osk_et = findViewById(R.id.editText_osk_as);
        ozp_et = findViewById(R.id.editText_ozp_as);
        ozk_et = findViewById(R.id.editText_ozk_as);
        czestotliwosc_et = findViewById(R.id.editText_czestotliwosc_as);

        wroc_b = findViewById(R.id.wroc_button_as);
        wroc_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(AddSeed.this,Seeds.class);
                mainIntent.putExtra("id_uzytkownika",id_uz);
                startActivity(mainIntent);
                db.close();
                finish();
            }
        });
        dodaj_b = findViewById(R.id.dodaj_button_as);
        dodaj_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check())
                {
                    db.insert_roslina(nazwa,odmiana,osp,osk,ozp,ozk,czestotliwosc_int);
                    Intent mainIntent = new Intent(AddSeed.this,Seeds.class);
                    mainIntent.putExtra("id_uzytkownika",id_uz);
                    startActivity(mainIntent);
                    db.close();
                    finish();
                }
                else
                {
                    // toast
                }
            }
        });
    }
    boolean check()
    {
        nazwa = nazwa_et.getText().toString();
        if(nazwa.equals("")) return false;
        odmiana = odmiana_et.getText().toString();
        if(odmiana.equals("")) return false;
        osp = osp_et.getText().toString();
        if(osp.equals("")) return false;
        osk = osk_et.getText().toString();
        if(osk.equals("")) return false;
        ozp = ozp_et.getText().toString();
        if(ozp.equals("")) return false;
        ozk = ozk_et.getText().toString();
        if(ozk.equals("")) return false;
        czestotliwosc = czestotliwosc_et.getText().toString();
        if(czestotliwosc.equals("")) return false;

        czestotliwosc_int = Integer.parseInt(czestotliwosc);
        System.out.println(czestotliwosc_int);
        Date nowDate = new Date();
        System.out.println("Aktualna data: " + nowDate);
        String date = nowDate.toString();
        String year = date.substring(0,4);
        osp = year + "-"+ osp + " 00:00:00";
        osk = year + "-"+ osk + " 00:00:00";
        ozp = year + "-"+ ozp + " 00:00:00";
        ozk = year + "-"+ ozk + " 00:00:00";
        return true;
    }
}
