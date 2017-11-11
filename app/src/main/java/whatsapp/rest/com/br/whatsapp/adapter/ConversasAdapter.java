package whatsapp.rest.com.br.whatsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import whatsapp.rest.com.br.whatsapp.R;
import whatsapp.rest.com.br.whatsapp.activity.ConversaActivity;
import whatsapp.rest.com.br.whatsapp.model.Contact;
import whatsapp.rest.com.br.whatsapp.model.Conversation;
import whatsapp.rest.com.br.whatsapp.util.ConverterBase64;

/**
 * Created by LUCAS RODRIGUES on 10/11/2017.
 */

public class ConversasAdapter extends RecyclerView.Adapter<ConversasAdapter.ViewHolder> {

    private List<Conversation> conversations;
    private Context context;

    public ConversasAdapter(Context context, List<Conversation> conversations) {
        this.conversations = conversations;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate(R.layout.item_conversa, parent, false);
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvName.setText( conversations.get(position).getName() );
        holder.tvConversation.setText( conversations.get(position).getMessage() );
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( context, ConversaActivity.class );
                Contact contact = new Contact();
                contact.setId( conversations.get( position).getIdUser() );
                contact.setEmail(ConverterBase64.decode(conversations.get( position).getIdUser()) );
                contact.setName( conversations.get( position).getName() );
                intent.putExtra("contact", contact);
                context.startActivity( intent );
            }
        });
    }

    @Override
    public int getItemCount() {
        return conversations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_conversation)
        TextView tvConversation;
        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind( this, itemView );
        }
    }
}
