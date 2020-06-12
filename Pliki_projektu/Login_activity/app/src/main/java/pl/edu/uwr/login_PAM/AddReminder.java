package pl.edu.uwr.login_PAM;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddReminder extends AppCompatActivity {

    int id_uz, id_e,id_og;
    Button wroc_b,zapisz_b;
    EditText tresc_et, data_et, godzina_et;

    DatabasesOpenHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        db = new DatabasesOpenHelper(this);

        id_og = getIntent().getIntExtra("id_ogrodka",0);
        id_uz = getIntent().getIntExtra("id_uzytkownika",0);
        id_e = getIntent().getIntExtra("id_egzemplarza",0);

        tresc_et = findViewById(R.id.editText_tresc_ar);
        data_et = findViewById(R.id.editText_data_ar);
        godzina_et = findViewById(R.id.editText_godzina_ar);


        wroc_b = findViewById(R.id.button_wroc_ar);
        wroc_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(AddReminder.this,MyGarden.class);
                mainIntent.putExtra("id_uzytkownika",id_uz);
                mainIntent.putExtra("id_ogrodka",id_og);
                startActivity(mainIntent);
                finish();
            }
        });

        zapisz_b = findViewById(R.id.button_zapisz_ar);
        zapisz_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check())
                {
                    String tresc = tresc_et.getText().toString();
                    String data =  data_et.getText().toString() + " "+godzina_et.getText().toString() + ":00";
                    System.out.println(id_e);
                    System.out.println(id_og);
                    System.out.println(id_uz);

                    db.insert_powiadomienia(id_e,id_og,tresc,data);

                    Intent mainIntent = new Intent(AddReminder.this,MyGarden.class);
                    mainIntent.putExtra("id_uzytkownika",id_uz);
                    mainIntent.putExtra("id_ogrodka",id_og);
                    startActivity(mainIntent);
                    finish();
                }
                else
                {
                    //toast
                }

            }
        });
    }
    boolean check()
    {
        if(tresc_et.getText().toString().equals("")) return false;
        if(data_et.getText().toString().equals("")) return false;
        if(godzina_et.getText().toString().equals("")) return false;
        return true;
    }
}
