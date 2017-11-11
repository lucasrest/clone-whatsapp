package whatsapp.rest.com.br.whatsapp.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import whatsapp.rest.com.br.whatsapp.R;
import whatsapp.rest.com.br.whatsapp.config.FirebaseConfig;
import whatsapp.rest.com.br.whatsapp.model.User;
import whatsapp.rest.com.br.whatsapp.util.ConverterBase64;
import whatsapp.rest.com.br.whatsapp.util.Preferences;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edt_email) public AppCompatEditText edtEmail;
    @BindView(R.id.edt_password) public AppCompatEditText edtPassword;
    private User user;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;

    @Override
    protected void onStart() {
        super.onStart();
        //databaseReference.addValueEventListener( valueEventListener );
    }

    @Override
    protected void onStop() {
        super.onStop();
       // databaseReference.removeEventListener( valueEventListener );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind( this );
        auth = FirebaseConfig.getAuth();
        checkUserIsLogged();
    }

    @OnClick(R.id.tv_cad)
    public void callCad(){
        startActivity( new Intent( this, CadastroUsuarioActivity.class ));
        finish();
    }

    @OnClick(R.id.btn_login)
    public void login(){

        user = new User();
        user.setEmail( edtEmail.getText().toString() );
        user.setPassword( edtPassword.getText().toString() );

        auth.signInWithEmailAndPassword(
                user.getEmail(),
                user.getPassword()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    final String idUser = ConverterBase64.encode(user.getEmail());
                    databaseReference = FirebaseConfig.getFireBase()
                            .child("users")
                            .child(idUser);

                    valueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.getValue( User.class );
                            Preferences preferences = new Preferences( LoginActivity.this );
                            preferences.saveData(idUser, user.getName());
                            startFirstActivity();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    };
                    databaseReference.addValueEventListener( valueEventListener );
                }else {
                    String errorMessage = "";
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException | FirebaseAuthInvalidCredentialsException e){
                        errorMessage = "E-mail ou senha invalidos";
                    }catch (Exception e){
                        errorMessage = "inexperado(" + e.getMessage() + ")";
                        e.printStackTrace();
                    }
                    Toast.makeText(LoginActivity.this, "Erro: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void startFirstActivity(){
        startActivity( new Intent(this, MainActivity.class));
        finish();
    }

    private void checkUserIsLogged(){
        if(auth.getCurrentUser() != null){
            startFirstActivity();
        }
    }

}
