package com.jieyou.adhd.domain;

import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.dod.RooDataOnDemand;
import org.springframework.stereotype.Component;

@Component
@Configurable
@RooDataOnDemand(entity = Record.class)
public class RecordDataOnDemand {

	private Random rnd = new java.security.SecureRandom();

	private List<Record> data;

	@Autowired
    private ScaleDataOnDemand scaleDataOnDemand;

	public Record getNewTransientRecord(int index) {
        com.jieyou.adhd.domain.Record obj = new com.jieyou.adhd.domain.Record();
        obj.setScale(scaleDataOnDemand.getRandomScale());
        obj.setPatientId("patientId_" + index);
        obj.setAnswers("answers_" + index);
        obj.setDoneDay(new java.util.GregorianCalendar(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR), java.util.Calendar.getInstance().get(java.util.Calendar.MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY), java.util.Calendar.getInstance().get(java.util.Calendar.MINUTE), java.util.Calendar.getInstance().get(java.util.Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime());
        obj.setIsFinished(Boolean.TRUE);
        return obj;
    }

	public Record getSpecificRecord(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        Record obj = data.get(index);
        return Record.findRecord(obj.getId());
    }

	public Record getRandomRecord() {
        init();
        Record obj = data.get(rnd.nextInt(data.size()));
        return Record.findRecord(obj.getId());
    }

	public boolean modifyRecord(Record obj) {
        return false;
    }

	public void init() {
        data = com.jieyou.adhd.domain.Record.findRecordEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Record' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new java.util.ArrayList<com.jieyou.adhd.domain.Record>();
        for (int i = 0; i < 10; i++) {
            com.jieyou.adhd.domain.Record obj = getNewTransientRecord(i);
            obj.persist();
            obj.flush();
            data.add(obj);
        }
    }
}
