package whatsapp.rest.com.br.whatsapp.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by LUCAS RODRIGUES on 04/11/2017.
 */

public final class FirebaseConfig {

    private static DatabaseReference databaseReference;
    private static FirebaseAuth auth;

    public static DatabaseReference getFireBase(){

        if(databaseReference == null){
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }

        return databaseReference;

    }

    public static FirebaseAuth getAuth(){

        if(auth == null){
            auth = FirebaseAuth.getInstance();
        }

        return auth;
    }

}
