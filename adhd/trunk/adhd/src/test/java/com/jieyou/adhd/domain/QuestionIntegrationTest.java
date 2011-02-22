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
@RooIntegrationTest(entity = Question.class)
public class QuestionIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    private QuestionDataOnDemand dod;

	@Test
    public void testCountQuestions() {
        org.junit.Assert.assertNotNull("Data on demand for 'Question' failed to initialize correctly", dod.getRandomQuestion());
        long count = com.jieyou.adhd.domain.Question.countQuestions();
        org.junit.Assert.assertTrue("Counter for 'Question' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindQuestion() {
        com.jieyou.adhd.domain.Question obj = dod.getRandomQuestion();
        org.junit.Assert.assertNotNull("Data on demand for 'Question' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Question' failed to provide an identifier", id);
        obj = com.jieyou.adhd.domain.Question.findQuestion(id);
        org.junit.Assert.assertNotNull("Find method for 'Question' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'Question' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllQuestions() {
        org.junit.Assert.assertNotNull("Data on demand for 'Question' failed to initialize correctly", dod.getRandomQuestion());
        long count = com.jieyou.adhd.domain.Question.countQuestions();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'Question', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<com.jieyou.adhd.domain.Question> result = com.jieyou.adhd.domain.Question.findAllQuestions();
        org.junit.Assert.assertNotNull("Find all method for 'Question' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'Question' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindQuestionEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'Question' failed to initialize correctly", dod.getRandomQuestion());
        long count = com.jieyou.adhd.domain.Question.countQuestions();
        if (count > 20) count = 20;
        java.util.List<com.jieyou.adhd.domain.Question> result = com.jieyou.adhd.domain.Question.findQuestionEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'Question' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'Question' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        com.jieyou.adhd.domain.Question obj = dod.getRandomQuestion();
        org.junit.Assert.assertNotNull("Data on demand for 'Question' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Question' failed to provide an identifier", id);
        obj = com.jieyou.adhd.domain.Question.findQuestion(id);
        org.junit.Assert.assertNotNull("Find method for 'Question' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyQuestion(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'Question' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testMerge() {
        com.jieyou.adhd.domain.Question obj = dod.getRandomQuestion();
        org.junit.Assert.assertNotNull("Data on demand for 'Question' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Question' failed to provide an identifier", id);
        obj = com.jieyou.adhd.domain.Question.findQuestion(id);
        boolean modified =  dod.modifyQuestion(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        com.jieyou.adhd.domain.Question merged = (com.jieyou.adhd.domain.Question) obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'Question' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'Question' failed to initialize correctly", dod.getRandomQuestion());
        com.jieyou.adhd.domain.Question obj = dod.getNewTransientQuestion(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'Question' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'Question' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'Question' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testRemove() {
        com.jieyou.adhd.domain.Question obj = dod.getRandomQuestion();
        org.junit.Assert.assertNotNull("Data on demand for 'Question' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Question' failed to provide an identifier", id);
        obj = com.jieyou.adhd.domain.Question.findQuestion(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'Question' with identifier '" + id + "'", com.jieyou.adhd.domain.Question.findQuestion(id));
    }
}
