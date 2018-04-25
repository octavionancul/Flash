package cl.octavionancul.flash.views.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;

import java.util.Arrays;

import cl.octavionancul.flash.BuildConfig;
import cl.octavionancul.flash.R;
import cl.octavionancul.flash.data.CurrentUser;
import cl.octavionancul.flash.views.main.MainActivity;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LoginActivity extends AppCompatActivity implements LoginCallback {
    // Choose an arbitrary request code value
    private static final int RC_SIGN_IN = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        new LoginEvaluator(this).loginEvaluator();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(RC_SIGN_IN == requestCode){
            if(resultCode== RESULT_OK){
                logged();
            }
        }
    }

    @Override
    public void logged() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void signUp() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(
                                Arrays.asList(
                                        new AuthUI.IdpConfig.EmailBuilder().build(),
                                        new AuthUI.IdpConfig.GoogleBuilder().build(),
                                        new AuthUI.IdpConfig.FacebookBuilder().build(),
                                        new AuthUI.IdpConfig.TwitterBuilder().build()/*
                                        new AuthUI.IdpConfig.PhoneBuilder().build(),
                                  */)
                        ).setIsSmartLockEnabled(!BuildConfig.DEBUG /* credentials */, true /* hints */)
                        .setTheme(R.style.LoginTheme)
                        .setLogo(R.mipmap.logo)
                        .build(),
                RC_SIGN_IN);
    }
}
