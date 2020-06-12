package pl.edu.uwr.login_PAM;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditGarden extends AppCompatActivity {


    private EditText nazwa_et, adres_et;
    private Button wroc_b, zapisz_b;
    DatabasesOpenHelper db;
    int id_og,id_uz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_garden);

        id_og = getIntent().getIntExtra("id_ogrodka",0);
        id_uz = getIntent().getIntExtra("id_uzytkownika",0);

        db = new DatabasesOpenHelper(this);

        nazwa_et = findViewById(R.id.nazwa_editText);
        adres_et = findViewById(R.id.adres_editText);
        set();

        wroc_b = findViewById(R.id.wroc_btn_eg);
        wroc_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(EditGarden.this,MyGarden.class);
                mainIntent.putExtra("id_ogrodka",id_og);
                mainIntent.putExtra("id_uzytkownika",id_uz);
                startActivity(mainIntent);
                finish();
            }
        });
        zapisz_b = findViewById(R.id.zapisz_btn_eg);
        zapisz_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nowa_nazwa = nazwa_et.getText().toString();
                String nowy_adres = adres_et.getText().toString();

                db.update_ogrodek(id_og,nowa_nazwa,nowy_adres);

                Intent mainIntent = new Intent(EditGarden.this,MyGarden.class);
                mainIntent.putExtra("id_ogrodka",id_og);
                mainIntent.putExtra("id_uzytkownika",id_uz);
                startActivity(mainIntent);
                finish();
            }
        });
    }
    void set()
    {
        Cursor c = db.getInfoGarden(id_og);
        while (c.moveToNext())
        {
            nazwa_et.setText(c.getString(0));
            adres_et.setText(c.getString(1));
        }

    }
}
