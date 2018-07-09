package com.malcolm.controller;

import com.malcolm.bean.Note;
import com.malcolm.bean.Tag;
import com.malcolm.service.NoteService;
import com.malcolm.service.TagService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    @Resource
    private NoteService noteService;
    @Resource
    private TagService tagService;

    @RequestMapping("/")
    public String index() {
        return "redirect:/searchByTitle?title=&page=0";
    }

    @RequestMapping("/searchByTitle")
    public String searchByTitle(@RequestParam(required = false, defaultValue = "") final String title, @RequestParam(required = false, defaultValue = "0") final Integer page, Model model) {
        Page<Note> notePage = noteService.findByTitlePaging(title, page);
        List<Note> notes = notePage.getContent();
        int totalPages = notePage.getTotalPages();
        model.addAttribute("notes", notes);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("navUrlPrefix", "/searchByTitle?title=" + title + "&page=");
        return "index";
    }

    @RequestMapping("/searchByTag")
    public String searchByTag(@RequestParam final String id, @RequestParam(required = false, defaultValue = "0") final Integer page, Model model) {
        Page<Note> notePage = noteService.findByTagsContaining(id, page);
        List<Note> notes = notePage.getContent();
        int totalPages = notePage.getTotalPages();
        model.addAttribute("notes", notes);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("navUrlPrefix", "/searchByTag?id=" + id + "&page=");
        return "index";
    }

    @RequestMapping("/updateNotePage")
    public String updateNote(@RequestParam final String id, Model model) {
        Note note = noteService.getById(id);
        model.addAttribute("note", note);
        return "note-details";
    }

    @RequestMapping("/updateNote")
    public String updateNote(HttpServletRequest request) {
        String noteId = request.getParameter("noteId");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        Note note = new Note();
        note.setId(Integer.valueOf(noteId));
        note.setTitle(title);
        note.setContent(content);
        noteService.update(note);
        return "redirect:/updateNotePage?id=" + noteId;
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
        for (int i = 0; i < 25; i++) {
            Note note = new Note("测试笔记" + i, "测试内容" + i);

//            tag1.getNotes().add(note);
//            tag2.getNotes().add(note);
            note.getTags().addAll(Arrays.asList(tag2, tag1));
            noteService.createNote(note);
        }
        for (int i = 0; i < 50; i++) {
            Tag testTag = new Tag("testTag" + i);
            tagService.createTag(testTag);
        }
        return "redirect:/";
    }

    @RequestMapping("/allTags")
    public String allTags(Model model) {
        List<Tag> allTags = tagService.findAll();
        model.addAttribute("tags", allTags);
        return "tag-list";
    }


}
