package org.elasticsearch.index.analysis;

import java.lang.reflect.*;
import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.KeywordAttribute;
import org.inflections.Inflector;


public final class InflectionsTokenFilter extends TokenFilter {

	private final CharTermAttribute termAttribute = addAttribute(CharTermAttribute.class);
	private final String inflection;

	public InflectionsTokenFilter(TokenStream input, String inflection) {
		super(input);
		this.inflection = inflection;
	}

	@Override
	public final boolean incrementToken() throws IOException {
		Class thisClass = null;
		Object iClass = null;
		Method thisMethod = null;
		String inflected = null;

		try {
			thisClass = Class.forName("org.inflections.Inflector");
		} catch(ClassNotFoundException e) { throw new IllegalArgumentException(e.getMessage()); }
		try {
			iClass = thisClass.newInstance();
		} catch(InstantiationException | IllegalAccessException e) { throw new IllegalArgumentException(e.getMessage()); }

		try {
			thisMethod = thisClass.getDeclaredMethod(inflection, String.class);
		} catch(NoSuchMethodException e) { throw new IllegalArgumentException(e.getMessage()); }

		if (input.incrementToken()) {
			try {
				inflected = thisMethod.invoke(iClass, termAttribute.toString()).toString();
			} catch (IllegalAccessException | InvocationTargetException e) { throw new IllegalArgumentException(e.getMessage()); }

			termAttribute.copyBuffer(inflected.toCharArray(), 0, inflected.length());
			return true;
		} else
			return false;
	}
}
