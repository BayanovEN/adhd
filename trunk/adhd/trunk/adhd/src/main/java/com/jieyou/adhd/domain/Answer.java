package com.jieyou.adhd.domain;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;
import com.jieyou.adhd.domain.Scale;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import javax.validation.constraints.Min;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooEntity
public class Answer {

    @NotNull
    @ManyToOne
    private Scale scale;

    @NotNull
    private String answerDescription;

    @NotNull
    @Min(0L)
    private Integer score;

	public Scale getScale() {
        return this.scale;
    }

	public void setScale(Scale scale) {
        this.scale = scale;
    }

	public String getAnswerDescription() {
        return this.answerDescription;
    }

	public void setAnswerDescription(String answerDescription) {
        this.answerDescription = answerDescription;
    }

	public Integer getScore() {
        return this.score;
    }

	public void setScore(Integer score) {
        this.score = score;
    }

	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Scale: ").append(getScale()).append(", ");
        sb.append("AnswerDescription: ").append(getAnswerDescription()).append(", ");
        sb.append("Score: ").append(getScore());
        return sb.toString();
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
            Answer attached = Answer.findAnswer(this.id);
            this.entityManager.remove(attached);
        }
    }

	@Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

	@Transactional
    public Answer merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Answer merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public static final EntityManager entityManager() {
        EntityManager em = new Answer().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countAnswers() {
        return entityManager().createQuery("select count(o) from Answer o", Long.class).getSingleResult();
    }

	public static List<Answer> findAllAnswers() {
        return entityManager().createQuery("select o from Answer o", Answer.class).getResultList();
    }

	public static Answer findAnswer(Long id) {
        if (id == null) return null;
        return entityManager().find(Answer.class, id);
    }

	public static List<Answer> findAnswerEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from Answer o", Answer.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
}
