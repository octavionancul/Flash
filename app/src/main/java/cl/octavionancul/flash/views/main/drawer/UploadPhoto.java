package cl.octavionancul.flash.views.main.drawer;

import android.content.Context;
import android.net.Uri;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import cl.octavionancul.flash.data.CurrentUser;
import cl.octavionancul.flash.data.Nodes;
import cl.octavionancul.flash.data.PhotoPreference;
import cl.octavionancul.flash.models.LocalUser;

public class UploadPhoto {

    private Context context;

    public UploadPhoto(Context context) {
        this.context = context;
    }

    public void toFirebase(String path){

        final CurrentUser currentUser = new CurrentUser();

        String folder = currentUser.sanitizeEmail(currentUser.email()+"/");
        String photoName = "avatar.jpg";
        String baseUrl="gs://flash-bde01.appspot.com/avatars/";
        String refUrl=baseUrl+folder+photoName;

        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(refUrl);
        storageReference.putFile(Uri.parse(path)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                String[] fullUrl = taskSnapshot.getDownloadUrl().toString().split("&token");
                String url = fullUrl[0];

              new PhotoPreference(context).photoSave(url);

                LocalUser user = new LocalUser();
                user.setEmail(currentUser.email());
                user.setName(currentUser.getCurrentUser().getDisplayName());
                user.setPhoto(url);
                user.setUid(currentUser.uid());
                String key = currentUser.sanitizeEmail(currentUser.email());
                //new Nodes().users().child(key).setValue(user);
               // FirebaseDatabase.getInstance().getReference().child("users").child(key).setValue(user);
                new Nodes().user(key).setValue(user);


            }
        });


    }
}
