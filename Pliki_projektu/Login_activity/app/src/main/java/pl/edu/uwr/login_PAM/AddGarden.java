package pl.edu.uwr.login_PAM;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddGarden extends AppCompatActivity {

    private Button _wroc_btn;
    private Button _zapisz_btn;
    private EditText _nazwa;
    private EditText _adres;
    private int id_uz;
    DatabasesOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_garden);
        _wroc_btn = findViewById(R.id.back_button);
        _zapisz_btn = findViewById(R.id.safe_button);
        _nazwa = findViewById(R.id.name_garden_editText);
        _adres = findViewById(R.id.adress_editText);

        id_uz = getIntent().getIntExtra("id_uzytkownika",0);
        db = new DatabasesOpenHelper(this);

        _wroc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(AddGarden.this,Garden.class);
                startActivity(mainIntent);
                finish();
            }
        });

        _zapisz_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nazwa = _nazwa.getText().toString();
                String adres = _adres.getText().toString();
                db.insert_ogrodek(id_uz,nazwa,adres);

                Intent mainIntent = new Intent(AddGarden.this,Garden.class);
                startActivity(mainIntent);
                finish();
            }
        });

    }
}
