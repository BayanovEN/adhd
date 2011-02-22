package com.jieyou.adhd.domain;

import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.dod.RooDataOnDemand;
import org.springframework.stereotype.Component;

@Configurable
@Component
@RooDataOnDemand(entity = Answer.class)
public class AnswerDataOnDemand {

	private Random rnd = new java.security.SecureRandom();

	private List<Answer> data;

	@Autowired
    private ScaleDataOnDemand scaleDataOnDemand;

	public Answer getNewTransientAnswer(int index) {
        com.jieyou.adhd.domain.Answer obj = new com.jieyou.adhd.domain.Answer();
        obj.setScale(scaleDataOnDemand.getRandomScale());
        obj.setAnswerDescription("answerDescription_" + index);
        java.lang.Integer score = new Integer(index);
        if (score < 1) {
            score = 1;
        }
        obj.setScore(score);
        return obj;
    }

	public Answer getSpecificAnswer(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        Answer obj = data.get(index);
        return Answer.findAnswer(obj.getId());
    }

	public Answer getRandomAnswer() {
        init();
        Answer obj = data.get(rnd.nextInt(data.size()));
        return Answer.findAnswer(obj.getId());
    }

	public boolean modifyAnswer(Answer obj) {
        return false;
    }

	public void init() {
        data = com.jieyou.adhd.domain.Answer.findAnswerEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Answer' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new java.util.ArrayList<com.jieyou.adhd.domain.Answer>();
        for (int i = 0; i < 10; i++) {
            com.jieyou.adhd.domain.Answer obj = getNewTransientAnswer(i);
            obj.persist();
            obj.flush();
            data.add(obj);
        }
    }
}
