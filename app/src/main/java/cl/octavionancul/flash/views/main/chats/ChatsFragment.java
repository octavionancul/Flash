package cl.octavionancul.flash.views.main.chats;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;

import cl.octavionancul.flash.R;
import cl.octavionancul.flash.adapters.ChatsAdapter;
import cl.octavionancul.flash.adapters.ChatsListener;
import cl.octavionancul.flash.data.CurrentUser;
import cl.octavionancul.flash.data.Nodes;
import cl.octavionancul.flash.models.Chat;
import cl.octavionancul.flash.views.chat.ChatActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends Fragment implements ChatsListener {

    public static final String CHAT_KEY = "cl.octavionancul.flash.views.main.chats.KEY.CHAT_KEY";
    public static final String CHAT_RECEIVER = "cl.octavionancul.flash.views.main.chats.KEY.CHAT_RECEIVER";

    public ChatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.chatsRv);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        FirebaseRecyclerOptions<Chat> options = new FirebaseRecyclerOptions.Builder<Chat>()
                .setQuery( new Nodes().userChat(new CurrentUser().uid()),Chat.class)
                .setLifecycleOwner(getActivity())
                .build();

        //Chatadapter
       ChatsAdapter chatAdapter = new ChatsAdapter(options,this);
       recyclerView.setAdapter(chatAdapter);

    }

    @Override
    public void clicked(String key, String mail) {
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra(CHAT_KEY,key);
        intent.putExtra(CHAT_RECEIVER,mail);
        startActivity(intent);



        //Toast.makeText(getContext(), key+mail, Toast.LENGTH_SHORT).show();
    }
}
