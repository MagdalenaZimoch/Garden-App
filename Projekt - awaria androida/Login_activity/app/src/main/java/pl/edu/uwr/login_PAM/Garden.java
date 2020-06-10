package pl.edu.uwr.login_PAM;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class Garden extends AppCompatActivity {

    public DatabasesOpenHelper db;
    private Button _dodaj_ogrodek_btn,_wroc_btn;
    private ArrayList<Button> _ogrodki_btn;
    private ArrayList<Integer> _id_ogrodka;
    private LinearLayout _miejsce_na_ogrodki_ll;
    private int id_uz;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garden);
        db = new DatabasesOpenHelper(this);
        id_uz = getIntent().getIntExtra("id_uzytkownika",0);
        _miejsce_na_ogrodki_ll = findViewById(R.id.miejsce_na_ogrodki_ll);

        _wroc_btn = findViewById(R.id.wroc_btn_do_menu);
        _wroc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(Garden.this,MenuActivity.class);
                mainIntent.putExtra("id_uzytkownika",id_uz);
                startActivity(mainIntent);
                finish();
            }
        });

        _dodaj_ogrodek_btn = findViewById(R.id.dodaj_ogrodek_btn);
        _dodaj_ogrodek_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(Garden.this,AddGarden.class);
                mainIntent.putExtra("id_uzytkownika",id_uz);
                startActivity(mainIntent);
                finish();
            }
        });

        read();
        show();

    }

    void read()
    {
        _ogrodki_btn = new ArrayList<>();
        _id_ogrodka = new ArrayList<>();
        LinearLayout.LayoutParams button_param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        button_param.setMargins(0,20,0,0);
        Cursor ogrodki = db.get_Where_User(id_uz,"ogrodki");
        while (ogrodki.moveToNext()) {
            Button ogrodek = new Button(this);
            ogrodek.setText(ogrodki.getString(2));
            System.out.println(ogrodki.getString(3));
            ogrodek.setTextColor(Color.WHITE);
            ogrodek.setBackgroundResource(R.drawable.button_background);
            ogrodek.setLayoutParams(button_param);
            ogrodek.setId(ogrodki.getInt(0));
            ogrodek.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id_og = view.getId();
                    Intent mainIntent = new Intent(Garden.this,MyGarden.class);
                    mainIntent.putExtra("id_ogrodka",id_og);
                    mainIntent.putExtra("id_uzytkownika", id_uz);
                    startActivity(mainIntent);
                    finish();
                }
            });

            _ogrodki_btn.add(ogrodek);
            i++;
        }
    }
    void show()
    {
        for(Button ogrodek : _ogrodki_btn){
            _miejsce_na_ogrodki_ll.addView(ogrodek);
        }

    }
}

