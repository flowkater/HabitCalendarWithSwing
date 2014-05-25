package com.habit.lib;

import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import com.habit.model.DailyHabit;

public class TweetPost {
	public void tweetMyHabit(String date, List<DailyHabit> dailyHabits)
			throws TwitterException {
		Twitter twitter = TwitterFactory.getSingleton();
		int miss = 0;
		int success = 0;
		for (DailyHabit dailyHabit : dailyHabits) {
			if (dailyHabit.getStatus() == 0) {
				miss++;
			} else if (dailyHabit.getStatus() == 1) {
				success++;
			}
		}
		String message = "성공 습관 프로젝트: " + date + " 성공:" + success + "개, 실패:"
				+ miss + "개";
		Status status = twitter.updateStatus(message);
	}
}