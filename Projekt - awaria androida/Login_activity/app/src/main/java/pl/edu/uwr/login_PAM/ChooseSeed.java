package pl.edu.uwr.login_PAM;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class ChooseSeed extends AppCompatActivity {

    private ArrayList<Button> nasiona_b;
    DatabasesOpenHelper db;
    private LinearLayout scroll_ll;
    private int id_og,id_uz;
    private Button wroc_b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_seed);
        id_og = getIntent().getIntExtra("id_ogrodka",0);
        id_uz = getIntent().getIntExtra("id_uzytkownika",0);
        scroll_ll = findViewById(R.id.dodaj_nasiona_ll);

        wroc_b = findViewById(R.id.wroc_do_mygarden_button);
        wroc_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(ChooseSeed.this,MyGarden.class);
                mainIntent.putExtra("id_ogrodka",id_og);
                mainIntent.putExtra("id_uzytkownika",id_uz);
                startActivity(mainIntent);
                finish();
            }
        });


        db = new DatabasesOpenHelper(this);
        read();
        set();
        show();
    }
    void read(){
        nasiona_b = new ArrayList<>();
        Cursor data = db.getAllData("rosliny");
        while (data.moveToNext())
        {
            Button temp = new Button(this);
            String temp_s = data.getString(1) + " : " + data.getString(2);
            temp.setBackgroundResource(R.drawable.button_background);
            temp.setId(data.getInt(0));
            System.out.println(data.getInt(0) + data.getString(1));
            temp.setText(temp_s);
            nasiona_b.add(temp);
        }
    }
    void set(){
        for(Button el: nasiona_b)
        {
            el.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mainIntent = new Intent(ChooseSeed.this,AddSeed2.class);
                    mainIntent.putExtra("id_rosliny",view.getId());
                    mainIntent.putExtra("id_ogrodka",id_og);
                    mainIntent.putExtra("id_uzytkownika",id_uz);
                    startActivity(mainIntent);
                    finish();
                }
            });
        }
    }
    void show(){
        for(Button el: nasiona_b){
            scroll_ll.addView(el);
        }
    }
}
