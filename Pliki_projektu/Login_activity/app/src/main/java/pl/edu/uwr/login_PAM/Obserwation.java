package pl.edu.uwr.login_PAM;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Obserwation extends AppCompatActivity {

    private ArrayList<TextView> obserwacje;
    private ArrayList<Button> usun_obserwacja_b, zmien_obserwacja_b;
    private ArrayList<LinearLayout> container, container_button;
    private LinearLayout scroll_ll;
    private Button dodaj_obserwacje_b,wroc_b;
    int id_og, id_eg, id_uz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obserwation);

        id_og = getIntent().getIntExtra("id_ogrodka",0);
        id_eg = getIntent().getIntExtra("id_egzemplarza",0);
        id_uz = getIntent().getIntExtra("id_uzytkownika",0);

        dodaj_obserwacje_b = findViewById(R.id.obserwacje_btn);
        scroll_ll = findViewById(R.id.obserwacje_linearlayout);
        wroc_b = findViewById(R.id.wroc_button_do_mygarden);

        dodaj_obserwacje_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(Obserwation.this,AddObserwation.class);
                mainIntent.putExtra("id_ogrodka",id_og);
                mainIntent.putExtra("id_egzemplarza",id_eg);
                mainIntent.putExtra("id_uzytkownika",id_uz);
                startActivity(mainIntent);
                finish();
            }
        });
        wroc_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(Obserwation.this,MyGarden.class);
                mainIntent.putExtra("id_ogrodka",id_og);
                mainIntent.putExtra("id_egzemplarza",id_eg);
                mainIntent.putExtra("id_uzytkownika",id_uz);
                startActivity(mainIntent);
                finish();
            }
        });



        read();
        set();
        show();
    }

    public void read()
    {
        System.out.println("pokazuje na nowo");
        DatabasesOpenHelper db = new DatabasesOpenHelper(this);
        obserwacje = new ArrayList<>();
        usun_obserwacja_b = new ArrayList<>();
        zmien_obserwacja_b = new ArrayList<>();
        Cursor data_o = db.getDataObserwacje(id_eg,"obserwacje");
        LinearLayout.LayoutParams tv_param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tv_param.setMargins(0,20,0,0);
        while (data_o.moveToNext())
        {
            TextView temp = new TextView(this);
            String temp_string = "Data obserwacji: " + data_o.getString(3) + "\n\n" + data_o.getString(2);
            temp.setText(temp_string);
            temp.setPadding(20,20,20,20);
            temp.setBackgroundResource(R.drawable.test_pop);
            temp.setLayoutParams(tv_param);
            obserwacje.add(temp);
            create_btn(data_o.getInt(0),"usun");
            create_btn(data_o.getInt(0),"zmien");

        }
    }
    public void set()
    {
        container_button = new ArrayList<>();
        for(int i=0;i<obserwacje.size();i++)
        {
            LinearLayout temp = new LinearLayout(this);
            temp.setOrientation(LinearLayout.HORIZONTAL);
            temp.setGravity(Gravity.RIGHT);
            temp.addView(usun_obserwacja_b.get(i));
            temp.addView(zmien_obserwacja_b.get(i));
            container_button.add(temp);
        }
        container = new ArrayList<>();
        LinearLayout.LayoutParams ll_param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll_param.setMargins(0,20,0,0);
        for(int i=0;i<obserwacje.size();i++)
        {
            LinearLayout temp = new LinearLayout(this);
            temp.setOrientation(LinearLayout.VERTICAL);
            temp.addView(obserwacje.get(i));
            temp.addView(container_button.get(i));
            container.add(temp);
        }
    }
    public void show()
    {
        for(LinearLayout el : container)
        {
            scroll_ll.addView(el);
        }
    }
    void create_btn(final int id_obs, String miejsce_buttona)
    {
        LinearLayout.LayoutParams btn_param = new LinearLayout.LayoutParams(50, 50);
        btn_param.setMargins(0,0,20,0);

        Button temp = new Button(this);
        temp.setId(id_obs);
        temp.setLayoutParams(btn_param);
        temp.setPadding(5,5,5,5);
        switch (miejsce_buttona){
            case "usun":
                temp.setBackgroundResource(android.R.drawable.ic_menu_delete);
                temp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatabasesOpenHelper db = new DatabasesOpenHelper(Obserwation.this);
                        db.delete_obserwacje(view.getId());
                        scroll_ll.removeAllViews();
                        read();
                        set();
                        show();
                        System.out.println("odswiezam");
                    }
                });
                usun_obserwacja_b.add(temp);
                System.out.println("tworze button");
                break;
            case "zmien":
                temp.setBackgroundResource(android.R.drawable.ic_menu_edit);
                zmien_obserwacja_b.add(temp);
                temp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int id_o = view.getId();
                        Intent mainIntent = new Intent(Obserwation.this,EditObserwation.class);
                        mainIntent.putExtra("id_obserwacji",id_o);
                        mainIntent.putExtra("id_ogrodka",id_og);
                        mainIntent.putExtra("id_egzemplarza",id_eg);
                        mainIntent.putExtra("id_uzytkownika",id_uz);
                        startActivity(mainIntent);
                    }
                });
                break;
        }

    }
}
