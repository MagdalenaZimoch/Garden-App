package pl.edu.uwr.login_PAM;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

public class EditSeed extends AppCompatActivity {

    int id_uz,czestotliwosc_int,id_ro;

    Button wroc_b, zapisz_b;
    EditText nazwa_es,odmiana_es,osp_es,osk_es,ozp_es,ozk_es,czestotliwosc_es;
    String nazwa,odmiana,osp,osk,ozp,ozk,czestotliwosc;

    DatabasesOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_seed);

        id_uz = getIntent().getIntExtra("id_uzytkownika",0);
        id_ro = getIntent().getIntExtra("id_rosliny",0);

        db = new DatabasesOpenHelper(this);
        nazwa_es = findViewById(R.id.editText_nazwa_es);
        odmiana_es = findViewById(R.id.editText_odmiana_es);
        osp_es = findViewById(R.id.editText_osp_es);
        osk_es = findViewById(R.id.editText_osk_es);
        ozp_es = findViewById(R.id.editText_ozp_es);
        ozk_es = findViewById(R.id.editText_ozk_es);
        czestotliwosc_es = findViewById(R.id.editText_czestotliwosc_es);

        set();
        wroc_b = findViewById(R.id.button_wroc_es);
        wroc_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(EditSeed.this,DescribeSeed.class);
                mainIntent.putExtra("id_uzytkownika",id_uz);
                mainIntent.putExtra("id_rosliny",id_ro);
                startActivity(mainIntent);
            }
        });
        zapisz_b = findViewById(R.id.button_zapisz_es);
        zapisz_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check())
                {
                    db.update_roslina(id_ro,nazwa,odmiana,osp,osk,ozp,ozk,czestotliwosc_int);
                    db.update_powiadomienia_update_roslina(id_ro,osp,osk,ozp,ozk);
                    Intent mainIntent = new Intent(EditSeed.this,DescribeSeed.class);
                    mainIntent.putExtra("id_uzytkownika",id_uz);
                    mainIntent.putExtra("id_rosliny",id_ro);
                    startActivity(mainIntent);
                }
                else{
                    //toast
                }
            }
        });

    }
    void set()
    {
        Cursor c = db.getSeed(id_ro);
        while (c.moveToNext())
        {
            nazwa_es.setText(c.getString(1));
            odmiana_es.setText(c.getString(2));
            String temp;
            temp = c.getString(3);
            osp_es.setText(temp.substring(5,10));
            temp = c.getString(4);
            osk_es.setText(temp.substring(5,10));
            temp = c.getString(5);
            ozp_es.setText(temp.substring(5,10));
            temp = c.getString(6);
            ozk_es.setText(temp.substring(5,10));
            czestotliwosc_es.setText(c.getString(7));
        }
    }

    boolean check() {


        nazwa = nazwa_es.getText().toString();
        if (nazwa.equals("")) return false;
        odmiana = odmiana_es.getText().toString();
        if (odmiana.equals("")) return false;
        osp = osp_es.getText().toString();
        if (osp.equals("")) return false;
        osk = osk_es.getText().toString();
        if (osk.equals("")) return false;
        ozp = ozp_es.getText().toString();
        if (ozp.equals("")) return false;
        ozk = ozk_es.getText().toString();
        if (ozk.equals("")) return false;
        czestotliwosc = czestotliwosc_es.getText().toString();
        if (czestotliwosc.equals("")) return false;

        czestotliwosc_int = Integer.parseInt(czestotliwosc);
        System.out.println(czestotliwosc_int);
        Date nowDate = new Date();
        System.out.println("Aktualna data: " + nowDate);
        String date = nowDate.toString();
        String year = "2020";
        osp = year + "-" + osp + " 00:00:00";
        osk = year + "-" + osk + " 00:00:00";
        ozp = year + "-" + ozp + " 00:00:00";
        ozk = year + "-" + ozk + " 00:00:00";
        return true;
    }
}
