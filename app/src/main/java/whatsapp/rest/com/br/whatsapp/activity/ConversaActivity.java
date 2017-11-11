package whatsapp.rest.com.br.whatsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import whatsapp.rest.com.br.whatsapp.R;
import whatsapp.rest.com.br.whatsapp.adapter.MensagensAdapter;
import whatsapp.rest.com.br.whatsapp.config.FirebaseConfig;
import whatsapp.rest.com.br.whatsapp.model.Contact;
import whatsapp.rest.com.br.whatsapp.model.Conversation;
import whatsapp.rest.com.br.whatsapp.model.Message;
import whatsapp.rest.com.br.whatsapp.util.ConverterBase64;
import whatsapp.rest.com.br.whatsapp.util.Preferences;

public class ConversaActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.rv_conversa) RecyclerView recyclerView;
    @BindView(R.id.edt_message) AppCompatEditText edtMessage;

    private Contact contact;
    private String idUserLogged;
    private String nameReciver;
    private String nameUserLogged;
    private DatabaseReference reference;
    private MensagensAdapter adapter;
    private List<Message> messages;
    private ValueEventListener valueEventListener;

    @Override
    protected void onStart() {
        super.onStart();
        reference.addValueEventListener( valueEventListener );
    }

    @Override
    protected void onStop() {
        super.onStop();
        reference.removeEventListener( valueEventListener );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);
        ButterKnife.bind( this );
        contact = new Contact();
        if (getIntent().getSerializableExtra("contact") != null){
            contact = (Contact) getIntent().getSerializableExtra("contact");
        }
        nameReciver = contact.getName();
        toolbar.setTitle( contact.getName() );
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar( toolbar );
        Preferences preferences = new Preferences( this );
        idUserLogged = preferences.getData().get("identificatorUserLogged");
        nameUserLogged = preferences.getData().get("nameUserLogged");

        recyclerView.setHasFixedSize( true );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( this, LinearLayoutManager.VERTICAL, true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator( new DefaultItemAnimator() );
        messages = new ArrayList<>();
        adapter = new MensagensAdapter( this, messages );
        recyclerView.setAdapter( adapter );

        reference = FirebaseConfig.getFireBase()
                .child("messages")
                .child( idUserLogged )
                .child( contact.getEncodedEmail() );

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messages.clear();
                for (DataSnapshot data: dataSnapshot.getChildren() ){
                    messages.add( data.getValue(Message.class) );
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        reference.addValueEventListener( valueEventListener );

    }

    @OnClick(R.id.btn_send_message)
    public void sendMessage(){
        String message = edtMessage.getText().toString();
        if(!message.isEmpty()){

            saveMessage(idUserLogged, contact.getEncodedEmail(), message);

            saveMessage(contact.getEncodedEmail(), idUserLogged, message);

            saveConversation(idUserLogged, contact.getEncodedEmail(), message, nameReciver);

            saveConversation(contact.getEncodedEmail(), idUserLogged, message, nameUserLogged);

        }
    }

    private boolean saveMessage(String sender, String reciver, String text){
        try {
            Message message = new Message();
            message.setIdUser(idUserLogged);
            message.setMessage(text);
            reference = FirebaseConfig.getFireBase()
                    .child("messages")
                    .child(sender)
                    .child(reciver)
                    .push();
            reference.setValue(message);

            return true;
        }catch (Exception e){
            return false;
        }

    }

    private boolean saveConversation(String sender, String reciver, String text, String name){
        try {
            Conversation conversation = new Conversation();
            conversation.setIdUser(reciver);
            conversation.setName(name);
            conversation.setMessage(text);
            reference = FirebaseConfig.getFireBase()
                    .child("conversation")
                    .child(sender)
                    .child(reciver);
            reference.setValue(conversation);
            return true;
        }catch (Exception e){
            return false;
        }

    }

}
