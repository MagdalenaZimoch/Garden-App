package pl.edu.uwr.login_PAM;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditReminder extends AppCompatActivity {


    Button wroc_b,zapisz_b,usun_b;
    EditText tresc_et, data_et, godzina_et;

    DatabasesOpenHelper db;
    int id_uz,id_po,id_e,id_og;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reminder);

        db = new DatabasesOpenHelper(this);
        id_uz = getIntent().getIntExtra("id_uzytkownika",0);
        id_po = getIntent().getIntExtra("id_powiadomienia",0);
        tresc_et = findViewById(R.id.editText_tresc_er);
        data_et = findViewById(R.id.editText_data_er);
        godzina_et = findViewById(R.id.editText_godzina_er);

        set();

        wroc_b = findViewById(R.id.button_wroc_er);
        wroc_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(EditReminder.this,MyReminder.class);
                mainIntent.putExtra("id_uzytkownika",id_uz);
                startActivity(mainIntent);
                finish();
            }
        });
        zapisz_b = findViewById(R.id.button_zapisz_er);
        zapisz_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(check())
                {
                    String tresc = tresc_et.getText().toString();
                    String data = data_et.getText().toString() + " " + godzina_et.getText().toString() + ":00";
                    db.update_powiadomienia(id_po,id_e,id_og,tresc,data);

                    Intent mainIntent = new Intent(EditReminder.this,MyReminder.class);
                    mainIntent.putExtra("id_uzytkownika",id_uz);
                    startActivity(mainIntent);
                    finish();
                }
                else {
                    //toast
                }

                }
        });
        usun_b = findViewById(R.id.button_usun_er);
        usun_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.delete_powiadomienia(id_po);

                Intent mainIntent = new Intent(EditReminder.this,MyReminder.class);
                mainIntent.putExtra("id_uzytkownika",id_uz);
                startActivity(mainIntent);
                finish();
            }
        });

    }
    void set()
    {
        Cursor c  = db.getInfoReminder(id_po);
        while (c.moveToNext())
        {
            id_e = c.getInt(1);
            id_og = c.getInt(2);
            tresc_et.setText(c.getString(3));
            data_et.setText(c.getString(4).substring(0,10));
            godzina_et.setText(c.getString(4).substring(11,16));
        }
    }
    boolean check()
    {
        if(tresc_et.getText().toString().equals("")) return false;
        if(data_et.getText().toString().equals("")) return false;
        if(!data_et.getText().toString().substring(4,5).equals("-")) return false;
        if(!data_et.getText().toString().substring(7,8).equals("-")) return false;
        // toast niewlasciwy format daty
        if(godzina_et.getText().toString().equals("")) return false;
        if(godzina_et.getText().toString().length()<5) return false;
        if(!godzina_et.getText().toString().substring(2,3).equals(":")) return false;
        //toast niewslasciwy format godziny
        return true;
    }
}
