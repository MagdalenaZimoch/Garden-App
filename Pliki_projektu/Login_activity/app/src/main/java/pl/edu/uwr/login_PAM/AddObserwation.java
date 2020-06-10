package pl.edu.uwr.login_PAM;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddObserwation extends AppCompatActivity {

    DatabasesOpenHelper db;
    private Button zapisz,wroc;
    private EditText _tresc;
    int id_og,id_eg,id_uz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_obserwation);
        id_og = getIntent().getIntExtra("id_ogrodka",0);
        id_eg = getIntent().getIntExtra("id_egzemplarza",0);
        id_uz = getIntent().getIntExtra("id_uzytkownika",0);

        db= new DatabasesOpenHelper(this);
        _tresc = findViewById(R.id.tresc_editText);
        zapisz = findViewById(R.id.save_btn);
        zapisz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String tresc = _tresc.getText().toString();
                    db.insert_obserwacje(id_eg,tresc);

                    Intent mainIntent = new Intent(AddObserwation.this,Obserwation.class);
                    mainIntent.putExtra("id_ogrodka",id_og);
                    mainIntent.putExtra("id_egzemplarza",id_eg);
                    mainIntent.putExtra("id_uzytkownika",id_uz);
                    startActivity(mainIntent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        wroc = findViewById(R.id.wroc_btn);
        wroc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(AddObserwation.this,Obserwation.class);
                mainIntent.putExtra("id_ogrodka",id_og);
                mainIntent.putExtra("id_egzemplarza",id_eg);
                mainIntent.putExtra("id_uzytkownika",id_uz);
                startActivity(mainIntent);
            }
        });

    }
}
