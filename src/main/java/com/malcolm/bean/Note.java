package com.malcolm.bean;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 */
@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String content;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "NoteTagRel", joinColumns = {
            @JoinColumn(name = "NOTE_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
            @JoinColumn(name = "TAG_ID", referencedColumnName = "ID")})
    private List<Tag> tags;

    public Note() {
        tags = new ArrayList<>();
    }

    public Note(String title) {
        this.tags = new ArrayList<>();
        this.title = title;
    }

    public Note(String title, String content) {
        this.tags = new ArrayList<>();
        this.title = title;
        this.content = content;
    }

    public Note(String title, String content, List<Tag> tags) {
        this.title = title;
        this.content = content;
        this.tags = tags;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
