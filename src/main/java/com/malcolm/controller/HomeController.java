package com.malcolm.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.malcolm.bean.Note;
import com.malcolm.bean.Tag;
import com.malcolm.service.NoteService;
import com.malcolm.service.TagService;
import com.malcolm.util.MarkdownUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
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
        model.addAttribute("title", title);
        model.addAttribute("searchByTags", false);
        return "index";
    }

    @RequestMapping("/searchByTag")
    public String searchByTag(@RequestParam final String id, @RequestParam(required = false, defaultValue = "0") final Integer page, Model model) {
        Page<Note> notePage = noteService.findByTagContaining(id, page);
        List<Note> notes = notePage.getContent();
        int totalPages = notePage.getTotalPages();
        model.addAttribute("notes", notes);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("navUrlPrefix", "/searchByTag?id=" + id + "&page=");
        model.addAttribute("searchByTags", false);
        return "index";
    }

    @RequestMapping("/searchByTags")
    public String searchByTags(@RequestParam("selectedTags") String selectedTags, Model model) {


        Gson gson = new Gson();
        List<Tag> tags = gson.fromJson(selectedTags, new TypeToken<List<Tag>>() {
        }.getType());

        if (CollectionUtils.isEmpty(tags)) {
            return "redirect:/";
        }
        List<String> ids = tags.stream().map(tag -> tag.getId().toString()).collect(Collectors.toList());
        List<Note> notes = noteService.findByTags(ids);

        model.addAttribute("notes", notes);
        model.addAttribute("selectedTags", tags);
        model.addAttribute("searchByTags", true);
        return "index";
    }

    @RequestMapping("/updateNotePage")
    public String updateNote(@RequestParam final String id, Model model) {
        Note note = noteService.getById(id);
        String mdHTML = MarkdownUtil.flexmark(note.getContent());
        model.addAttribute("note", note);
        model.addAttribute("mdHTML", mdHTML);
        return "note-details";
    }

    @RequestMapping("/refreshMD")
    @ResponseBody
    public String ajaxRefreshMarkDown(@RequestParam final String content) {
        String mdHTML = MarkdownUtil.flexmark(content);
        return mdHTML;
    }


    @RequestMapping("/addNote")
    public String addNote(@RequestParam final String title) {
        Integer id = noteService.addNote(title);
        return "redirect:/updateNotePage?id=" + id;
    }

    @RequestMapping("/deleteNote")
    @ResponseBody
    public String deleteNote(@RequestParam(defaultValue = "-1") final String id) {
        if (!id.equals("-1")) {

            Note note = noteService.getById(id);
            note.setTags(null);
            noteService.deleteNote(Integer.valueOf(id));
        }
        return "success";
    }

    @RequestMapping("/updateNote")
    public String updateNote(HttpServletRequest request) {
        String noteId = request.getParameter("noteId");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        if (StringUtils.isBlank(title)) {
            return "redirect:/deleteNote?id=" + noteId;
        }
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

    @RequestMapping("/addTag")
    @ResponseBody
    public String addTag(@RequestParam String noteId, @RequestParam String name) {
        noteService.addTag(noteId, name);
        return "success";
    }

    @RequestMapping("/init")
    public String init() {
        noteService.clearAll();
        tagService.clearAll();

        String defaultContent = "Markdown\n" +
                "\n" +
                "# 这是一级标题\n" +
                "## 这是二级标题\n" +
                "### 这是三级标题\n" +
                "#### 这是四级标题\n" +
                "##### 这是五级标题\n" +
                "###### 这是六级标题\n" +
                "\n" +
                "**这是加粗的文字**\n" +
                "*这是倾斜的文字*`\n" +
                "***这是斜体加粗的文字***\n" +
                "~~这是加删除线的文字~~\n" +
                "\n" +
                ">这是引用的内容\n" +
                ">>这是引用的内容\n" +
                ">>>>>>>>>>这是引用的内容\n" +
                "\n" +
                "分割线\n" +
                "---\n" +
                "----\n" +
                "***\n" +
                "*****\n" +
                "\n" +
                "![图片alt](图片地址 ''图片title'')\n" +
                "\n" +
                "图片alt就是显示在图片下面的文字，相当于对图片内容的解释。\n" +
                "图片title是图片的标题，当鼠标移到图片上时显示的内容。title可加可不加\n" +
                "\n" +
                "![blockchain](https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=702257389,1274025419&fm=27&gp=0.jpg \"区块链\")\n" +
                "\n" +
                "\n" +
                "[超链接名](超链接地址 \"超链接title\")\n" +
                "title可加可不加\n" +
                "\n" +
                "[简书](http://jianshu.com)\n" +
                "[百度](http://baidu.com)\n" +
                "\n" +
                "- 列表内容\n" +
                "+ 列表内容\n" +
                "* 列表内容\n" +
                "\n" +
                "注意：- + * 跟内容之间都要有一个空格\n" +
                "\n" +
                "\n" +
                "1.列表内容\n" +
                "2.列表内容\n" +
                "3.列表内容\n" +
                "\n" +
                "注意：序号跟内容之间要有空格\n" +
                "\n" +
                "\n" +
                "表头|表头|表头\n" +
                "---|:--:|---:\n" +
                "内容|内容|内容\n" +
                "内容|内容|内容\n" +
                "\n" +
                "第二行分割表头和内容。\n" +
                "- 有一个就行，为了对齐，多加了几个\n" +
                "文字默认居左\n" +
                "-两边加：表示文字居中\n" +
                "-右边加：表示文字居右\n" +
                "注：原生的语法两边都要用 | 包起来。此处省略\n" +
                "\n" +
                "\n" +
                "单行代码    `代码内容`\n" +
                "\n" +
                "多行代码    \n" +
                "```\n" +
                "代码内容\n" +
                " ```" +
                "\n" +
                "\n" +
                "\n" +
                "```flow\n" +
                "st=>start: 开始\n" +
                "op=>operation: My Operation\n" +
                "cond=>condition: Yes or No?\n" +
                "e=>end\n" +
                "st->op->cond\n" +
                "cond(yes)->e\n" +
                "cond(no)->op\n" +
                "```";

        Tag tag1 = new Tag("tag1");
        Tag tag2 = new Tag("tag2");
        tagService.createTag(tag1);
        tagService.createTag(tag2);
        for (int i = 0; i < 25; i++) {
            Note note = new Note("测试笔记" + i, defaultContent);

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
