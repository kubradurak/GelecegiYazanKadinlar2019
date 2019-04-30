package mobil.csystem.org.hafta5.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import mobil.csystem.org.hafta5.R;

public class RegisterActivity extends AppCompatActivity {


    EditText register_email;
    EditText register_password;
    EditText register_password2;
    Button register_kaydol;


    String email, password , passwordOnay;

    private FirebaseAuth mAuth;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mAuth = FirebaseAuth.getInstance();

        register_email = findViewById(R.id.Id_kayit_mail);
        register_password = findViewById(R.id.Id_kayit_password);
        register_password2 = findViewById(R.id.Id_again_password);
        register_kaydol = findViewById(R.id.btn_kayitregister);


        register_kaydol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserInfoAndRegister();
            }
        });

    }





    private void getUserInfoAndRegister(){
        //Stringleri kullanacağımız metot

        email = register_email.getText().toString();
        password = register_password.getText().toString();
        passwordOnay = register_password2.getText().toString();


        if(!email.isEmpty() && !password.isEmpty()  && !passwordOnay.isEmpty()){  // email ve password null değilse kontrolu
            if(password.equals(passwordOnay)){ //karşılaştırma
                register();
            }
            else{
                Toast.makeText(getApplicationContext(), "Parola eşleşmedi", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext() ,"Lütfen boş alanları doldurunuz.",Toast.LENGTH_SHORT).show();

        }
    }

    private void register(){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "Kayıt Olundu!", Toast.LENGTH_SHORT).show();

                            Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(loginIntent);

                        } else {
                            // If sign in fails, display a message to the user.

                        }
                    }
                }).addOnFailureListener(RegisterActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof FirebaseAuthException) {
                    if (((FirebaseAuthException) e).getErrorCode().equals("ERROR_WEAK_PASSWORD")) {
                        Toast.makeText(getApplicationContext(), "Eksik Şifre", Toast.LENGTH_SHORT).show();

                    } else if (((FirebaseAuthException) e).getErrorCode().equals("ERROR_INVALID_EMAIL")) {
                        Toast.makeText(getApplicationContext(), "Geçersiz mail", Toast.LENGTH_SHORT).show();

                    } else if (((FirebaseAuthException) e).getErrorCode().equals("ERROR_EMAIL_ALREADY_IN_USE")) {
                        Toast.makeText(getApplicationContext(), "Mail zaten kayıtlı", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}

