package com.itcube.chatbot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;

import javax.persistence.*;

@Entity
@Table(name = "opttion")
@Getter
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "next_node_id")
    @JsonIgnore
    private Node nextNode;

    @ManyToOne
    @JoinColumn(name = "node_id")
    @JsonIgnore
    private Node node;

    public void addNode(Node n) {
        setNode(n);
    }

    public Option() {}

    public Option(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public Node getNode() {
        return node;
    }

    public Node getNextNode() {
        return nextNode;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public void setNextNode(Node node ) {
        this.nextNode = node;
    }

}
