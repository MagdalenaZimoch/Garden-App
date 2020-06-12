package pl.edu.uwr.login_PAM;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditUser extends AppCompatActivity {

    EditText haslo_et, nowehaslo_et, powtorz_haslo_et;

    Button usun_b, zapisz_b, wroc_b;
    int id_uz;

    DatabasesOpenHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        id_uz = getIntent().getIntExtra("id_uzytkownika",0);

        db = new DatabasesOpenHelper(this);

        haslo_et = findViewById(R.id.password_editText);
        nowehaslo_et = findViewById(R.id.new_password_editText);
        powtorz_haslo_et = findViewById(R.id.new_password_repeat_editText);

        usun_b = findViewById(R.id.usun_uzytkownika_button_eu);
        usun_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.delete_uzytkownik(id_uz);
                db.close();
                Intent mainIntent = new Intent(EditUser.this, MainActivity.class);
                mainIntent.putExtra("id_uzytkownika",id_uz);
                startActivity(mainIntent);
                finish();
            }
        });
        zapisz_b = findViewById(R.id.zapisz_button_eu);
        zapisz_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check()){
                    String nowe_haslo=nowehaslo_et.getText().toString();
                    db.update_uzytkownik(id_uz,nowe_haslo);
                    Intent mainIntent = new Intent(EditUser.this, MenuActivity.class);
                    mainIntent.putExtra("id_uzytkownika",id_uz);
                    startActivity(mainIntent);
                    db.close();
                    finish();
                }
                else{
                    //toast ze zle hasla czy cos
                }

            }
        });
        wroc_b = findViewById(R.id.wroc_button_eu);
        wroc_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(EditUser.this, MenuActivity.class);
                mainIntent.putExtra("id_uzytkownika",id_uz);
                startActivity(mainIntent);
                db.close();
                finish();

            }
        });
    }
    boolean check()
    {
        String wpisane_haslo = haslo_et.getText().toString();
        if(wpisane_haslo.equals("")) return false;
        String nowe_haslo = nowehaslo_et.getText().toString();
        if(nowe_haslo.equals("")) return false;
        String powtorzone_nowe = powtorz_haslo_et.getText().toString();
        if(powtorzone_nowe.equals("")) return false;

        Cursor c = db.getPassword(id_uz);
        while(c.moveToNext())
        {
            if(! wpisane_haslo.equals(c.getString(0))) return false;
        }
        if(! nowe_haslo.equals(powtorzone_nowe)) return false;

        return true;

    }
}
