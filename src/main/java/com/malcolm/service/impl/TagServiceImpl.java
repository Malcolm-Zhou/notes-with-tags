package com.malcolm.service.impl;

import com.malcolm.bean.Note;
import com.malcolm.bean.Tag;
import com.malcolm.repository.TagDao;
import com.malcolm.service.TagService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class TagServiceImpl implements TagService {
    @Resource
    private TagDao tagDao;

    @Override
    public List<Tag> findAll() {
        Sort sort = new Sort(Sort.Direction.ASC,"name");
        return tagDao.findAll(sort);
    }


    @Override
    public Tag findByName(String name) {
        return tagDao.findByNameEquals(name);
    }

    @Override
    public Tag getById(String id) {
        return tagDao.getOne(Integer.valueOf(id));
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

    @Override
    public void save(Tag tag) {
        tagDao.save(tag);
    }
}
