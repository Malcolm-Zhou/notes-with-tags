package com.malcolm.service;

import com.malcolm.bean.Note;
import com.malcolm.bean.Tag;

import java.util.List;

/**
 *
 */
public interface NoteService {
    List<Note> findAll();

    void addTag(String noteId, String tagName);

    void clearAll();

    void createNote(Note note);
}
