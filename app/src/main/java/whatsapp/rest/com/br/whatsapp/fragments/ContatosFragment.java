package whatsapp.rest.com.br.whatsapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import whatsapp.rest.com.br.whatsapp.R;
import whatsapp.rest.com.br.whatsapp.adapter.ContatosAdapter;
import whatsapp.rest.com.br.whatsapp.config.FirebaseConfig;
import whatsapp.rest.com.br.whatsapp.model.Contact;
import whatsapp.rest.com.br.whatsapp.util.Preferences;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContatosFragment extends Fragment {

    @BindView(R.id.rv_contatos) RecyclerView recyclerView;
    private List<Contact> contacts;
    private ValueEventListener valueEventListener;
    private DatabaseReference reference;
    private ContatosAdapter contatosAdapter;

    @Override
    public void onStart() {
        super.onStart();
        reference.addValueEventListener( valueEventListener );
    }

    @Override
    public void onStop() {
        super.onStop();
        reference.removeEventListener( valueEventListener );
    }

    public ContatosFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contatos, container, false);
        ButterKnife.bind( this, view );
        contacts = new ArrayList<>();
        contatosAdapter = new ContatosAdapter( contacts, getActivity() );
        recyclerView.setHasFixedSize( true );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getActivity() );
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setItemAnimator( new DefaultItemAnimator() );
        recyclerView.setAdapter( contatosAdapter );

        Preferences preferences = new Preferences( getContext() );
        String idUserLogged = preferences.getData().get("identificatorUserLogged");

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contacts.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    contacts.add( data.getValue( Contact.class ) );
                }
                contatosAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        reference = FirebaseConfig.getFireBase()
                .child("contacts")
                .child(idUserLogged);

        reference.addValueEventListener( valueEventListener );



        return view;
    }

}
