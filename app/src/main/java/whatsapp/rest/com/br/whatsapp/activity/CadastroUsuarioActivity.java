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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import whatsapp.rest.com.br.whatsapp.R;
import whatsapp.rest.com.br.whatsapp.config.FirebaseConfig;
import whatsapp.rest.com.br.whatsapp.model.User;
import whatsapp.rest.com.br.whatsapp.util.ConverterBase64;
import whatsapp.rest.com.br.whatsapp.util.Preferences;

public class CadastroUsuarioActivity extends AppCompatActivity {

    @BindView(R.id.edt_nome) public AppCompatEditText edtNome;
    @BindView(R.id.edt_email) public AppCompatEditText edtEmail;
    @BindView(R.id.edt_password) public AppCompatEditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        ButterKnife.bind( this );

    }

    @OnClick(R.id.btn_save)
    public void save(){

        final User user = new User();
        user.setName( edtNome.getText().toString() );
        user.setEmail( edtEmail.getText().toString() );
        user.setPassword( edtPassword.getText().toString() );

        final FirebaseAuth auth = FirebaseConfig.getAuth();

        auth.createUserWithEmailAndPassword(
                user.getEmail(),
                user.getPassword()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String encodedEmail = ConverterBase64.encode( user.getEmail() );
                    user.setId( encodedEmail );
                    user.save();
                    Preferences preferences = new Preferences( CadastroUsuarioActivity.this );
                    preferences.saveData( encodedEmail, user.getName() );
                    startActivity( new Intent( CadastroUsuarioActivity.this, LoginActivity.class ));
                    finish();
                }else {
                    String errorMessage = "";
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e) {
                        errorMessage = "Sua senha deve ter mais caracteres, senha muito fraca.";
                    }catch (FirebaseAuthInvalidCredentialsException e) {
                        errorMessage = "Email informado não é valido";
                    }catch (FirebaseAuthUserCollisionException e) {
                        errorMessage = "Email ja existente";
                    }catch (Exception e){
                        errorMessage = "inexperado(" + e.getMessage() +")";
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroUsuarioActivity.this, "Erro: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
