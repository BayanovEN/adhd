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

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooEntity
public class Conclusion {

    @NotNull
    @ManyToOne
    private Scale scale;

    @NotNull
    private Integer lowerBound;

    @NotNull
    private Integer upperLimit;

    @NotNull
    private String conclusion;

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
            Conclusion attached = Conclusion.findConclusion(this.id);
            this.entityManager.remove(attached);
        }
    }

	@Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

	@Transactional
    public Conclusion merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Conclusion merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public static final EntityManager entityManager() {
        EntityManager em = new Conclusion().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countConclusions() {
        return entityManager().createQuery("select count(o) from Conclusion o", Long.class).getSingleResult();
    }

	public static List<Conclusion> findAllConclusions() {
        return entityManager().createQuery("select o from Conclusion o", Conclusion.class).getResultList();
    }

	public static Conclusion findConclusion(Long id) {
        if (id == null) return null;
        return entityManager().find(Conclusion.class, id);
    }

	public static List<Conclusion> findConclusionEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from Conclusion o", Conclusion.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Scale: ").append(getScale()).append(", ");
        sb.append("LowerBound: ").append(getLowerBound()).append(", ");
        sb.append("UpperLimit: ").append(getUpperLimit()).append(", ");
        sb.append("Conclusion: ").append(getConclusion());
        return sb.toString();
    }

	public Scale getScale() {
        return this.scale;
    }

	public void setScale(Scale scale) {
        this.scale = scale;
    }

	public Integer getLowerBound() {
        return this.lowerBound;
    }

	public void setLowerBound(Integer lowerBound) {
        this.lowerBound = lowerBound;
    }

	public Integer getUpperLimit() {
        return this.upperLimit;
    }

	public void setUpperLimit(Integer upperLimit) {
        this.upperLimit = upperLimit;
    }

	public String getConclusion() {
        return this.conclusion;
    }

	public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }
}
