package pl.edu.uwr.login_PAM;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText _username_txt, _password_txt;
    Button _login_btn, _register_btn;
    TextView _register_txt;
    SqlDatabase gardenDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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


    }
}
