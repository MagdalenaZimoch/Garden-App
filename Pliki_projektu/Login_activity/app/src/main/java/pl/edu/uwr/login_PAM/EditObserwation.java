package pl.edu.uwr.login_PAM;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditObserwation extends AppCompatActivity {

    DatabasesOpenHelper db;

    int id_o,id_og,id_eg,id_uz;
    EditText text_obserwation;
    Button wroc_btn,zapisz_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_obserwation);

        id_og = getIntent().getIntExtra("id_ogrodka",0);
        id_eg = getIntent().getIntExtra("id_egzemplarza",0);
        id_uz = getIntent().getIntExtra("id_uzytkownika",0);
        id_o  = getIntent().getIntExtra("id_obserwacji",0);

        text_obserwation = findViewById(R.id.obserwation_edittext);

        db = new DatabasesOpenHelper(this);
        read();

        wroc_btn = findViewById(R.id.btn_wroc_obserw);
        wroc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(EditObserwation.this, Obserwation.class);
                mainIntent.putExtra("id_ogrodka",id_og);
                mainIntent.putExtra("id_egzemplarza",id_eg);
                mainIntent.putExtra("id_uzytkownika",id_uz);
                startActivity(mainIntent);
                finish();
            }
        });

        zapisz_btn = findViewById(R.id.btn_zapisz);
        zapisz_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tresc = text_obserwation.getText().toString();
                db.update_obserwacje(id_o,id_eg,tresc);

                Intent mainIntent = new Intent(EditObserwation.this, Obserwation.class);
                mainIntent.putExtra("id_ogrodka",id_og);
                mainIntent.putExtra("id_egzemplarza",id_eg);
                mainIntent.putExtra("id_uzytkownika",id_uz);
                startActivity(mainIntent);
                finish();
            }
        });
    }
    void read()
    {
        Cursor c =  db.getTextObserwacje(id_o);
        while(c.moveToNext())
        {
            text_obserwation.setText(c.getString(0));
        }
    }
}
