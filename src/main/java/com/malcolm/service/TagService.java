package com.malcolm.service;

import com.malcolm.bean.Note;
import com.malcolm.bean.Tag;

import java.util.List;

/**
 *
 */
public interface TagService {
    List<Tag> findAll();

    List<Note> findNotesByTagIdPaging(String tagId, Integer page);

    Tag findByName(String name);

    Tag getById(String id);

    void clearAll();

    Tag createTag(Tag tag);

    void save(Tag tag);
}
