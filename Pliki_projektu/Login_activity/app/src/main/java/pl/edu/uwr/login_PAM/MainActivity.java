package pl.edu.uwr.login_PAM;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText _username_txt, _password_txt;
    Button _login_btn, _register_btn;
    TextView _register_txt;
    public SqlDatabase gardenDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("tworzymy baze danych");
        gardenDb = new SqlDatabase(this);

        _username_txt = (EditText) findViewById(R.id.name_txt);
        _password_txt = (EditText) findViewById(R.id.password_txt);
        _login_btn = (Button) findViewById(R.id.login_btn);
        _register_btn =  (Button) findViewById(R.id.register_btn);

        _register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(MainActivity.this, Register.class);
                startActivity(registerIntent);
            }
        });
        _login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean access_granted = false;
                String user_name = _username_txt.getText().toString();
                String password = _password_txt.getText().toString();
                ArrayList<String> _all_users = new ArrayList<>();
                ArrayList<String> _all_users_password = new ArrayList<>();
                Cursor users = gardenDb.getAllData("users");
                while(users.moveToNext()){
                    _all_users.add(users.getString(1));
                    _all_users_password.add(users.getString(2));
                }
                for(int i=0;i<_all_users.size();i++)
                {
                    if(user_name.equals(_all_users.get(i)))
                    {
                        if(password.equals(_all_users_password.get(i))) access_granted=true;
                        else ToastMessage("Blad hasła/użytkownika");
                    }
                }
                if(access_granted)
                {
                    Intent menuIntent = new Intent(MainActivity.this, MenuActivity.class);
                    startActivity(menuIntent);
                }

            }
        });

    }
    private void ToastMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
