package com.game.microfeedback.model;

import java.util.Collection;
import javax.persistence.*;

@Entity
public class Session {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    public Session() {}

    public Session(int id) {
        this.id = id;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



}
