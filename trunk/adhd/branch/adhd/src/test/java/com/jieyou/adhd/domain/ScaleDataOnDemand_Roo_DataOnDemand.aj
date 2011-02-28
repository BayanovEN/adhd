// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.jieyou.adhd.domain;

import com.jieyou.adhd.domain.Scale;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;

privileged aspect ScaleDataOnDemand_Roo_DataOnDemand {
    
    declare @type: ScaleDataOnDemand: @Component;
    
    private Random ScaleDataOnDemand.rnd = new java.security.SecureRandom();
    
    private List<Scale> ScaleDataOnDemand.data;
    
    public Scale ScaleDataOnDemand.getNewTransientScale(int index) {
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
    
    public Scale ScaleDataOnDemand.getSpecificScale(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        Scale obj = data.get(index);
        return Scale.findScale(obj.getId());
    }
    
    public Scale ScaleDataOnDemand.getRandomScale() {
        init();
        Scale obj = data.get(rnd.nextInt(data.size()));
        return Scale.findScale(obj.getId());
    }
    
    public boolean ScaleDataOnDemand.modifyScale(Scale obj) {
        return false;
    }
    
    public void ScaleDataOnDemand.init() {
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