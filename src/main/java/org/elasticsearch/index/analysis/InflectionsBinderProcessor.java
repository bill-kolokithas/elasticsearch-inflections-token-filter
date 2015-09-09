package org.elasticsearch.index.analysis;

public class InflectionsBinderProcessor extends AnalysisModule.AnalysisBinderProcessor {

	@Override
	public void processTokenFilters(TokenFiltersBindings tokenFiltersBindings) {
		tokenFiltersBindings.processTokenFilter("inflections", InflectionsTokenFilterFactory.class);
	}
}
