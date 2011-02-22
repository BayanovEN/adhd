// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.jieyou.adhd.domain;

import com.jieyou.adhd.domain.Question;
import java.lang.Integer;
import java.lang.Long;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Question_Roo_Entity {
    
    declare @type: Question: @Entity;
    
    @PersistenceContext
    transient EntityManager Question.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Question.id;
    
    @Version
    @Column(name = "version")
    private Integer Question.version;
    
    public Long Question.getId() {
        return this.id;
    }
    
    public void Question.setId(Long id) {
        this.id = id;
    }
    
    public Integer Question.getVersion() {
        return this.version;
    }
    
    public void Question.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void Question.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Question.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Question attached = Question.findQuestion(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Question.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public Question Question.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Question merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager Question.entityManager() {
        EntityManager em = new Question().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Question.countQuestions() {
        return entityManager().createQuery("select count(o) from Question o", Long.class).getSingleResult();
    }
    
    public static List<Question> Question.findAllQuestions() {
        return entityManager().createQuery("select o from Question o", Question.class).getResultList();
    }
    
    public static Question Question.findQuestion(Long id) {
        if (id == null) return null;
        return entityManager().find(Question.class, id);
    }
    
    public static List<Question> Question.findQuestionEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from Question o", Question.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
