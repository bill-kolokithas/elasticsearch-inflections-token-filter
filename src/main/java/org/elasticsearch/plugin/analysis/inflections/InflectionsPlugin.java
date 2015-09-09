package org.elasticsearch.plugin.analysis.inflections;

import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.index.analysis.InflectionsBinderProcessor;
import org.elasticsearch.plugins.AbstractPlugin;

public class InflectionsPlugin extends AbstractPlugin {

	@Override
	public String description() {
		return "Inflections token filter";
	}

	@Override
	public String name() {
		return "inflections";
	}

	public void onModule(AnalysisModule module) {
		module.addProcessor(new InflectionsBinderProcessor());
	}
}

