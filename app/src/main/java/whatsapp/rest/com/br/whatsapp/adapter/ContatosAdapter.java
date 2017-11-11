package whatsapp.rest.com.br.whatsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import whatsapp.rest.com.br.whatsapp.R;
import whatsapp.rest.com.br.whatsapp.activity.ConversaActivity;
import whatsapp.rest.com.br.whatsapp.model.Contact;

/**
 * Created by LUCAS RODRIGUES on 09/11/2017.
 */

public class ContatosAdapter extends RecyclerView.Adapter<ContatosAdapter.ViewHolder>{

    private List<Contact> contacts;
    private Context context;

    public ContatosAdapter(List<Contact> contacts, Context context){
        this.contacts = contacts;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from( parent.getContext() ).inflate(R.layout.item_contato, parent, false);

        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Contact contact = contacts.get( position );
        holder.tvEmail.setText( contact.getEmail() );
        holder.tvName.setText( contact.getName() );
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( context, ConversaActivity.class );
                intent.putExtra("contact", contact);
                context.startActivity( intent );
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_email) AppCompatTextView tvEmail;
        @BindView(R.id.tv_name) AppCompatTextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }
    }

}
