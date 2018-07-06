package com.malcolm.service;

import com.malcolm.bean.Note;
import com.malcolm.bean.Tag;

import java.util.List;

/**
 *
 */
public interface NoteService {
    List<Note> findAll();

    List<Note> findByTitle(String title);

    Note getById(String id);

    void addTag(String noteId, String tagName);

    void deleteTagInNote(String noteId, String tagId);

    void clearAll();

    void update(Note note);

    void createNote(Note note);
}
