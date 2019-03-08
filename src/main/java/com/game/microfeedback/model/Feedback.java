package com.game.microfeedback.model;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@ApiModel("A unique constraint is added because a user can only leave one feedback per session. His rating and comment will be updated only.")
@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"user_id", "session_id"})})
public class Feedback {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @ApiModelProperty(value = "A feedback can only have one user but a user can have multiple feedback")
    @ManyToOne
    private User user;

    @ApiModelProperty(value = "A feedback can only have one session but a session can have multiple feedback")
    @ManyToOne
    private Session session;

    @ApiModelProperty(value = "Rating can be from 1 to 5")
    @Min(value = 1)
    @Max(value = 5)
    private int rating;

    private String comment;

    public Feedback(){}

    public Feedback(int id,  int rating, String comment) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
    }

    //Getter and setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ",rating=" + rating+
                ",comment="+ comment+
                '}';
    }
}
