package pl.edu.uwr.login_PAM;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
// obsługuje dodanie nowego użytkownika
public class Register extends AppCompatActivity {

    Button _back_to_login_btn;
    Button _register_new_user_btn;
    EditText _name, _password, _confirm_password, _nazwa;
    DatabasesOpenHelper gardenDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        gardenDb = new DatabasesOpenHelper(this);

        _back_to_login_btn = (Button) findViewById(R.id.back_to_login_btn);
        _register_new_user_btn = (Button) findViewById(R.id.register_to_bd_btn);

        _name = (EditText) findViewById(R.id.name_txt);
        _password = (EditText) findViewById(R.id.password_txt);
        _confirm_password = (EditText) findViewById(R.id.repeat_password_txt);
        _nazwa = findViewById(R.id.username_txt);

        _back_to_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(Register.this,MainActivity.class);
                startActivity(mainIntent);
            }
        });
        _register_new_user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = _name.getText().toString();
                String password = _password.getText().toString();
                String confirm_password = _confirm_password.getText().toString();
                String nazwa = _nazwa.getText().toString();
                boolean name_ok, password_ok;

                //sprawdzanie w bd czy istnieje juz taki uzytkownik
                ArrayList<String> _all_users = new ArrayList<>();
                name_ok = true;
                Cursor users = gardenDb.getAllData("uzytkownicy");
                while(users.moveToNext()){
                    _all_users.add(users.getString(2));
                }
                for(int i=0;i<_all_users.size();i++)
                {
                    if(name.equals(_all_users.get(i)))
                    {
                        name_ok=false;
                        ToastMessage("taki użytkownik już istnieje!");
                    }

                }
                //sprawdzanie czy hasla sa takie same
                if(password.equals(confirm_password))
                {
                    password_ok = true;
                }
                else
                {
                    ToastMessage("Hasła nie są takie same!");
                    password_ok = false;
                }

                if(name_ok && password_ok)
                {
                    gardenDb.insert_uzytkownik(name,password,nazwa);
                    ToastMessage("Dodano nowego użytkownika");

                    Intent mainIntent = new Intent(Register.this,MainActivity.class);
                    startActivity(mainIntent);
                }
            }
        });
    }
    private void ToastMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}
