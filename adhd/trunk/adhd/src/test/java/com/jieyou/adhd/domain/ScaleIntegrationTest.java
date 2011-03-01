package com.jieyou.adhd.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
@Transactional
@Configurable
@RooIntegrationTest(entity = Scale.class)
public class ScaleIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    private ScaleDataOnDemand dod;

	@Test
    public void testCountScales() {
        org.junit.Assert.assertNotNull("Data on demand for 'Scale' failed to initialize correctly", dod.getRandomScale());
        long count = com.jieyou.adhd.domain.Scale.countScales();
        org.junit.Assert.assertTrue("Counter for 'Scale' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindScale() {
        com.jieyou.adhd.domain.Scale obj = dod.getRandomScale();
        org.junit.Assert.assertNotNull("Data on demand for 'Scale' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Scale' failed to provide an identifier", id);
        obj = com.jieyou.adhd.domain.Scale.findScale(id);
        org.junit.Assert.assertNotNull("Find method for 'Scale' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'Scale' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllScales() {
        org.junit.Assert.assertNotNull("Data on demand for 'Scale' failed to initialize correctly", dod.getRandomScale());
        long count = com.jieyou.adhd.domain.Scale.countScales();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'Scale', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<com.jieyou.adhd.domain.Scale> result = com.jieyou.adhd.domain.Scale.findAllScales();
        org.junit.Assert.assertNotNull("Find all method for 'Scale' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'Scale' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindScaleEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'Scale' failed to initialize correctly", dod.getRandomScale());
        long count = com.jieyou.adhd.domain.Scale.countScales();
        if (count > 20) count = 20;
        java.util.List<com.jieyou.adhd.domain.Scale> result = com.jieyou.adhd.domain.Scale.findScaleEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'Scale' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'Scale' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        com.jieyou.adhd.domain.Scale obj = dod.getRandomScale();
        org.junit.Assert.assertNotNull("Data on demand for 'Scale' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Scale' failed to provide an identifier", id);
        obj = com.jieyou.adhd.domain.Scale.findScale(id);
        org.junit.Assert.assertNotNull("Find method for 'Scale' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyScale(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'Scale' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testMerge() {
        com.jieyou.adhd.domain.Scale obj = dod.getRandomScale();
        org.junit.Assert.assertNotNull("Data on demand for 'Scale' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Scale' failed to provide an identifier", id);
        obj = com.jieyou.adhd.domain.Scale.findScale(id);
        boolean modified =  dod.modifyScale(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        com.jieyou.adhd.domain.Scale merged = (com.jieyou.adhd.domain.Scale) obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'Scale' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'Scale' failed to initialize correctly", dod.getRandomScale());
        com.jieyou.adhd.domain.Scale obj = dod.getNewTransientScale(Integer.MAX_VALUE);
        obj.setDescription("更改:");
        org.junit.Assert.assertNotNull("Data on demand for 'Scale' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'Scale' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'Scale' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testRemove() {
        com.jieyou.adhd.domain.Scale obj = dod.getRandomScale();
        org.junit.Assert.assertNotNull("Data on demand for 'Scale' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Scale' failed to provide an identifier", id);
        obj = com.jieyou.adhd.domain.Scale.findScale(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'Scale' with identifier '" + id + "'", com.jieyou.adhd.domain.Scale.findScale(id));
    }
}
