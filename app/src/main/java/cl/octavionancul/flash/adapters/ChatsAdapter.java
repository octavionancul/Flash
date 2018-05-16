package cl.octavionancul.flash.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.siyamed.shapeimageview.BubbleImageView;
import com.squareup.picasso.Picasso;

import cl.octavionancul.flash.R;
import cl.octavionancul.flash.models.Chat;

public class ChatsAdapter extends FirebaseRecyclerAdapter<Chat, ChatsAdapter.ChatHolder> {

    private ChatsListener listener;

    public ChatsAdapter(@NonNull FirebaseRecyclerOptions<Chat> options, ChatsListener listener) {
        super(options);
        this.listener= listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull final ChatHolder holder, int position, @NonNull Chat model) {
        Picasso.get().load(model.getPhoto())
                .centerCrop()
                .fit()
                .into(holder.photo);

        holder.email.setText(model.getReceiver());
        if(model.isNotification()) {
            holder.notification.setVisibility(View.VISIBLE);
        }else{
            holder.notification.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Chat chat =  getItem(holder.getAdapterPosition());
             //   getRef(holder.getAdapterPosition()).getKey();
                 listener.clicked(chat.getKey(),chat.getReceiver());
            }
        });
    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_chat, parent, false);
        return new ChatsAdapter.ChatHolder(view);
    }

    public static class ChatHolder extends RecyclerView.ViewHolder {

        private BubbleImageView photo;
        private TextView email;
        private View notification;

        public ChatHolder(View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.photoBiv);
            email = itemView.findViewById(R.id.emailTv);
            notification = itemView.findViewById(R.id.notificationV);

        }
    }
}
