package pl.edu.uwr.login_PAM;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//obsluguje menu
public class MenuActivity extends AppCompatActivity {

    private Button _moje_ogrodki_btn;
    private Button _nowe_nasiona_btn;
    private Button _moje_powiadomienia_btn;
    int id_uz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        id_uz = getIntent().getIntExtra("id_uzytkownika",0);

        _moje_ogrodki_btn = findViewById(R.id.my_garden_button);
        _nowe_nasiona_btn = findViewById(R.id.add_seed_button);
        _moje_powiadomienia_btn = findViewById(R.id.my_reminder);

        _moje_ogrodki_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(MenuActivity.this,Garden.class);
                mainIntent.putExtra("id_uzytkownika",id_uz);
                startActivity(mainIntent);
                finish();
            }
        });
        _nowe_nasiona_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(MenuActivity.this,Seeds.class);
                startActivity(mainIntent);
                finish();
            }
        });
        _moje_powiadomienia_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(MenuActivity.this,MyReminder.class);
                mainIntent.putExtra("id_uzytkownika",id_uz);
                startActivity(mainIntent);
                finish();
            }
        });
    }
}
