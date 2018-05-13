package cl.octavionancul.flash.views.main.finder;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Node;

import cl.octavionancul.flash.data.CurrentUser;
import cl.octavionancul.flash.data.EmailProcessor;
import cl.octavionancul.flash.data.Nodes;
import cl.octavionancul.flash.data.PhotoPreference;
import cl.octavionancul.flash.models.Chat;
import cl.octavionancul.flash.models.LocalUser;

public class UserValidation {

    private FinderCallback callback;
    private Context context;

    public UserValidation(FinderCallback callback, Context context) {
        this.callback = callback;
        this.context = context;
    }

    public void init(String email){
        if(email.trim().length()>0){
            if(email.contains("@")){
                String currentEmail = new CurrentUser().email();

                if(!email.equals(currentEmail)){
                        findUser(email);
                }else{
                    callback.error("Chat contigo mismo?");
                }
            }
            else{
                callback.error("Error en email");
            }
        }else{
        callback.error("Se necesita email");
        }
    }

    private void findUser(String email){

        new Nodes().user(new EmailProcessor().sanitizeEmail(email)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LocalUser otherUser = dataSnapshot.getValue(LocalUser.class);
                if(otherUser!=null){
                 createChats(otherUser);
                }else{
                callback.notFound();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void createChats(LocalUser otherUser){

        FirebaseUser currentUser = new CurrentUser().getCurrentUser();
        String photo = new PhotoPreference(context).getPhoto();

        String key = new EmailProcessor().keyEmails(otherUser.getEmail());

        Chat currentChat = new Chat();
        currentChat.setKey(key);
        currentChat.setPhoto(otherUser.getPhoto());
        currentChat.setNotification(true);
        currentChat.setReceiver(otherUser.getEmail());

        Chat otherChat = new Chat();
        otherChat.setKey(key);
        otherChat.setPhoto(photo);
        otherChat.setNotification(true);
        otherChat.setReceiver(currentUser.getEmail());

        new Nodes().userChat(currentUser.getUid()).child(key).setValue(currentChat);
        new Nodes().userChat(otherUser.getUid()).child(key).setValue(otherChat);

        callback.success();




    }

}
