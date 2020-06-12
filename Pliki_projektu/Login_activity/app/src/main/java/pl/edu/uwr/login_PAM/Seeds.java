package pl.edu.uwr.login_PAM;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Seeds extends AppCompatActivity {

    private ArrayList<String>  _nazwa, _odmiana, _okres_siewu_p, _okres_siewu_k,_okres_zbioru_p, _okres_zbioru_k,_czest_podl;
    private ArrayList<TextView> id_rosliny, nazwa, odmiana, okres_siewu_p, okres_siewu_k,okres_zbioru_p, okres_zbioru_k,czest_podl;
    private ArrayList<Integer> _id_rosliny;
    private ArrayList<Button> more_info_b;
    private ArrayList<LinearLayout> container_ll;
    private LinearLayout scroll_ll;
    private Button back,add_b;
    private int id_uz;
    DatabasesOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeds);

        id_uz = getIntent().getIntExtra("id_uzytkownika",0);
        scroll_ll = findViewById(R.id.roslina_linearlayout);

        db = new DatabasesOpenHelper(this);

        read();
        set();
        show();

        back = findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(Seeds.this,MenuActivity.class);
                mainIntent.putExtra("id_uzytkownika",id_uz);
                startActivity(mainIntent);
                finish();
            }
        });
        add_b = findViewById(R.id.dodaj_roslina);
        add_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(Seeds.this,AddSeed.class);
                mainIntent.putExtra("id_uzytkownika",id_uz);
                startActivity(mainIntent);
            }
        });

    }
    void read()
    {
        _id_rosliny = new ArrayList<>();
        _nazwa = new ArrayList<>();
        _odmiana = new ArrayList<>();
        Cursor data = db.getAllData("rosliny");
        while(data.moveToNext()){
            _id_rosliny.add(data.getInt(0));
            _nazwa.add(data.getString(1));
            System.out.println("tutaj wypisuje" + data.getString(1));
            _odmiana.add(data.getString(2));
        }
    }
    void set()
    {
        nazwa = new ArrayList<>();
        odmiana = new ArrayList<>();
        more_info_b = new ArrayList<>();

        insertString(_nazwa,nazwa);
        insertString(_odmiana,odmiana);
        Createbutton();

        container_ll = new ArrayList<>();
        LinearLayout.LayoutParams container_param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        container_param.setMargins(0,20,0,0);
        for(int i=0; i<nazwa.size();i++)
        {
            LinearLayout container = new LinearLayout(this);
            container.addView(nazwa.get(i));
            container.addView(odmiana.get(i));
            container.addView(more_info_b.get(i));
            container.setLayoutParams(container_param);
            container.setOrientation(LinearLayout.VERTICAL);
            container.setBackgroundResource(R.drawable.test_pop);
            container.setPadding(10,10,10,10);
            container_ll.add(container);
            System.out.println(i);
        }

    }
    void show()
    {
        for(LinearLayout el: container_ll)
        {
            scroll_ll.addView(el);

        }
    }
    void insertString(ArrayList<String> where, ArrayList<TextView> to)
    {
        for (String el : where)
        {
            TextView temp = new TextView(this);
            temp.setText(el);
            temp.setTextSize(14);
            temp.setTextColor(Color.WHITE);
            to.add(temp);
        }
    }
    void Createbutton()
    {
        for(Integer el: _id_rosliny)
        {
            Button btn = new Button(this);
            btn.setText("More info");
            btn.setId(el);
            btn.setTextColor(Color.WHITE);
            btn.setBackgroundResource(R.drawable.button_background);
            more_info_b.add(btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = view.getId();
                    Intent mainIntent = new Intent(Seeds.this,DescribeSeed.class);
                    mainIntent.putExtra("id_uzytkownika",id_uz);
                    mainIntent.putExtra("id_rosliny",id);
                    startActivity(mainIntent);
                }
            });
        }


    }
}
