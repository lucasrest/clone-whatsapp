package whatsapp.rest.com.br.whatsapp.util;

import android.telephony.SmsManager;

/**
 * Created by LUCAS RODRIGUES on 04/11/2017.
 */

public class Sms {

    public static boolean sendSms( String fone, String message){
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage( fone, null, message, null, null);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
