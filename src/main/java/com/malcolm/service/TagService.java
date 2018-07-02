package com.malcolm.service;

import com.malcolm.bean.Tag;

import java.util.List;

/**
 *
 */
public interface TagService {
    List<Tag> findAll();

    void clearAll();

    Tag createTag(Tag tag);
}
