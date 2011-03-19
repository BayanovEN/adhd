package com.jieyou.adhd.service;

import java.util.ArrayList;
import java.util.List;
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
		String[] num=answer.split(",");
		for (int i = 0; i < num.length; i++) {
			String string = num[i];
			result += Integer.valueOf(string);
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
	public Integer[] getAnswers(Record record) {
		String answers=record.getAnswers();
		String[] st=answers.split(",");
		List<Integer> result=new ArrayList<Integer>();
		for (int i = 0; i < st.length; i++) {
			result.add(Integer.parseInt(st[i]));
		}
		return result.toArray(new Integer[result.size()]);
	}
}