package experiment.madilance.firebasephoneverfication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    static EditText phone;
    EditText code;
    Button send,verify;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String mVerificationId;
    FirebaseAuth mAuth;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    //FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onStart() {
        super.onStart();
        //mAuth.addAuthStateListener(mAuthListener);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //mAuth.addAuthStateListener(mAuthListener);
    }




    @Override
    protected void onStop() {
        super.onStop();
        //mAuth.removeAuthStateListener(mAuthListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phone = findViewById(R.id.phonenumber);
        code = findViewById(R.id.codenumber);
        send = findViewById(R.id.send);
        verify = findViewById(R.id.verfy);
        mAuth=FirebaseAuth.getInstance();




        //for one time verification only


        //mAuthListener = new FirebaseAuth.AuthStateListener(){
        //    @Override
        //    public  void  onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
        //        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //        if(user!=null){
        //            Intent intent = new Intent(MainActivity.this, Profile.class);
        //            startActivity(intent);
        //            finish();
         //       }
        //    }


        //};





        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phoneNumber = phone.getText().toString();

                getData();


                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        phoneNumber,        // Phone number to verify
                        60,                 // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        MainActivity.this,               // Activity (for callback binding)
                        mCallbacks);             // ForceResendingToken from callbacks
            }



        });







        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codenumber=code.getText().toString();
                PhoneAuthCredential credential=PhoneAuthProvider.getCredential(mVerificationId,codenumber);
                signInWithPhoneAuthCredential(credential);
            }
        });


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                //signInWithPhoneAuthCredential(credential);
                Toast.makeText(getApplicationContext(),"write the code carefully to avoid error",Toast.LENGTH_SHORT).show();



            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {



                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                Toast.makeText(getApplicationContext(),"code send",Toast.LENGTH_SHORT).show();

                // ...
            }
        };
    }








    private void signInWithPhoneAuthCredential(PhoneAuthCredential Credential) {
        mAuth.signInWithCredential(Credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getApplicationContext(),"sign in success",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(),Profile.class);

                            startActivity(intent);


                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Toast.makeText(getApplicationContext(),"sign in Failed",Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }
    public static String getData()
    {
        return phone.getText().toString();
    }
}
