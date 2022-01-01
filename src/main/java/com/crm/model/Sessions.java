package com.crm.model;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sessions")
public class Sessions extends GenericInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SEQUENCE_NAME = "sessions_sequence";

    @Id
    //@Column(name = "id", updatable = false, nullable = false)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Column(name = "sessionName")
    private String sessionName;
    //@Column(name = "email")
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Session{" + "id=" + id + ", sessionName=" + sessionName + ", email=" + email + '}';
    }

}
