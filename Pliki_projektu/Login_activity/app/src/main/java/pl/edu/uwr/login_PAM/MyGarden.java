package pl.edu.uwr.login_PAM;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MyGarden extends AppCompatActivity {
    DatabasesOpenHelper db;

    private LinearLayout _rosliny_ll;
    private ArrayList<LinearLayout> _rosliny_vertical;
    private ArrayList<LinearLayout> _rosliny_horizontal_buttony;
    private ArrayList<LinearLayout> _miejsce_status_ll;

    private ArrayList<TextView> _nazwa_rosliny_tv;
    private ArrayList<TextView> _miejsce_rosliny_tv;
    private ArrayList<TextView> _miejsce_string_tv;
    private ArrayList<TextView> _status_rosliny_tv;
    private ArrayList<TextView> _status_string_tv;
    private ArrayList<TextView> _id_rosliny_tv;

    private ArrayList<Button> _zmien_status_b;
    private ArrayList<Button> _obserwacje_b;
    private ArrayList<Button> _powiadomienia_b;
    private ArrayList<Button> _usun_rosline_b;


    private ArrayList<String> nazwa_rosliny_s;
    private ArrayList<String> miejsce_rosliny_s;
    private ArrayList<String> status_rosliny_s;
    private ArrayList<Integer> id_rosliny_s;

    private int id_og;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_garden);
        db = new DatabasesOpenHelper(this);
        id_og = getIntent().getIntExtra("id_ogrodka",0);
        System.out.println(id_og);
        _rosliny_ll = findViewById(R.id.rosliny_w_ogrodku_linearlayout);
        read();
        set();
        show();
    }

    void read()
    {
        nazwa_rosliny_s =  new ArrayList<>();
        miejsce_rosliny_s = new ArrayList<>();
        status_rosliny_s = new ArrayList<>();
        id_rosliny_s = new ArrayList<>();

        Cursor rosliny_c = db.get_Where_Garden_egzemplarz(id_og,"egzemplarze");

        //przekazuje informacje z bazy danych do odpowiednich tablic
        //odczytane wartosci z bazy danych
        while(rosliny_c.moveToNext()){
            nazwa_rosliny_s.add(rosliny_c.getString(4));
            miejsce_rosliny_s.add(rosliny_c.getString(2));
            status_rosliny_s.add(rosliny_c.getString(3));
            id_rosliny_s.add(rosliny_c.getInt(1));
            System.out.println(rosliny_c.getString(4));
        }
    }
    void set()
    {
        //elementy ui
        _nazwa_rosliny_tv = new ArrayList<>();
        _id_rosliny_tv = new ArrayList<>();
        _miejsce_rosliny_tv = new ArrayList<>();
        _miejsce_string_tv = new ArrayList<>();
        _status_rosliny_tv = new ArrayList<>();

        _obserwacje_b = new ArrayList<>();
        _zmien_status_b = new ArrayList<>();
        _powiadomienia_b = new ArrayList<>();
        _usun_rosline_b = new ArrayList<>();

        wpisz_string(nazwa_rosliny_s, _nazwa_rosliny_tv);
        wpisz_int(id_rosliny_s,_id_rosliny_tv);
        wpisz_string(miejsce_rosliny_s,_miejsce_rosliny_tv);
        wpisz_string(status_rosliny_s,_status_rosliny_tv);

        for(Integer id : id_rosliny_s)
        {
            //tworzenie buttonow
            stworz_btn("O",_obserwacje_b);
            stworz_btn("ZS",_zmien_status_b);
            stworz_btn("P", _powiadomienia_b);
            stworz_btn("U",_usun_rosline_b);
        }
    }
    @SuppressLint("RtlHardcoded")
    void show()
    {
        //elementy scrollview
        //ogolny w jednym egzemplarzu
        _rosliny_vertical = new ArrayList<>();
        //dla buttonow
        _rosliny_horizontal_buttony = new ArrayList<>();
        //dla miejsca i statusu
        _miejsce_status_ll = new ArrayList<>();

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        //wkladanie do linear layout miejsca i statusu
        for(int i=0;i<_nazwa_rosliny_tv.size();i++)
        {
            LinearLayout temp = new LinearLayout(this);

            //przypisanie tekstu do _miejsce_status_ll
            temp.setOrientation(LinearLayout.HORIZONTAL);
            temp.setLayoutParams(params1);
            //temp.addView(_status_string_tv.get(i));
            temp.addView(_status_rosliny_tv.get(i));
            //temp.addView(_miejsce_string_tv.get(i));
            temp.addView(_miejsce_rosliny_tv.get(i));

            _miejsce_status_ll.add(temp);
        }
        //wkladanie buttonow
        for(int i=0;i<_nazwa_rosliny_tv.size();i++)
        {
            LinearLayout temp = new LinearLayout(this);
            temp.setOrientation(LinearLayout.HORIZONTAL);
            temp.setGravity(Gravity.RIGHT);
            temp.addView(_obserwacje_b.get(i));
            temp.addView(_zmien_status_b.get(i));
            temp.addView(_powiadomienia_b.get(i));
            temp.addView(_usun_rosline_b.get(i));
            _rosliny_horizontal_buttony.add(temp);

        }
        //wkladanie calosci dla egzemplarza do scrollView
        for(int i=0;i<_nazwa_rosliny_tv.size();i++)
        {
            LinearLayout temp = new LinearLayout(this);
            temp.setOrientation(LinearLayout.VERTICAL);
            temp.addView(_nazwa_rosliny_tv.get(i));
            temp.addView(_miejsce_status_ll.get(i));
            temp.addView(_id_rosliny_tv.get(i));
            temp.addView(_rosliny_horizontal_buttony.get(i));
            _rosliny_vertical.add(temp);
        }
        //dodaje do linearlayout w scrollview kolejne linear layout
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(0,0,0,20);
        for(LinearLayout el: _rosliny_vertical)
        {
            el.setBackgroundResource(R.drawable.test_pop);
            el.setLayoutParams(params);
            el.setPadding(30,20,20,20);
            _rosliny_ll.addView(el);
        }
    }
    void wpisz_int(ArrayList<Integer> _nazwa_al, ArrayList<TextView> _nazwa_gdzie_al)
    {
        for(Integer el : _nazwa_al)
        {
            //przypisanie wartosci do nazwy rosliny
            TextView temp_tv = new TextView(this);
            temp_tv.setText(String.valueOf(el));
            temp_tv.setTextSize(20);
            temp_tv.setTextColor(Color.WHITE);
            _nazwa_gdzie_al.add(temp_tv);
        }
    }
    void wpisz_string(ArrayList<String> _nazwa_al, ArrayList<TextView> _nazwa_gdzie_al)
    {
        for(String el : _nazwa_al)
        {
            //przypisanie wartosci do nazwy rosliny
            TextView temp_tv = new TextView(this);
            temp_tv.setText(el);
            temp_tv.setTextSize(12);
            temp_tv.setTextColor(Color.WHITE);
            _nazwa_gdzie_al.add(temp_tv);
        }
    }
    void stworz_btn(String _text, ArrayList<Button> _lista)
    {
        LinearLayout.LayoutParams button_param = new LinearLayout.LayoutParams(100,100);
        button_param.setMargins(0,0,20,0);
        Button temp_1 = new Button(this);
        temp_1.setBackgroundResource(R.drawable.button_background);
        Drawable img;
        switch (_text){
            case "O":
                img = temp_1.getContext().getResources().getDrawable( android.R.drawable.ic_menu_view );
                break;
            case "ZS":
                img = temp_1.getContext().getResources().getDrawable( android.R.drawable.ic_menu_edit );
                break;
            case "P":
                img = temp_1.getContext().getResources().getDrawable( android.R.drawable.ic_menu_info_details );
                break;
            case "U":
                img = temp_1.getContext().getResources().getDrawable( android.R.drawable.ic_menu_delete);
                break;
            default:
                img = temp_1.getContext().getResources().getDrawable( android.R.drawable.ic_menu_help );
                System.out.println("Nic nie pasuje");
                break;
        }
        temp_1.setCompoundDrawablesWithIntrinsicBounds(null , null, null, img);
        temp_1.setLayoutParams(button_param);
        temp_1.setPadding(5,5,5,5);
        _lista.add(temp_1);
    }
}
