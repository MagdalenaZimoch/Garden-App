package pl.edu.uwr.login_PAM;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddSeed2 extends AppCompatActivity {

    DatabasesOpenHelper db;
    private int id_og,id_r,id_uz;
    private TextView _id_rosliny, _nazwa, _odmiana, _okres_siewu_p, _okres_siewu_k,_okres_zbioru_p, _okres_zbioru_k,_czest_podl;
    private ArrayList<TextView> zasady_tv;
    private LinearLayout scroll_ll;
    private Button dodaj_b,wroc_b;
    private EditText miejsce_et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_seed2);

        id_og = getIntent().getIntExtra("id_ogrodka",0);
        id_r = getIntent().getIntExtra("id_rosliny",0);
        System.out.println(id_r);
        id_uz = getIntent().getIntExtra("id_uzytkownika",0);


        scroll_ll = findViewById(R.id.dodaj_nasiona_ll);
        miejsce_et = findViewById(R.id.miejsce_egzemplarza_editText);
        dodaj_b = findViewById(R.id.dodaj_button);
        wroc_b = findViewById(R.id.wroc_button_add2);

        dodaj_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String miejsce_s="";
                try {
                    miejsce_s = miejsce_et.getText().toString();
                    if(db.insert_egzemplarz(id_og,id_r,miejsce_s,"NZ"))
                    {
                        db.startService(AddSeed2.this, id_uz);
                        Intent mainIntent = new Intent(AddSeed2.this,ChooseSeed.class);
                        mainIntent.putExtra("id_ogrodka",id_og);
                        mainIntent.putExtra("id_uzytkownika",id_uz);
                        startActivity(mainIntent);
                        finish();
                    }
                    else {
                        //toast na takie samo miejsce w ogrodku, ze juz zajete
                        System.out.println("Miejsce jest juz zajete");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Miejsce nie moze byc puste");
                }

            }
        });

        wroc_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(AddSeed2.this,ChooseSeed.class);
                mainIntent.putExtra("id_ogrodka",id_og);
                mainIntent.putExtra("id_uzytkownika",id_uz);
                startActivity(mainIntent);
                finish();
            }
        });

        db = new DatabasesOpenHelper(this);
        read();
        show();
    }
    void read()
    {
        _id_rosliny = new TextView(this);
        _nazwa = new TextView(this);
        _odmiana = new TextView(this);
        _okres_siewu_p = new TextView(this);
        _okres_siewu_k = new TextView(this);
        _okres_zbioru_p = new TextView(this);
        _okres_zbioru_k = new TextView(this);
        _czest_podl = new TextView(this);

        Cursor data_r = db.getAllData_whereidr("rosliny",id_r);
        while (data_r.moveToNext())
        {
            //insertfromcursor(String.valueOf(data_r.getInt(0)), _id_rosliny);
            insertfromcursor("Nazwa:\n" + data_r.getString(1), _nazwa);
            insertfromcursor("Odmiana:\n" +data_r.getString(2), _odmiana);
            insertfromcursor("Początek siewu:\n" + data_r.getString(3), _okres_siewu_p);
            insertfromcursor("Koniec siewu:\n" + data_r.getString(4), _okres_siewu_k);
            insertfromcursor("Początek zbioru:\n" + data_r.getString(5), _okres_zbioru_p);
            insertfromcursor("Koniec zbioru:\n" + data_r.getString(6), _okres_zbioru_k);
            insertfromcursor("Częstotliwość podlewania (dni):\n" + data_r.getString(7), _czest_podl);

        }
        zasady_tv = new ArrayList<>();
        Cursor data_z = db.getAllData_whereidr("zasady",id_r);
        while(data_z.moveToNext())
        {
            TextView temp = new TextView(this);
            temp.setText(data_z.getString(1));
            temp.setTextSize(15);
            temp.setTextColor(Color.WHITE);
            temp.setBackgroundResource(R.drawable.button_background);
            zasady_tv.add(temp);
        }
    }

    void show()
    {
        /*scroll_ll.addView(_id_rosliny);
        scroll_ll.addView(_nazwa);
        scroll_ll.addView(_okres_siewu_p);
        scroll_ll.addView(_okres_siewu_k);
        scroll_ll.addView(_okres_zbioru_p);
        scroll_ll.addView(_okres_zbioru_k);
        scroll_ll.addView(_czest_podl);*/
        LinearLayout.LayoutParams text_param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        text_param.setMargins(0,20,0,0);
        for(TextView el: zasady_tv)
        {
            el.setLayoutParams(text_param);
            el.setPadding(10,10,10,10);
            scroll_ll.addView(el);
        }
    }
    void insertfromcursor(String c, TextView view)
    {
        LinearLayout.LayoutParams text_param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        text_param.setMargins(0,20,0,0);
        view.setPadding(10,10,10,10);
        view.setText(c);
        view.setTextSize(15);
        view.setTextColor(Color.WHITE);
        view.setBackgroundResource(R.drawable.button_background);
        view.setLayoutParams(text_param);
        scroll_ll.addView(view);
    }
}
