package com.jieyou.adhd.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import com.jieyou.adhd.domain.Scale;
import javax.validation.constraints.NotNull;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;

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
}
