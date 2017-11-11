package whatsapp.rest.com.br.whatsapp.model;

/**
 * Created by LUCAS RODRIGUES on 10/11/2017.
 */

public class Message {

    private String idUser;
    private String message;

    public Message() {
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
