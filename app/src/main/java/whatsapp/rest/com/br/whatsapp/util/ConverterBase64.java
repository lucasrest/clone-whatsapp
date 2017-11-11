package whatsapp.rest.com.br.whatsapp.util;

import android.util.Base64;

/**
 * Created by LUCAS RODRIGUES on 08/11/2017.
 */

public class ConverterBase64 {

    public static String encode( String text ){
        return Base64.encodeToString( text.getBytes(), Base64.DEFAULT ).replaceAll("(\\n|\\r)", "");
    }

    public static String decode( String text ){
        return new String( Base64.decode( text, Base64.DEFAULT ) );
    }
}
