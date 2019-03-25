package org.elasticsearch.index.analysis;

import java.util.List;
import java.util.Arrays;
import org.apache.lucene.analysis.TokenStream;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.settings.IndexSettingsService;

public class InflectionsTokenFilterFactory  extends AbstractTokenFilterFactory {

	private final String inflection;

	@Inject
	public InflectionsTokenFilterFactory(Index index, IndexSettingsService indexSettings, @Assisted String name, @Assisted Settings settings) {
		super(index, indexSettings.getSettings(), name, settings);
		this.inflection = settings.get("inflection", null);
	}

	@Override
	public TokenStream create(TokenStream tokenStream) {
		if (inflection == null)
			throw new IllegalArgumentException("inflection can't be empty");

		List valid = Arrays.asList("pluralize", "singularize", "tableize", "underscore", "camelize", "capitalize");
		String inflection_lowercase = inflection.toLowerCase();

		if (valid.contains(inflection_lowercase))
			return new InflectionsTokenFilter(tokenStream, inflection_lowercase);
		else
			throw new IllegalArgumentException("inflection [" + inflection + "] not supported");
	}
}
