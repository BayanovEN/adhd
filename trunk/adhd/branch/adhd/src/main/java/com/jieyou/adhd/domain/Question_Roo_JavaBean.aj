// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.jieyou.adhd.domain;

import com.jieyou.adhd.domain.Scale;
import java.lang.Integer;
import java.lang.String;

privileged aspect Question_Roo_JavaBean {
    
    public Scale Question.getScale() {
        return this.scale;
    }
    
    public void Question.setScale(Scale scale) {
        this.scale = scale;
    }
    
    public String Question.getSectionDescription() {
        return this.sectionDescription;
    }
    
    public void Question.setSectionDescription(String sectionDescription) {
        this.sectionDescription = sectionDescription;
    }
    
    public Integer Question.getQuestionNo() {
        return this.questionNo;
    }
    
    public void Question.setQuestionNo(Integer questionNo) {
        this.questionNo = questionNo;
    }
    
    public String Question.getQuestionContent() {
        return this.questionContent;
    }
    
    public void Question.setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }
    
}