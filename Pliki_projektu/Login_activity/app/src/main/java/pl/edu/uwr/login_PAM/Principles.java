package pl.edu.uwr.login_PAM;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Principles extends AppCompatActivity {

    int id_uz,id_ro;
    DatabasesOpenHelper db;
    Button wroc_b, dodaj_b;
    LinearLayout zadady_ll;

    ArrayList<TextView> tresc_tv;
    ArrayList<Button> usun_b,edytuj_b;
    ArrayList<LinearLayout> buttony_ll, container_ll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principles);

        id_uz = getIntent().getIntExtra("id_uzytkownika",0);
        id_ro = getIntent().getIntExtra("id_rosliny",0);
        db = new DatabasesOpenHelper(this);

        zadady_ll = findViewById(R.id.principles_ll_p);

        read();
        set();
        show();

        wroc_b = findViewById(R.id.wroc_btn_p);
        wroc_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(Principles.this,DescribeSeed.class);
                mainIntent.putExtra("id_uzytkownika",id_uz);
                mainIntent.putExtra("id_rosliny",id_ro);
                startActivity(mainIntent);
                finish();
            }
        });
        dodaj_b = findViewById(R.id.dodaj_zasade_p);
        dodaj_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(Principles.this,AddPrinciple.class);
                mainIntent.putExtra("id_uzytkownika",id_uz);
                mainIntent.putExtra("id_rosliny",id_ro);
                startActivity(mainIntent);
            }
        });

    }
    void read(){
        Cursor c = db.getPrinciples(id_ro);
        tresc_tv = new ArrayList<>();
        usun_b = new ArrayList<>();
        edytuj_b = new ArrayList<>();
        buttony_ll = new ArrayList<>();
        while(c.moveToNext())
        {
            TextView temp_tresc = new TextView(this);
            temp_tresc.setText(c.getString(2));
            tresc_tv.add(temp_tresc);
            Button temp_usun_b = new Button(this);
            temp_usun_b.setId(c.getInt(0));
            usun_b.add(temp_usun_b);
            Button temp_edytuj_b = new Button(this);
            temp_edytuj_b.setId(c.getInt(0));
            edytuj_b.add(temp_edytuj_b);
            LinearLayout temp_ll = new LinearLayout(this);
            temp_ll.setOrientation(LinearLayout.HORIZONTAL);
            buttony_ll.add(temp_ll);
        }
    }
    // ustawianie wygladu
    void set(){

        LinearLayout.LayoutParams tv_param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tv_param.setMargins(0,0,0,0);
        for(TextView el : tresc_tv)
        {
            el.setPadding(10,10,10,10);
            el.setTextSize(15);
            el.setTextColor(Color.WHITE);
            el.setBackgroundResource(R.drawable.test_pop);
            el.setLayoutParams(tv_param);
        }

        LinearLayout.LayoutParams button_param = new LinearLayout.LayoutParams(50,50);
        button_param.setMargins(0,0,20,0);
        Drawable img;
        for(Button el: usun_b )
        {
            el.setGravity(Gravity.CENTER);
            el.setPadding(5,5,5,5);
            el.setLayoutParams(button_param);
            el.setBackgroundResource(R.drawable.button_background);
            img = el.getContext().getResources().getDrawable( android.R.drawable.ic_menu_delete );
            el.setCompoundDrawablesWithIntrinsicBounds(null , null, null, img);
            el.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.delete_zasada(view.getId());
                    zadady_ll.removeAllViews();
                    read();
                    set();
                    show();
                }
            });
        }

        for(Button el: edytuj_b)
        {
            el.setGravity(Gravity.CENTER);
            el.setPadding(5,5,5,5);
            el.setLayoutParams(button_param);
            el.setBackgroundResource(R.drawable.button_background);
            img = el.getContext().getResources().getDrawable( android.R.drawable.ic_menu_edit );
            el.setCompoundDrawablesWithIntrinsicBounds(null , null, null, img);
            el.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mainIntent = new Intent(Principles.this, EditPrinciple.class);
                    mainIntent.putExtra("id_uzytkownika",id_uz);
                    mainIntent.putExtra("id_rosliny",id_ro);
                    mainIntent.putExtra("id_zasady",view.getId());
                    startActivity(mainIntent);
                }
            });
        }

        for(int i = 0; i < buttony_ll.size() ;i++)
        {
            buttony_ll.get(i).setGravity(Gravity.RIGHT);
            buttony_ll.get(i).addView(usun_b.get(i));
            buttony_ll.get(i).addView(edytuj_b.get(i));
        }

        container_ll = new ArrayList<>();
        LinearLayout.LayoutParams ll_param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for(int i = 0; i < buttony_ll.size() ;i++)
        {
            LinearLayout temp  =  new LinearLayout(this);
            temp.setOrientation(LinearLayout.VERTICAL);
            temp.setLayoutParams(ll_param);
            temp.addView(tresc_tv.get(i));
            temp.addView(buttony_ll.get(i));
            container_ll.add(temp);
        }
    }
    void show(){
        for(LinearLayout el: container_ll)
        {
            zadady_ll.addView(el);
        }
    }
}
