package whatsapp.rest.com.br.whatsapp.model;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import whatsapp.rest.com.br.whatsapp.config.FirebaseConfig;

/**
 * Created by LUCAS RODRIGUES on 04/11/2017.
 */

public class User {

    private String id;
    private String name;
    private String email;
    private String password;

    public User() {
    }

    public void save(){
        DatabaseReference reference = FirebaseConfig.getFireBase();
        reference.child("users").child( getId() ).setValue( this );
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
