package com.jieyou.adhd.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import com.jieyou.adhd.domain.Scale;
import javax.validation.constraints.NotNull;
import javax.persistence.ManyToOne;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

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
}
