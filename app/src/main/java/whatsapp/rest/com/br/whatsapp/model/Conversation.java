package whatsapp.rest.com.br.whatsapp.model;

/**
 * Created by LUCAS RODRIGUES on 10/11/2017.
 */

public class Conversation {

    private String idUser;
    private String name;
    private String message;

    public Conversation() {
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
