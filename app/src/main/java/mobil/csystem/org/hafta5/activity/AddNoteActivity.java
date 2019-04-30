package mobil.csystem.org.hafta5.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import mobil.csystem.org.hafta5.R;

public class AddNoteActivity extends AppCompatActivity {

    EditText userNoteEt;
    Button addNoteEkle;
    Button gotoNotePage;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        userNoteEt = findViewById(R.id.add_note);
        addNoteEkle = findViewById(R.id.btn_add_note);
        gotoNotePage = findViewById(R.id.btn_goto_note);


        addNoteEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNote();
            }
        });


        gotoNotePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });

    }

    private void addNote(){
        // FireBase yazma

        firebaseDatabase = FirebaseDatabase.getInstance();

        myRef = firebaseDatabase.getReference().child("GezdigimYerler");

        String notesId = myRef.push().getKey();
        String userNote = userNoteEt.getText().toString();

        if(userNote.length() > 0){
            myRef.child(notesId).child("sehirAdi").setValue(userNote);
            showDialog("İşlem Başarılı" , "Notunuz kaydedildi");
        } else {
            showDialog("İşlem Başarısız","Not alanı boş bırakılamaz");
        }


        userNoteEt.setText("");

    }
    private  void showDialog(String title , String message){
        final AlertDialog.Builder builder = new AlertDialog.Builder(AddNoteActivity.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("Tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }


}
