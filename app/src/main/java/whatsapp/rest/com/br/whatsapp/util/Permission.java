package whatsapp.rest.com.br.whatsapp.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LUCAS RODRIGUES on 04/11/2017.
 */

public class Permission {

    public static boolean validationPermissions(int requestCode, Activity activity, String[] permissions){

        if(Build.VERSION.SDK_INT >= 23){
            List<String> dontPermissions = new ArrayList<>();
            for (String permission: permissions){
                if( ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED ){
                    dontPermissions.add( permission );
                }
            }
            if( dontPermissions.isEmpty() )
                return true;

            String[] solicitations = new String[dontPermissions.size()];
            dontPermissions.toArray( solicitations );

            ActivityCompat.requestPermissions( activity, solicitations, requestCode);
        }

        return true;
    }

}
