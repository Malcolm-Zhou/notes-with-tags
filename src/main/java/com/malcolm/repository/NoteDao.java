package com.malcolm.repository;

import com.malcolm.bean.Note;
import com.malcolm.bean.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 */
public interface NoteDao extends JpaRepository<Note, Integer> {

    Page<Note> findByTitleContaining(String title, Pageable pageable);

    Page<Note> findByTagsContaining(Tag tag, Pageable pageable);

    List<Note> findByTagsContaining(Tag tag);

}
