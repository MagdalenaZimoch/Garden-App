package pl.edu.uwr.login_PAM;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddPrinciple extends AppCompatActivity {

    EditText zasada_et;

    Button wroc_b, zapisz_b;
    int id_uz,id_ro;

    DatabasesOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_principle);

        db = new DatabasesOpenHelper(this);
        id_uz = getIntent().getIntExtra("id_uzytkownika",0);
        id_ro = getIntent().getIntExtra("id_rosliny",0);

        wroc_b = findViewById(R.id.wroc_button_ap);
        wroc_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(AddPrinciple.this,Principles.class);
                mainIntent.putExtra("id_uzytkownika",id_uz);
                mainIntent.putExtra("id_rosliny",id_ro);
                startActivity(mainIntent);
                finish();
            }
        });

        zapisz_b = findViewById(R.id.zapisz_button_ap);
        zapisz_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zasada_et = findViewById(R.id.zasada_editText_ap);
                String temp_zasada = zasada_et.getText().toString();
                if(temp_zasada.equals(""))
                {
                    //toast ze zasada nie moze byc pusta
                }
                else {
                    db.insert_zasada(id_ro, temp_zasada);

                    Intent mainIntent = new Intent(AddPrinciple.this, Principles.class);
                    mainIntent.putExtra("id_uzytkownika", id_uz);
                    mainIntent.putExtra("id_rosliny", id_ro);
                    startActivity(mainIntent);
                    finish();
                }
            }
        });

    }
}
