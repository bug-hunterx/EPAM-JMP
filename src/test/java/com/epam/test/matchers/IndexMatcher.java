package com.epam.test.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class IndexMatcher extends TypeSafeMatcher<String> {

	public static IndexMatcher isIndexValid() {
		return new IndexMatcher();
	}
	
	@Override
	public void describeTo(Description description) {
		description.appendText("Index starts from year and month");
	}

	@Override
	protected boolean matchesSafely(String value) {
		return value.matches("^([0-9]{6})(.)*");
	}

}
