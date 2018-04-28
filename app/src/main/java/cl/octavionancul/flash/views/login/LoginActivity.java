package cl.octavionancul.flash.views.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;

import java.util.Arrays;

import cl.octavionancul.flash.BuildConfig;
import cl.octavionancul.flash.R;
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
        new LoginEvaluator(this).evaluate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(RC_SIGN_IN ==   requestCode){
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if(resultCode== RESULT_OK){
               logged();

            }else {
              //  new LoginEvaluator(this).evaluate();
                Log.d("login", String.valueOf(response.getError()));
                if (resultCode==RESULT_CANCELED){
                    Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show();
                }

                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    Toast.makeText(this, "Presionado atras", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(this, "Sin internet", Toast.LENGTH_SHORT).show();
                    return;
                }

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
