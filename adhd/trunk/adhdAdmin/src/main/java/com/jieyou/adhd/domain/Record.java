package com.jieyou.adhd.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;


@Configurable
@Entity
@RooJavaBean
@RooToString
@RooEntity
public class Record {

    @NotNull
    @ManyToOne
    private Scale scale;

    @NotNull
    private String patientId;

    @NotNull
    private String answers;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date doneDay;

    @NotNull
    private Boolean isFinished;

    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("Scale: ").append(getScale()).append(", ");
	sb.append("PatientId: ").append(getPatientId()).append(", ");
	sb.append("Answers: ").append(getAnswers()).append(", ");
	sb.append("DoneDay: ").append(getDoneDay()).append(", ");
	sb.append("IsFinished: ").append(getIsFinished());
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
	if (this.entityManager == null)
	    this.entityManager = entityManager();
	this.entityManager.persist(this);
    }

    @Transactional
    public void remove() {
	if (this.entityManager == null)
	    this.entityManager = entityManager();
	if (this.entityManager.contains(this)) {
	    this.entityManager.remove(this);
	} else {
	    Record attached = Record.findRecord(this.id);
	    this.entityManager.remove(attached);
	}
    }

    @Transactional
    public void flush() {
	if (this.entityManager == null)
	    this.entityManager = entityManager();
	this.entityManager.flush();
    }

    @Transactional
    public Record merge() {
	if (this.entityManager == null)
	    this.entityManager = entityManager();
	Record merged = this.entityManager.merge(this);
	this.entityManager.flush();
	return merged;
    }

    public static final EntityManager entityManager() {
	EntityManager em = new Record().entityManager;
	if (em == null)
	    throw new IllegalStateException(
		    "Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
	return em;
    }

    public static long countRecords() {
	return entityManager().createQuery("select count(o) from Record o", Long.class).getSingleResult();
    }

    public static List<Record> findAllRecords() {
	return entityManager().createQuery("select o from Record o", Record.class).getResultList();
    }

    public static Record findRecord(Long id) {
	if (id == null)
	    return null;
	return entityManager().find(Record.class, id);
    }

    public static List<Record> findRecordEntries(int firstResult, int maxResults) {
	return entityManager().createQuery("select o from Record o", Record.class).setFirstResult(firstResult)
		.setMaxResults(maxResults).getResultList();
    }

    public Scale getScale() {
	return this.scale;
    }

    public void setScale(Scale scale) {
	this.scale = scale;
    }

    public String getPatientId() {
	return this.patientId;
    }

    public void setPatientId(String patientId) {
	this.patientId = patientId;
    }

    public String getAnswers() {
	return this.answers;
    }

    public void setAnswers(String answers) {
	this.answers = answers;
    }

    public Date getDoneDay() {
	return this.doneDay;
    }

    public void setDoneDay(Date doneDay) {
	this.doneDay = doneDay;
    }

    public Boolean getIsFinished() {
	return this.isFinished;
    }

    public void setIsFinished(Boolean isFinished) {
	this.isFinished = isFinished;
    }
}
