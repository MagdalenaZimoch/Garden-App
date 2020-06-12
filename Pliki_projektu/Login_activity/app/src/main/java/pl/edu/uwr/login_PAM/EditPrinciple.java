package pl.edu.uwr.login_PAM;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditPrinciple extends AppCompatActivity {

    int id_uz,id_ro,id_za;

    DatabasesOpenHelper db;
    EditText tresc_zasady_et;

    Button wroc_b, zapisz_b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_principle);

        id_uz = getIntent().getIntExtra("id_uzytkownika",0);
        id_ro = getIntent().getIntExtra("id_rosliny",0);
        id_za = getIntent().getIntExtra("id_zasady",0);

        db = new DatabasesOpenHelper(this);
        tresc_zasady_et = findViewById(R.id.tresc_editText_ep);
        read();

        wroc_b = findViewById(R.id.wroc_button_ep);
        wroc_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(EditPrinciple.this, Principles.class);
                mainIntent.putExtra("id_uzytkownika",id_uz);
                mainIntent.putExtra("id_rosliny",id_ro);
                startActivity(mainIntent);
                db.close();
                finish();
            }
        });
        zapisz_b = findViewById(R.id.zapisz_button_ep);
        zapisz_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp_zasady = tresc_zasady_et.getText().toString();

                db.update_zasada(id_za,temp_zasady);

                Intent mainIntent = new Intent(EditPrinciple.this, Principles.class);
                mainIntent.putExtra("id_uzytkownika",id_uz);
                mainIntent.putExtra("id_rosliny",id_ro);
                startActivity(mainIntent);
                db.close();
                finish();
            }
        });


    }
    void read()
    {
        Cursor c = db.getTextZasady(id_za);
        while(c.moveToNext())
        {
            tresc_zasady_et.setText(c.getString(0));
        }
    }
}
