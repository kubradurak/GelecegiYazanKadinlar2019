package mobil.csystem.org.hafta5.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import mobil.csystem.org.hafta5.R;

public class LoginActivity extends AppCompatActivity {

    EditText login_eposta;
    EditText login_password;
    Button login_giris_yap;
    Button login_kaydol;


    private FirebaseAuth mAuth;

    SharedPreferences pref;  // çerezler için

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        pref = PreferenceManager.getDefaultSharedPreferences(this); //oturumu açık tutma
        String mail = pref.getString("email","");
        String pass = pref.getString("password","");
        String result = pref.getString("result" ,"");

        if(!result.equals("1") && !result.equals("") && result != null){
            Intent i = new Intent (this,MainActivity.class);
            startActivity(i);
            finish();
        }


        login_eposta = findViewById(R.id.Id_login_email);
        login_password = findViewById(R.id.Id_login_password);
        login_eposta.setText(mail);
        login_password.setText(pass);


        login_giris_yap = (Button) findViewById(R.id.btn_sign_in);
        login_kaydol = findViewById(R.id.btn_kaydol);

        mAuth = FirebaseAuth.getInstance();

        login_giris_yap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = login_eposta.getText().toString().trim();
                String userPassword = login_password.getText().toString().trim();

                final SharedPreferences.Editor editor = pref.edit();
                editor.putString("email",userEmail);
                editor.putString("password",userPassword);
                editor.apply();
                editor.commit();


                if (!userEmail.isEmpty() && !userPassword.isEmpty()) {
                    Login(userEmail, userPassword);
                } else {
                    Toast.makeText(getApplicationContext(), "Email ve parola boş bırakılamaz!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        login_kaydol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoRegister();
            }
        });

    }

    private  void Login(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent main=new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(main);
                            finish();
                           // Log.d("EMail", "signInWithEmail:success");
                        } else {
                            //Log.w("Fail", "signInWithEmail:failure", task.getException());

                        }
                    }
                });
    }



    private void gotoRegister(){
        Intent gotoRegisterPage = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(gotoRegisterPage);
    }

}
