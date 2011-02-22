package com.jieyou.adhd.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
@Transactional
@RooIntegrationTest(entity = Record.class)
public class RecordIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    private RecordDataOnDemand dod;

	@Test
    public void testCountRecords() {
        org.junit.Assert.assertNotNull("Data on demand for 'Record' failed to initialize correctly", dod.getRandomRecord());
        long count = com.jieyou.adhd.domain.Record.countRecords();
        org.junit.Assert.assertTrue("Counter for 'Record' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindRecord() {
        com.jieyou.adhd.domain.Record obj = dod.getRandomRecord();
        org.junit.Assert.assertNotNull("Data on demand for 'Record' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Record' failed to provide an identifier", id);
        obj = com.jieyou.adhd.domain.Record.findRecord(id);
        org.junit.Assert.assertNotNull("Find method for 'Record' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'Record' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllRecords() {
        org.junit.Assert.assertNotNull("Data on demand for 'Record' failed to initialize correctly", dod.getRandomRecord());
        long count = com.jieyou.adhd.domain.Record.countRecords();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'Record', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<com.jieyou.adhd.domain.Record> result = com.jieyou.adhd.domain.Record.findAllRecords();
        org.junit.Assert.assertNotNull("Find all method for 'Record' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'Record' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindRecordEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'Record' failed to initialize correctly", dod.getRandomRecord());
        long count = com.jieyou.adhd.domain.Record.countRecords();
        if (count > 20) count = 20;
        java.util.List<com.jieyou.adhd.domain.Record> result = com.jieyou.adhd.domain.Record.findRecordEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'Record' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'Record' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        com.jieyou.adhd.domain.Record obj = dod.getRandomRecord();
        org.junit.Assert.assertNotNull("Data on demand for 'Record' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Record' failed to provide an identifier", id);
        obj = com.jieyou.adhd.domain.Record.findRecord(id);
        org.junit.Assert.assertNotNull("Find method for 'Record' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyRecord(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'Record' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testMerge() {
        com.jieyou.adhd.domain.Record obj = dod.getRandomRecord();
        org.junit.Assert.assertNotNull("Data on demand for 'Record' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Record' failed to provide an identifier", id);
        obj = com.jieyou.adhd.domain.Record.findRecord(id);
        boolean modified =  dod.modifyRecord(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        com.jieyou.adhd.domain.Record merged = (com.jieyou.adhd.domain.Record) obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'Record' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'Record' failed to initialize correctly", dod.getRandomRecord());
        com.jieyou.adhd.domain.Record obj = dod.getNewTransientRecord(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'Record' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'Record' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'Record' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testRemove() {
        com.jieyou.adhd.domain.Record obj = dod.getRandomRecord();
        org.junit.Assert.assertNotNull("Data on demand for 'Record' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Record' failed to provide an identifier", id);
        obj = com.jieyou.adhd.domain.Record.findRecord(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'Record' with identifier '" + id + "'", com.jieyou.adhd.domain.Record.findRecord(id));
    }
}
