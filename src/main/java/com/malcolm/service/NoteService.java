package com.malcolm.service;

import com.malcolm.bean.Note;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *
 */
public interface NoteService {

    Integer addNote(String title);

    void deleteNote(Integer id);

    Page<Note> findByTitlePaging(String title, Integer page);

    Note getById(String id);

    Page<Note> findByTagContaining(String tagId, Integer page);

    List<Note> findByTags(List<String> tagIds);

    void addTag(String noteId, String tagName);

    void deleteTagInNote(String noteId, String tagId);

    void clearAll();

    void update(Note note);

    void createNote(Note note);
}
