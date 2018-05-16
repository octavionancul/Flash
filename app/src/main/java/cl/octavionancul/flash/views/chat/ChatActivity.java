package cl.octavionancul.flash.views.chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cl.octavionancul.flash.R;
import cl.octavionancul.flash.views.main.chats.ChatsFragment;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        String key = getIntent().getStringExtra(ChatsFragment.CHAT_KEY);
        String mail = getIntent().getStringExtra(ChatsFragment.CHAT_RECEIVER);

        getSupportActionBar().setTitle(mail);

    }
}
