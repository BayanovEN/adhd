package com.jieyou.adhd.service;

import com.jieyou.adhd.domain.Record;


/**
 * A service interface for retrieving hotels and bookings from a backing repository. Also supports the ability to cancel
 * a booking.
 */
public interface ConclusionService {

   
    public String getResult(Record record);

	public int[] getAnswers(Record record);


}
