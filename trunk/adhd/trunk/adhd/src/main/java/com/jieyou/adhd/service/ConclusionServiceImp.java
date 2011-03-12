package com.jieyou.adhd.service;

import java.util.Set;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.jieyou.adhd.domain.Conclusion;
import com.jieyou.adhd.domain.Record;

/**
 * A JPA-based implementation of the Booking Service. Delegates to a JPA entity manager to issue data access calls
 * against the backing repository. The EntityManager reference is provided by the managing container (Spring)
 * automatically.
 */
@Service("conclusionService")
@Repository
public class ConclusionServiceImp implements ConclusionService {

	private static final String UNKNOWN = "It can not be determined";

	@Override
	public String getResult(Record record) {
		// TODO Auto-generated method stub
		Integer result=0;
		String answer=record.getAnswers();
		for(int i=0;i<answer.length();i++){
			result += Integer.valueOf(answer.substring(i,i+1));
		}
		Set<Conclusion> conclusions = record.getScale().getConclusions();
		for (Conclusion conclusion : conclusions) {
			if(conclusion.getLowerBound()<=result && result<=conclusion.getUpperLimit()){
				return conclusion.getConclusion();
			}
		}
		return UNKNOWN;
	}

	@Override
	public int[] getAnswers(Record record) {
		String answers=record.getAnswers();
		int[] result=new int[answers.length()];
		char[] c=answers.toCharArray();
		for (int i = 0; i < c.length; i++) {
			result[i]=c[i]-48;
		}
		return result;
	}
}