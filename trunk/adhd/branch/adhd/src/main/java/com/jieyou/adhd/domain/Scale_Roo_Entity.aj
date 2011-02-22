// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.jieyou.adhd.domain;

import com.jieyou.adhd.domain.Scale;
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

privileged aspect Scale_Roo_Entity {
    
    declare @type: Scale: @Entity;
    
    @PersistenceContext
    transient EntityManager Scale.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Scale.id;
    
    @Version
    @Column(name = "version")
    private Integer Scale.version;
    
    public Long Scale.getId() {
        return this.id;
    }
    
    public void Scale.setId(Long id) {
        this.id = id;
    }
    
    public Integer Scale.getVersion() {
        return this.version;
    }
    
    public void Scale.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void Scale.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Scale.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Scale attached = Scale.findScale(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Scale.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public Scale Scale.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Scale merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager Scale.entityManager() {
        EntityManager em = new Scale().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Scale.countScales() {
        return entityManager().createQuery("select count(o) from Scale o", Long.class).getSingleResult();
    }
    
    public static List<Scale> Scale.findAllScales() {
        return entityManager().createQuery("select o from Scale o", Scale.class).getResultList();
    }
    
    public static Scale Scale.findScale(Long id) {
        if (id == null) return null;
        return entityManager().find(Scale.class, id);
    }
    
    public static List<Scale> Scale.findScaleEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from Scale o", Scale.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}