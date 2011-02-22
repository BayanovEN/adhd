package com.jieyou.adhd.domain;

import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.dod.RooDataOnDemand;
import org.springframework.stereotype.Component;

@Component
@Configurable
@RooDataOnDemand(entity = Question.class)
public class QuestionDataOnDemand {

	private Random rnd = new java.security.SecureRandom();

	private List<Question> data;

	@Autowired
    private ScaleDataOnDemand scaleDataOnDemand;

	public Question getNewTransientQuestion(int index) {
        com.jieyou.adhd.domain.Question obj = new com.jieyou.adhd.domain.Question();
        obj.setScale(scaleDataOnDemand.getRandomScale());
        obj.setSectionDescription("sectionDescription_" + index);
        java.lang.Integer questionNo = new Integer(index);
        if (questionNo < 1) {
            questionNo = 1;
        }
        obj.setQuestionNo(questionNo);
        obj.setQuestionContent("questionContent_" + index);
        return obj;
    }

	public Question getSpecificQuestion(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        Question obj = data.get(index);
        return Question.findQuestion(obj.getId());
    }

	public Question getRandomQuestion() {
        init();
        Question obj = data.get(rnd.nextInt(data.size()));
        return Question.findQuestion(obj.getId());
    }

	public boolean modifyQuestion(Question obj) {
        return false;
    }

	public void init() {
        data = com.jieyou.adhd.domain.Question.findQuestionEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Question' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new java.util.ArrayList<com.jieyou.adhd.domain.Question>();
        for (int i = 0; i < 10; i++) {
            com.jieyou.adhd.domain.Question obj = getNewTransientQuestion(i);
            obj.persist();
            obj.flush();
            data.add(obj);
        }
    }
}
