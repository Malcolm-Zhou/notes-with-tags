package com.malcolm.controller;

import com.malcolm.bean.Note;
import com.malcolm.bean.Tag;
import com.malcolm.service.NoteService;
import com.malcolm.service.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {
    @Resource
    private NoteService noteService;
    @Resource
    private TagService tagService;

    @RequestMapping("/")
    public String index(Model model) {
        List<Note> notes = noteService.findAll();
        model.addAttribute("notes", notes);
        return "index";
    }

    @RequestMapping("/updateNote")
    public String updateNote(@RequestParam final String id, Model model) {
        Note note = noteService.getById(id);
        model.addAttribute("note", note);
        return "note-details";
    }

    @RequestMapping("/deleteTag")
    @ResponseBody
    public String deleteTag(@RequestParam String noteId, @RequestParam String tagId) {
        noteService.deleteTagInNote(noteId, tagId);
        return "success";
    }

    @RequestMapping("/addNote")
    public String index(HttpServletRequest request) {
        String orderId = request.getParameter("orderId");
        String tagName = request.getParameter("tagName");
        noteService.addTag(orderId, tagName);
        return "redirect:/";
    }

    @RequestMapping("/init")
    public String init() {
        noteService.clearAll();
        tagService.clearAll();

        Tag tag1 = new Tag("tag1");
        Tag tag2 = new Tag("tag2");
        tagService.createTag(tag1);
        tagService.createTag(tag2);
        for (int i = 0; i < 10; i++) {
            Note note = new Note("测试笔记" + i, "测试内容" + i);

//            tag1.getNotes().add(note);
//            tag2.getNotes().add(note);
            note.getTags().addAll(Arrays.asList(tag2, tag1));
            noteService.createNote(note);
        }
        return "redirect:/";
    }


}
