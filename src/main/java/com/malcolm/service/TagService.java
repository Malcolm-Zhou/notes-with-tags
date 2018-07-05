package com.malcolm.service;

import com.malcolm.bean.Tag;

import java.util.List;

/**
 *
 */
public interface TagService {
    List<Tag> findAll();

    Tag findByName(String name);

    Tag getById(String id);

    void clearAll();

    Tag createTag(Tag tag);

    void save(Tag tag);
}
