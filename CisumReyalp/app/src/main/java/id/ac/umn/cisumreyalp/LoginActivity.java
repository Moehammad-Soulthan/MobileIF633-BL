package id.ac.umn.cisumreyalp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private TextView tvErrMsg;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.setTitle("Login Menu");

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        tvErrMsg = findViewById(R.id.tvErrMsg);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( etUsername.getText().toString().equals("uasmobile") &&
                        etPassword.getText().toString().equals("uasmobilegenap") ) {
                    finish();
                    startActivity(new Intent(LoginActivity.this, MusicActivity.class));
                } else {
                    tvErrMsg.setText("(Invalid Username or Password)");
                    Toast toast= Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 200);
                    toast.show();
                }
            }
        });
    }
}