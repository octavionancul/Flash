package cl.octavionancul.flash.views.login;

import cl.octavionancul.flash.data.CurrentUser;

public class LoginEvaluator {

    public LoginEvaluator(LoginCallback callback) {
        this.callback = callback;
    }

    private LoginCallback callback;

    public void evaluate(){

        if(new CurrentUser().getCurrentUser()!=null){
           callback.logged();
        }else{
           callback.signUp();
        }

    }
}
