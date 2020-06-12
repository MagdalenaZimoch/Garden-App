package pl.edu.uwr.login_PAM;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

//edycja miejsca egzemplarza oraz edycja statusu

public class EditEgzemplarz extends AppCompatActivity {

    int id_og,id_eg,id_uz;
    String status;

    DatabasesOpenHelper db;

    Button wroc_btn, zapisz_btn;
    EditText miejsce_et;
    RadioGroup status_rg;
    RadioButton status_nz,status_z,status_dz,status_ze;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_egzemplarz);

        id_og = getIntent().getIntExtra("id_ogrodka",0);
        id_uz = getIntent().getIntExtra("id_uzytkownika",0);
        id_eg = getIntent().getIntExtra("id_egzemplarza",0);

        miejsce_et = findViewById(R.id.miejsce_edittext);
        status_rg = findViewById(R.id.status_radiogroup);

        status_nz = findViewById(R.id.radio_nz);
        status_z = findViewById(R.id.radio_z);
        status_dz = findViewById(R.id.radio_dz);
        status_ze = findViewById(R.id.radio_ze);

        db = new DatabasesOpenHelper(this);
        read();
        // czytamy

        wroc_btn = findViewById(R.id.wroc_ee);
        wroc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(EditEgzemplarz.this,MyGarden.class);
                mainIntent.putExtra("id_ogrodka",id_og);
                mainIntent.putExtra("id_uzytkownika",id_uz);
                startActivity(mainIntent);
            }
        });

        zapisz_btn = findViewById(R.id.zapisz_ee);
        zapisz_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // update bazy danych
                check();    // SPRAWDZAMY STATUS
                String miejsce = miejsce_et.getText().toString(); // SPRAWDZAMY NA JAKI ZMIENILISMY

                db.update_egzemplarz(id_eg,id_og,miejsce,status); //UPDATE

                Intent mainIntent = new Intent(EditEgzemplarz.this,MyGarden.class);
                mainIntent.putExtra("id_ogrodka",id_og);
                mainIntent.putExtra("id_uzytkownika",id_uz);
                startActivity(mainIntent);
            }
        });
    }
    void read() {
        Cursor c = db.getPlaceEgzemplarz(id_eg);
        while(c.moveToNext()) {
            miejsce_et.setText(c.getString(0));
            status = c.getString(1);
        }
        switch (status){
            case "NZ":
                status_nz.setChecked(true);
                break;
            case"Z":
                status_z.setChecked(true);
                break;
            case"DZ":
                status_dz.setChecked(true);
                break;
            case "ZE":
                status_ze.setChecked(true);
                break;
        }
    }

    void check()
    {
        if(status_nz.isChecked()) status = "NZ";
        if(status_z.isChecked()) status = "Z";
        if(status_dz.isChecked()) status = "DZ";
        if(status_ze.isChecked()) status = "ZE";
    }
}
