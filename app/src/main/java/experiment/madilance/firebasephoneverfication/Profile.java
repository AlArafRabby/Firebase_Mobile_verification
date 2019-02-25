package experiment.madilance.firebasephoneverfication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Profile extends AppCompatActivity {

    Button Logout;
    FirebaseAuth mAuth;
    EditText phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Logout=findViewById(R.id.logout);
        mAuth=FirebaseAuth.getInstance();
        phone=findViewById(R.id.edittextphonenumber);



        String number=MainActivity.getData();

        phone.setText(number);
        setVisible(false);

        //Toast.makeText(getApplicationContext(),"Data from first activity is"+data, Toast.LENGTH_SHORT).show();

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                sendToMain();
            }
        });


    }

    private void sendToMain() {
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
}
