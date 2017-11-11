package whatsapp.rest.com.br.whatsapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import whatsapp.rest.com.br.whatsapp.R;
import whatsapp.rest.com.br.whatsapp.model.Message;
import whatsapp.rest.com.br.whatsapp.util.Preferences;

/**
 * Created by LUCAS RODRIGUES on 10/11/2017.
 */

public class MensagensAdapter extends RecyclerView.Adapter<MensagensAdapter.ViewHolder> {

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private Context context;
    private List<Message> messages;

    public MensagensAdapter(Context context, List<Message> messages){
        this.messages = messages;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate(R.layout.mensagem_esquerda, parent, false);

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mensagem_esquerda, parent, false);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mensagem_direita, parent, false);
        }
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tvMessage.setText( messages.get( position ).getMessage() );

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_message) TextView tvMessage;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind( this, itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        String userLogged = new Preferences( context ).getData().get("identificatorUserLogged");
        if( !userLogged.equals( messages.get( position ).getIdUser() ))
            return VIEW_TYPE_MESSAGE_SENT;
        else
            return VIEW_TYPE_MESSAGE_RECEIVED;
    }
}
