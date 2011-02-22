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
public class Question {

    @NotNull
    @ManyToOne
    private Scale scale;

    @NotNull
    private String sectionDescription;

    @NotNull
    @Min(1L)
    private Integer questionNo;

    @NotNull
    private String questionContent;

	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Scale: ").append(getScale()).append(", ");
        sb.append("SectionDescription: ").append(getSectionDescription()).append(", ");
        sb.append("QuestionNo: ").append(getQuestionNo()).append(", ");
        sb.append("QuestionContent: ").append(getQuestionContent());
        return sb.toString();
    }

	public Scale getScale() {
        return this.scale;
    }

	public void setScale(Scale scale) {
        this.scale = scale;
    }

	public String getSectionDescription() {
        return this.sectionDescription;
    }

	public void setSectionDescription(String sectionDescription) {
        this.sectionDescription = sectionDescription;
    }

	public Integer getQuestionNo() {
        return this.questionNo;
    }

	public void setQuestionNo(Integer questionNo) {
        this.questionNo = questionNo;
    }

	public String getQuestionContent() {
        return this.questionContent;
    }

	public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
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
            Question attached = Question.findQuestion(this.id);
            this.entityManager.remove(attached);
        }
    }

	@Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

	@Transactional
    public Question merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Question merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public static final EntityManager entityManager() {
        EntityManager em = new Question().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countQuestions() {
        return entityManager().createQuery("select count(o) from Question o", Long.class).getSingleResult();
    }

	public static List<Question> findAllQuestions() {
        return entityManager().createQuery("select o from Question o", Question.class).getResultList();
    }

	public static Question findQuestion(Long id) {
        if (id == null) return null;
        return entityManager().find(Question.class, id);
    }

	public static List<Question> findQuestionEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from Question o", Question.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
}
