package com.malcolm.service.impl;

import com.malcolm.bean.Note;
import com.malcolm.bean.Tag;
import com.malcolm.repository.NoteDao;
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

    public void addTag(String noteId, String tagName) {
        Tag tag = new Tag();
        tag.setName(tagName);
        tag = tagService.createTag(tag);
        Optional<Note> optionalNote = noteDao.findById(Integer.valueOf(noteId));
        if(optionalNote.isPresent()){
            Note note = optionalNote.get();
            List<Tag> tags = note.getTags();
            tags.add(tag);
            note.setTags(tags.stream().sorted(Comparator.comparing(Tag::getName)).collect(Collectors.toList()));
            tag.getNotes().add(note);
            noteDao.save(note);
        } else {
            System.err.println("noteId查不到记录: " + noteId);
        }
    }

    @Override
    public void clearAll() {
        noteDao.deleteAll();
    }

    @Override
    public void createNote(Note note) {
        noteDao.save(note);
    }


}
