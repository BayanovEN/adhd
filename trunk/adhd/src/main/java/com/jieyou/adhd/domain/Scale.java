package com.jieyou.adhd.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.jieyou.adhd.reference.ScaleType;
import javax.persistence.Enumerated;
import java.util.Set;
import com.jieyou.adhd.domain.Question;
import java.util.HashSet;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import com.jieyou.adhd.domain.Answer;
import com.jieyou.adhd.domain.Record;

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
    private Set<Question> questions = new HashSet<Question>();

    @NotNull
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "scale")
    private Set<Answer> answers = new HashSet<Answer>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "scale")
    private Set<Record> records = new HashSet<Record>();
}
