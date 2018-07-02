package com.malcolm.repository;

import com.malcolm.bean.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 */
public interface TagDao extends JpaRepository<Tag, Integer> {
    Tag findByNameEquals(String name);
    List<Tag> findByNameContaining(String name);
}
