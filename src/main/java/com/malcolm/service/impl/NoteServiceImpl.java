package com.malcolm.service.impl;

import com.malcolm.bean.Note;
import com.malcolm.bean.Tag;
import com.malcolm.repository.NoteDao;
import com.malcolm.repository.TagDao;
import com.malcolm.service.NoteService;
import com.malcolm.service.TagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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

    public List<Note> findAll() {
        return noteDao.findAll();
    }

    @Override
    public Note getById(String id) {
        return noteDao.getOne(Integer.valueOf(id));
    }

    public void addTag(String noteId, String tagName) {
        Tag tag = tagService.findByName(tagName);
        if(null == tag){
            tag = tagService.createTag(new Tag(tagName));
            tagService.save(tag);
        }
        Note note = getById(noteId);
        if(null != note){
            List<Tag> tags = note.getTags();
            if(tags.contains(tag))
            {
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
