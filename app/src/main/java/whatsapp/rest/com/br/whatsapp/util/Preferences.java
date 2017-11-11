package whatsapp.rest.com.br.whatsapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by LUCAS RODRIGUES on 04/11/2017.
 */

public class Preferences {

    private final Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private final String NOME_ARQUIVO = "preferences";
    private final String KEY_IDENTIFICATOR = "identificatorUserLogged";
    private final String KEY_NAME = "nameUserLogged";

    public Preferences(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(NOME_ARQUIVO, Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

    public void saveData(String identificatorUserLogged, String nameUserLogged){
        editor.putString(KEY_IDENTIFICATOR, identificatorUserLogged);
        editor.putString(KEY_NAME, nameUserLogged);
        editor.commit();
    }

    public HashMap<String, String> getData(){
        HashMap<String, String> data = new HashMap<>();
        data.put(KEY_IDENTIFICATOR, sharedPreferences.getString(KEY_IDENTIFICATOR, null));
        data.put(KEY_NAME, sharedPreferences.getString(KEY_NAME, null));
        return data;
    }


}
