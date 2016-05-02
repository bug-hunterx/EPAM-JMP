package com.epam.test.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class EndNumberMatcher extends TypeSafeMatcher<String>{

	@Override
	public void describeTo(Description description) {
		description.appendText("Index should end with numbers");
		
	}

	@Override
	protected boolean matchesSafely(String value) {
		return value.matches("(.)*([0-9]{2})$");
	}
	
	public static EndNumberMatcher isIndexEndWIthNumber() {
		return new EndNumberMatcher();
	}

}
