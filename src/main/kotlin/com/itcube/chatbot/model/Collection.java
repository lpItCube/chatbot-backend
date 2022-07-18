package com.itcube.chatbot.model;


import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "collection")
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String description;
    private Boolean isReady;

    @OneToMany(mappedBy = "collection")
    private List<Node> node = new ArrayList<>();

    public void addNode(Node n) {
        n.setCollection(this);
        node.add(n);
    }

    public Collection(){}

    public Collection(String name, String description, Boolean isReady) {
        this.name = name;
        this.description = description;
        this.isReady = isReady;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsReady() {
        return isReady;
    }

    public void setIsReady(Boolean ready) {
        isReady = ready;
    }

    public Integer getId() {
        return id;
    }
}
