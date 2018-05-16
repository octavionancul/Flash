package cl.octavionancul.flash.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cl.octavionancul.flash.models.LocalUser;

public class EmailProcessor {
    public String sanitizeEmail(String email){
        return email.replace("@","AT").replace(".","DOT");
    }

    public String keyEmails(String otherEmail){
        String currentEmail = new CurrentUser().email();

        List<String> emails = new ArrayList<>();
        emails.add(sanitizeEmail(currentEmail));
        emails.add(sanitizeEmail(otherEmail));
        Collections.sort(emails);

/* //En el caso de comparar dos objectos
        List<LocalUser> localUsers = new ArrayList<>();
        Collections.sort(localUsers, new Comparator<LocalUser>() {
            @Override
            public int compare(LocalUser o1, LocalUser o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });*/

        return emails.get(0)+" - "+emails.get(1);
    }
}
