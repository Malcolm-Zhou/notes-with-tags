package com.malcolm.repository;

import com.malcolm.bean.Note;
import com.malcolm.bean.Tag;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
/**
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class NoteDaoTest {
    @Autowired
    private NoteDao noteDao;

    @Autowired
    private TagDao tagDao;

    @Before
    public void init() {
        Tag tag1 = new Tag("Tag1");
        Tag tag2 = new Tag("Tag2");
        Tag tag3 = new Tag("Tag3");

        Note note1 = new Note("概率论", "概率论content");
        List<Tag> tags1 = new ArrayList<>(Arrays.asList(tag1, tag2));
        note1.setTags(tags1);

        Note note2 = new Note("线性代数", "线性代数content");
        List<Tag> tags2 = new ArrayList<>(Arrays.asList(tag2, tag3));
        note2.setTags(tags2);

        noteDao.save(note1);
        noteDao.save(note2);
    }

    @After
    public void deleteAll() {
        // 删除所有书籍，级联删除关联的作者，但是没有与书籍关联的作者不会被删掉
        noteDao.deleteAll();

        // 删除所有作者，只能删除没有与书籍关联的作者，与书籍有关联的作者无法被删除
        tagDao.deleteAll();
    }

    @Test
    public void findAll() {


        List<Note> notes = noteDao.findAll();
        System.out.println(notes);

        List<Tag> tags = tagDao.findAll();
        System.out.println(tags);

    }

}
