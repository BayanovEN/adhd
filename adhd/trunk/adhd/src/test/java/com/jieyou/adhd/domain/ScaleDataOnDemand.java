package com.jieyou.adhd.domain;

import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.dod.RooDataOnDemand;
import org.springframework.stereotype.Component;

@Component
@Configurable
@RooDataOnDemand(entity = Scale.class)
public class ScaleDataOnDemand {

	private Random rnd = new java.security.SecureRandom();

	private List<Scale> data;

	public Scale getNewTransientScale(int index) {
        com.jieyou.adhd.domain.Scale obj = new com.jieyou.adhd.domain.Scale();
        java.lang.String scaleName = "scaleName_" + index;
        if (scaleName.length() > 30) {
            scaleName  = scaleName.substring(0, 30);
        }
        obj.setScaleName(scaleName);
        obj.setScaleType(com.jieyou.adhd.reference.ScaleType.class.getEnumConstants()[0]);
        obj.setDescription("description_" + index);
        return obj;
    }

	public Scale getSpecificScale(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        Scale obj = data.get(index);
        return Scale.findScale(obj.getId());
    }

	public Scale getRandomScale() {
        init();
        Scale obj = data.get(rnd.nextInt(data.size()));
        return Scale.findScale(obj.getId());
    }

	public boolean modifyScale(Scale obj) {
        return false;
    }

	public void init() {
        data = com.jieyou.adhd.domain.Scale.findScaleEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Scale' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new java.util.ArrayList<com.jieyou.adhd.domain.Scale>();
        for (int i = 0; i < 10; i++) {
            com.jieyou.adhd.domain.Scale obj = getNewTransientScale(i);
            obj.persist();
            obj.flush();
            data.add(obj);
        }
    }
}
