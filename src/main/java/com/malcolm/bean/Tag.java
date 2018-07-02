package com.malcolm.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 */
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Note> notes;

    public Tag() {
        notes = new ArrayList<>();
    }

    public Tag(String name) {
        notes = new ArrayList<>();
        this.name = name;
    }

    public Tag(String name, List<Note> notes) {
        this.notes = new ArrayList<>();
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Note> getNotes() {
        return notes;
    }

    @Override
    public String toString() {
        return name;
    }

    @JsonBackReference
    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}