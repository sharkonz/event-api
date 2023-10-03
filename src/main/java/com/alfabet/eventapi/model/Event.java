package com.alfabet.eventapi.model;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String location;
    private LocalDateTime date;
    private LocalDateTime creationTime;
    private int popularity;

    @ManyToMany
    @JoinTable(
        name = "event_tag",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "event_subscriber",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "subscriber_id")
    )
    private Set<Subscriber> subscribers = new HashSet<>();

}
