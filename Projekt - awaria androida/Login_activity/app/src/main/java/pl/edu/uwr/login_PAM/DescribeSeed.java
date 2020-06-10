package pl.edu.uwr.login_PAM;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import androidx.appcompat.app.AppCompatActivity;

public class DescribeSeed extends AppCompatActivity {

    private DatabasesOpenHelper db;
    private Button wroc_btn, edytuj_btn;
    private TextView nazwa_tv,odmiana_tv,okres_siewu_tv,okres_zbioru_tv,czestotliwosc_tv;
    int id_uz, id_ro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_describe_seed);

        id_uz = getIntent().getIntExtra("id_uzytkownika",0);
        id_ro = getIntent().getIntExtra("id_rosliny",0);

        db = new DatabasesOpenHelper(this);

        wroc_btn = findViewById(R.id.button_wroc_ds);
        wroc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(DescribeSeed.this,Seeds.class);
                mainIntent.putExtra("id_uzytkownika",id_uz);
                startActivity(mainIntent);
                finish();
            }
        });
        edytuj_btn = findViewById(R.id.button_edytuj_ds);
        //akcja do edytowania

        nazwa_tv = findViewById(R.id.text_nazwa);
        odmiana_tv = findViewById(R.id.text_odmiana);
        okres_siewu_tv = findViewById(R.id.text_okres_siewu);
        okres_zbioru_tv = findViewById(R.id.text_okres_zbioru);
        czestotliwosc_tv = findViewById(R.id.text_czestotliwosc_podlewania);

        read();
    }
    void read()
    {
        Cursor c = db.getAllData_whereidr("rosliny",id_ro);
        String temp;
        while(c.moveToNext())
        {
            temp = nazwa_tv.getText().toString();
            nazwa_tv.setText(temp + " " + c.getString(1));
            temp = odmiana_tv.getText().toString();
            odmiana_tv.setText(temp + " " + c.getString(2));
            temp = okres_siewu_tv.getText().toString();
            okres_siewu_tv.setText(temp + " \n"+ c.getString(3) + " - "+ c.getString(4));
            temp = okres_zbioru_tv.getText().toString();
            okres_zbioru_tv.setText(temp+ " \n" + c.getString(5)+ " - "+ c.getString(6));
            temp = czestotliwosc_tv.getText().toString();
            czestotliwosc_tv.setText(temp + " co "+ c.getString(7)+" dni");

        }
    }
}
