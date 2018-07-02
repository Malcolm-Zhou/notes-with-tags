package com.malcolm.repository;

import com.malcolm.bean.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 */
public interface NoteDao extends JpaRepository<Note, Integer> {
    List<Note> findByTitleContaining(String title);
}
