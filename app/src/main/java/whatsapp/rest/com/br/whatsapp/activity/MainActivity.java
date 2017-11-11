package whatsapp.rest.com.br.whatsapp.activity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import whatsapp.rest.com.br.whatsapp.R;
import whatsapp.rest.com.br.whatsapp.config.FirebaseConfig;
import whatsapp.rest.com.br.whatsapp.fragments.TabFragment;
import whatsapp.rest.com.br.whatsapp.model.Contact;
import whatsapp.rest.com.br.whatsapp.model.User;
import whatsapp.rest.com.br.whatsapp.util.ConverterBase64;
import whatsapp.rest.com.br.whatsapp.util.Preferences;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    private DatabaseReference reference;
    private String idContato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolbar.setTitle("WhatsApp");
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, new TabFragment()).commit();

    }

    private void logout() {
        FirebaseAuth auth = FirebaseConfig.getAuth();
        auth.signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_sair:
                logout();
                return true;
            case R.id.menu_friends:
                addContact();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addContact() {

        final Context context = MainActivity.this;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Adicionar Contato");
        builder.setMessage("Insira o e-mail do contato");
        builder.setCancelable(false);

        final EditText edtEmail = new EditText(context);
        edtEmail.setPadding(8, 8, 8, 8);
        builder.setView(edtEmail);

        builder.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (edtEmail.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Necessário informar o e-mail!", Toast.LENGTH_SHORT).show();
                } else {


                    idContato = ConverterBase64.encode(edtEmail.getText().toString());
                    reference = FirebaseConfig.getFireBase()
                            .child("users")
                            .child(idContato);

                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    if (dataSnapshot.getValue() != null) {
                                        User user = dataSnapshot.getValue(User.class);
                                        Preferences preferences = new Preferences(context);
                                        String idUserLogged = preferences.getData().get("identificatorUserLogged");
                                        Contact contact = new Contact();
                                        contact.setId(idContato);
                                        contact.setEmail(user.getEmail());
                                        contact.setName(user.getName());
                                        reference = FirebaseConfig.getFireBase()
                                                .child("contacts")
                                                .child(idUserLogged)
                                                .child(idContato);
                                        reference.setValue( contact );

                                    } else {
                                        Toast.makeText(context, "Contato não encontrado", Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                }
            }
        });

        builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
