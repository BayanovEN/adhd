package com.jieyou.adhd.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import com.jieyou.adhd.reference.ScaleType;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooEntity
public class Scale {

    @NotNull
    @Size(min = 3, max = 30)
    private String scaleName;

    @NotNull
    @Enumerated
    private ScaleType scaleType;

    private String description;

    @NotNull
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "scale")
    @OrderBy("questionNo")
    private Set<Question> questions = new HashSet<Question>();

    @NotNull
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "scale")
    @OrderBy("id")
    private Set<Answer> answers = new HashSet<Answer>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "scale")
    private Set<Record> records = new HashSet<Record>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "scale")
    private Set<Conclusion> conclusions = new HashSet<Conclusion>();

	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ScaleName: ").append(getScaleName()).append(", ");
        sb.append("ScaleType: ").append(getScaleType()).append(", ");
        sb.append("Description: ").append(getDescription()).append(", ");
        sb.append("Questions: ").append(getQuestions() == null ? "null" : getQuestions().size()).append(", ");
        sb.append("Answers: ").append(getAnswers() == null ? "null" : getAnswers().size()).append(", ");
        sb.append("Records: ").append(getRecords() == null ? "null" : getRecords().size()).append(", ");
        sb.append("Conclusions: ").append(getConclusions() == null ? "null" : getConclusions().size());
        return sb.toString();
    }

	public String getScaleName() {
        return this.scaleName;
    }

	public void setScaleName(String scaleName) {
        this.scaleName = scaleName;
    }

	public ScaleType getScaleType() {
        return this.scaleType;
    }

	public void setScaleType(ScaleType scaleType) {
        this.scaleType = scaleType;
    }

	public String getDescription() {
        return this.description;
    }

	public void setDescription(String description) {
        this.description = description;
    }

	public Set<Question> getQuestions() {
        return this.questions;
    }

	public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

	public Set<Answer> getAnswers() {
        return this.answers;
    }

	public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

	public Set<Record> getRecords() {
        return this.records;
    }

	public void setRecords(Set<Record> records) {
        this.records = records;
    }

	public Set<Conclusion> getConclusions() {
        return this.conclusions;
    }

	public void setConclusions(Set<Conclusion> conclusions) {
        this.conclusions = conclusions;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;

	public Long getId() {
        return this.id;
    }

	public void setId(Long id) {
        this.id = id;
    }

	public Integer getVersion() {
        return this.version;
    }

	public void setVersion(Integer version) {
        this.version = version;
    }

	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

	@Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Scale attached = Scale.findScale(this.id);
            this.entityManager.remove(attached);
        }
    }

	@Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

	@Transactional
    public Scale merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Scale merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public static final EntityManager entityManager() {
        EntityManager em = new Scale().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countScales() {
        return entityManager().createQuery("select count(o) from Scale o", Long.class).getSingleResult();
    }

	public static List<Scale> findAllScales() {
        return entityManager().createQuery("select o from Scale o", Scale.class).getResultList();
    }

	public static Scale findScale(Long id) {
        if (id == null) return null;
        return entityManager().find(Scale.class, id);
    }

	public static List<Scale> findScaleEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from Scale o", Scale.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
}
