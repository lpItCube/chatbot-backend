package com.itcube.chatbot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "node")
@NoArgsConstructor
@Getter
@Setter
public class Node {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "collectionId")
    private Collection collection;
    private String title;
    private String description;
    private Boolean firstNode;
    private Boolean lastNode;
    private String type;
    private String data;

    @OneToMany(mappedBy = "nextNode")
    private List<Option> refOpt;

    @OneToMany(mappedBy = "node", cascade = CascadeType.ALL)
    private List<Option> options = new ArrayList<>();

    public List<Option> getRefOpt() {
        return refOpt;
    }

    public void setRefOpt(List<Option> refs) {
        refOpt = refs;
    }

    public void addOption(Option o) {
        o.setNode(this);
        options.add(o);
    }

    public void addRefOpt(Option o) {
        o.setNextNode(this);
        refOpt.add(o);
    }

    public void removeRefOpt(Option o) {
        o.setNextNode(null);
        refOpt.remove(o);
    }

    public Node(
        String title,
        String description,
        Boolean firstNode,
        Boolean lastNode,
        String type,
        String data,
        List<Option> options
    ) {
        this.title = title;
        this.description = description;
        this.firstNode = firstNode;
        this.lastNode = lastNode;
        this.type = type;
        this.data = data;

        for (Option o : options) {
            addOption(o);
        }
    }

}
