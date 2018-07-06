package com.malcolm.service;

import com.malcolm.bean.Note;
import com.malcolm.bean.Tag;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *
 */
public interface NoteService {

    Page<Note> findByTitlePaging(String title, Integer page);

    Note getById(String id);

    Page<Note> findByTagsContaining(String tagId, Integer page);

    void addTag(String noteId, String tagName);

    void deleteTagInNote(String noteId, String tagId);

    void clearAll();

    void update(Note note);

    void createNote(Note note);
}
