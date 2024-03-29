// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.jieyou.adhd.domain;

import com.jieyou.adhd.domain.Record;
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

privileged aspect Record_Roo_Entity {
    
    declare @type: Record: @Entity;
    
    @PersistenceContext
    transient EntityManager Record.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Record.id;
    
    @Version
    @Column(name = "version")
    private Integer Record.version;
    
    public Long Record.getId() {
        return this.id;
    }
    
    public void Record.setId(Long id) {
        this.id = id;
    }
    
    public Integer Record.getVersion() {
        return this.version;
    }
    
    public void Record.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void Record.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Record.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Record attached = Record.findRecord(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Record.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public Record Record.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Record merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager Record.entityManager() {
        EntityManager em = new Record().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Record.countRecords() {
        return entityManager().createQuery("select count(o) from Record o", Long.class).getSingleResult();
    }
    
    public static List<Record> Record.findAllRecords() {
        return entityManager().createQuery("select o from Record o", Record.class).getResultList();
    }
    
    public static Record Record.findRecord(Long id) {
        if (id == null) return null;
        return entityManager().find(Record.class, id);
    }
    
    public static List<Record> Record.findRecordEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from Record o", Record.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
