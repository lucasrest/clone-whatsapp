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
import whatsapp.rest.com.br.whatsapp.adapter.ConversasAdapter;
import whatsapp.rest.com.br.whatsapp.config.FirebaseConfig;
import whatsapp.rest.com.br.whatsapp.model.Conversation;
import whatsapp.rest.com.br.whatsapp.util.Preferences;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConversasFragment extends Fragment {

    @BindView(R.id.rv_conversas) RecyclerView recyclerView;
    private List<Conversation> conversations;
    private ConversasAdapter conversasAdapter;
    private DatabaseReference reference;
    private ValueEventListener valueEventListener;
    private String idUserLogged;

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

    public ConversasFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversas, container, false);

        ButterKnife.bind( this, view );

        conversations = new ArrayList<>();
        conversasAdapter = new ConversasAdapter( getContext(), conversations );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getActivity() );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setItemAnimator( new DefaultItemAnimator() );
        recyclerView.setAdapter( conversasAdapter );

        Preferences preferences = new Preferences( getContext() );
        idUserLogged = preferences.getData().get("identificatorUserLogged");
        reference = FirebaseConfig.getFireBase()
                .child("conversation")
                .child(idUserLogged);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                conversations.clear();
                for (DataSnapshot data: dataSnapshot.getChildren() )
                    conversations.add( data.getValue(Conversation.class) );
                conversasAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        reference.addValueEventListener( valueEventListener );

        return view;
    }

}
