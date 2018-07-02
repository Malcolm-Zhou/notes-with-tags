package com.malcolm.service.impl;

import com.malcolm.bean.Tag;
import com.malcolm.repository.TagDao;
import com.malcolm.service.TagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service
public class TagServiceImpl implements TagService {
    @Resource
    private TagDao tagDao;

    @Override
    public List<Tag> findAll() {
        return tagDao.findAll();
    }

    @Override
    public void clearAll() {
        tagDao.deleteAll();
    }

    @Override
    public Tag createTag(Tag tag) {
        Tag tagInDb = tagDao.findByNameEquals(tag.getName());
        if (null == tagInDb) {
            return tagDao.save(tag);
        } else {
            return tagInDb;
        }
    }
}