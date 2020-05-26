package pl.edu.uwr.login_PAM;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Seeds extends AppCompatActivity {

    private ArrayList<String> _id_rosliny, _nazwa, _odmiana, _okres_siewu_p, _okres_siewu_k,_okres_zbioru_p, _okres_zbioru_k,_czest_podl;
    private ArrayList<TextView> id_rosliny, nazwa, odmiana, okres_siewu_p, okres_siewu_k,okres_zbioru_p, okres_zbioru_k,czest_podl;
    private ArrayList<Button> more_info_b;
    private ArrayList<LinearLayout> container_ll;
    private LinearLayout scroll_ll;
    DatabasesOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeds);
        scroll_ll = findViewById(R.id.roslina_linearlayout);
        db = new DatabasesOpenHelper(this);
        read();
        set();
        show();
    }
    void read()
    {
        _id_rosliny = new ArrayList<>();
        _nazwa = new ArrayList<>();
        _odmiana = new ArrayList<>();
        _okres_siewu_p = new ArrayList<>();
        _okres_siewu_k = new ArrayList<>();
        _okres_zbioru_p = new ArrayList<>();
        _okres_zbioru_k = new ArrayList<>();
        _czest_podl = new ArrayList<>();
        Cursor data = db.getAllData("rosliny");
        while(data.moveToNext()){
            String temp0 = "id: " + data.getInt(0);
            _id_rosliny.add(temp0);
            _nazwa.add(data.getString(1));
            _odmiana.add(data.getString(2));
            _okres_siewu_p.add(data.getString(3));
            _okres_siewu_k.add(data.getString(4));
            _okres_zbioru_p.add(data.getString(5));
            _okres_zbioru_k.add(data.getString(6));
            _czest_podl.add(data.getString(7));
        }
    }
    void set()
    {
        id_rosliny = new ArrayList<>();
        nazwa = new ArrayList<>();
        odmiana = new ArrayList<>();
        okres_siewu_p = new ArrayList<>();
        okres_siewu_k = new ArrayList<>();
        okres_zbioru_p = new ArrayList<>();
        okres_zbioru_k = new ArrayList<>();
        czest_podl = new ArrayList<>();
        more_info_b = new ArrayList<>();

        insertString(_id_rosliny,id_rosliny);
        insertString(_nazwa,nazwa);
        insertString(_odmiana,odmiana);
        insertString(_okres_siewu_p, okres_siewu_p);
        insertString(_okres_siewu_k,okres_siewu_k);
        insertString(_okres_zbioru_p,okres_zbioru_p);
        insertString(_okres_zbioru_k,okres_zbioru_k);
        insertString(_czest_podl,czest_podl);
        Createbutton();

        container_ll = new ArrayList<>();
        LinearLayout.LayoutParams container_param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        container_param.setMargins(0,20,0,0);
        for(int i=0; i<id_rosliny.size();i++)
        {
            LinearLayout container = new LinearLayout(this);
            container.addView(id_rosliny.get(i));
            container.addView(nazwa.get(i));
            container.addView(odmiana.get(i));
            container.addView(okres_siewu_p.get(i));
            container.addView(okres_siewu_k.get(i));
            container.addView(okres_zbioru_p.get(i));
            container.addView(okres_zbioru_k.get(i));
            container.addView(czest_podl.get(i));
            container.addView(more_info_b.get(i));
            container.setLayoutParams(container_param);
            container.setOrientation(LinearLayout.VERTICAL);
            container.setBackgroundResource(R.drawable.test_pop);
            container.setPadding(10,10,10,10);
            container_ll.add(container);
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
        for(String el: _id_rosliny)
        {
            Button btn = new Button(this);
            btn.setText("More info");
            btn.setTextColor(Color.WHITE);
            btn.setBackgroundResource(R.drawable.button_background);
            more_info_b.add(btn);
        }


    }
}
