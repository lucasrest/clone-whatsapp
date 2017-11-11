package whatsapp.rest.com.br.whatsapp.model;

import java.io.Serializable;

import whatsapp.rest.com.br.whatsapp.util.ConverterBase64;

/**
 * Created by LUCAS RODRIGUES on 09/11/2017.
 */

public class Contact implements Serializable{

    private String id;
    private String email;
    private String name;

    public Contact(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEncodedEmail(){
        return ConverterBase64.encode( email );
    }
}
