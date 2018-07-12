package com.malcolm.service.impl;

import com.malcolm.bean.Note;
import com.malcolm.bean.Tag;
import com.malcolm.repository.NoteDao;
import com.malcolm.service.NoteService;
import com.malcolm.service.TagService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
public class NoteServiceImpl implements NoteService {
    @Resource
    private NoteDao noteDao;
    @Resource
    private TagService tagService;

    @Resource
    private Environment env;

    @Override
    public Integer addNote(String title) {
        Note note = new Note();
        note.setTitle(title);
        noteDao.save(note);
        System.out.println(note.getId());
        return note.getId();
    }

    @Override
    public void deleteNote(Integer id) {
        noteDao.deleteById(id);
    }

    @Override
    public Page<Note> findByTitlePaging(String title, Integer page) {
        String size = env.getProperty("paging.size");
        Pageable pageable = PageRequest.of(page, Integer.valueOf(size), Sort.Direction.ASC, "id");
        return noteDao.findByTitleContaining(title, pageable);
    }

    @Override
    public Note getById(String id) {
        return noteDao.getOne(Integer.valueOf(id));
    }

    @Override
    public Page<Note> findByTagContaining(String tagId, Integer page) {
        Tag tag = tagService.getById(tagId);
        String size = env.getProperty("paging.size");
        Pageable pageable = PageRequest.of(page, Integer.valueOf(size), Sort.Direction.ASC, "id");
        return noteDao.findByTagsContaining(tag, pageable);
    }

    @Override
    public List<Note> findByTags(List<String> tagIds) {
        List<Tag> tags = tagIds.stream().map(tagId -> tagService.getById(tagId)).collect(Collectors.toList());

        List<List<Note>> lists = tags.stream().map(tag -> noteDao.findByTagsContaining(tag)).collect(Collectors.toList());
        List<Note> result = lists.get(0);
        lists.forEach(result::retainAll);
        return result;
    }

    public void addTag(String noteId, String tagName) {
        if (StringUtils.isBlank(tagName)) {
            return;
        }

        Tag tag = tagService.findByName(tagName);
        if (null == tag) {
            tag = tagService.createTag(new Tag(tagName));
            tagService.save(tag);
        }
        Note note = getById(noteId);
        if (null != note) {
            List<Tag> tags = note.getTags();
            if (tags.contains(tag)) {
                return;
            }
            tags.add(tag);
            note.setTags(tags.stream().sorted(Comparator.comparing(Tag::getName)).collect(Collectors.toList()));
            tag.getNotes().add(note);
            noteDao.save(note);
        } else {
            System.err.println("noteId查不到记录: " + noteId);
        }
    }

    @Override
    public void deleteTagInNote(String noteId, String tagId) {
        Note note = getById(noteId);
        Tag tag = tagService.getById(tagId);
        note.getTags().remove(tag);
        noteDao.save(note);
    }

    @Override
    public void clearAll() {
        noteDao.deleteAll();
    }

    @Override
    public void update(Note note) {
        Note searchedNote = getById(note.getId().toString());
        searchedNote.setTitle(note.getTitle());
        searchedNote.setContent(note.getContent());
        noteDao.save(searchedNote);
    }


    @Override
    public void createNote(Note note) {
        noteDao.save(note);
    }


}
