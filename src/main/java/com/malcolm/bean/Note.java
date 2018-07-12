package com.malcolm.bean;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name=" content", columnDefinition="CLOB")
    private String content;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "NoteTagRel", joinColumns = {
            @JoinColumn(name = "NOTE_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
            @JoinColumn(name = "TAG_ID", referencedColumnName = "ID")})
    private List<Tag> tags;

    /**
     * 创建时间
     */
    @CreatedDate
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @LastModifiedDate
    @Column(name = "modify_time")
    private Date modifyTime;

    public Note() {
        tags = new ArrayList<>();
    }

    public Note(String title) {
        this.tags = new ArrayList<>();
        this.title = title;
    }

    public Note(String title, String content) {
        this.tags = new ArrayList<>();
        this.title = title;
        this.content = content;
    }

    public Note(String title, String content, List<Tag> tags) {
        this.title = title;
        this.content = content;
        this.tags = tags;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
