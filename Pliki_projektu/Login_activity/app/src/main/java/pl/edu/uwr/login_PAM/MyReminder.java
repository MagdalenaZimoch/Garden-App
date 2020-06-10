package pl.edu.uwr.login_PAM;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MyReminder extends AppCompatActivity {

    private ArrayList<String> _id_p;
    private ArrayList<String> _id_gzemplarza;
    private ArrayList<String> _id_ogrodka;
    private ArrayList<String> _tresc;
    private ArrayList<String> _data;

    private ArrayList<TextView> id_p_tv;
    private ArrayList<TextView> id_gzemplarza_tv;
    private ArrayList<TextView> id_ogrodka_tv;
    private ArrayList<TextView> tresc_tv;
    private ArrayList<TextView> data_tv;

    private ArrayList<LinearLayout> container_ll;

    private Button wroc;
    private LinearLayout scroll_ll;
    DatabasesOpenHelper db;
    private int id_uz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reminder);
        id_uz = getIntent().getIntExtra("id_uzytkownika",0);
        db = new DatabasesOpenHelper(this);
        wroc = findViewById(R.id.back_button);
        scroll_ll = findViewById(R.id.powiadomienia_linearlayout);
        read();
        set();
        show();
        wroc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(MyReminder.this,MenuActivity.class);
                startActivity(mainIntent);
                finish();
            }
        });
    }
    void read()
    {
        _id_p = new ArrayList<>();
        _id_gzemplarza = new ArrayList<>();
        _id_ogrodka = new ArrayList<>();
        _tresc = new ArrayList<>();
        _data = new ArrayList<>();


        Cursor data = db.getAllData("powiadomienia");
        while(data.moveToNext())
        {
            String temp3 ="Id powiadomienia: " + String.valueOf(data.getInt(0));
            _id_p.add(temp3);
            String temp4 ="Id egzemplarza: " + String.valueOf(data.getInt(1));
            _id_gzemplarza.add(temp4);
            String temp5 ="Id_ogrodka: " + String.valueOf(data.getInt(2));
            _id_ogrodka.add(temp5);
            String temp1 ="Tresc: " + data.getString(3);
            _tresc.add(temp1);
            String temp2 ="Data: " + data.getString(4);
            _data.add(temp2);
        }
    }
    void set()
    {
        id_p_tv = new ArrayList<>();
        id_gzemplarza_tv = new ArrayList<>();
        id_ogrodka_tv = new ArrayList<>();
        tresc_tv = new ArrayList<>();
        data_tv = new ArrayList<>();

        container_ll = new ArrayList<>();

        for(String el: _id_p)
        {
            TextView temp = new TextView(this);
            temp.setText(el);
            temp.setTextColor(Color.WHITE);
            temp.setTextSize(15);
            id_p_tv.add(temp);
        }
        for(String el: _id_gzemplarza)
        {
            TextView temp = new TextView(this);
            temp.setText(el);
            temp.setTextColor(Color.WHITE);
            temp.setTextSize(15);
            id_gzemplarza_tv.add(temp);
        }
        for(String el: _id_ogrodka)
        {
            TextView temp = new TextView(this);
            temp.setText(el);
            temp.setTextColor(Color.WHITE);
            temp.setTextSize(15);
            id_ogrodka_tv.add(temp);
        }
        for(String el: _tresc)
        {
            TextView temp = new TextView(this);
            temp.setText(el);
            temp.setTextColor(Color.WHITE);
            temp.setTextSize(15);
            tresc_tv.add(temp);
        }
        for(String el: _data)
        {
            TextView temp = new TextView(this);
            temp.setText(el);
            temp.setTextColor(Color.WHITE);
            temp.setTextSize(15);
            data_tv.add(temp);
        }

        LinearLayout.LayoutParams container_param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        container_param.setMargins(0,20,0,0);
        for(int i=0; i<data_tv.size();i++)
        {
            LinearLayout temp = new LinearLayout(this);
            temp.setOrientation(LinearLayout.VERTICAL);
            temp.addView(id_p_tv.get(i));
            temp.addView(id_gzemplarza_tv.get(i));
            temp.addView(id_ogrodka_tv.get(i));
            temp.addView(tresc_tv.get(i));
            temp.addView(data_tv.get(i));
            temp.setBackgroundResource(R.drawable.test_pop);
            temp.setLayoutParams(container_param);
            temp.setPadding(20,20,10,10);
            container_ll.add(temp);
        }
    }
    void show()
    {
        for(LinearLayout el : container_ll)
        {
            scroll_ll.addView(el);
        }
    }
}
