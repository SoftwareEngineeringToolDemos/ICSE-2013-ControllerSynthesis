package ar.dc.uba.model.lsc;

import java.util.Set;

import ac.ic.doc.mtstools.model.MTS;
import ar.dc.uba.model.condition.Fluent;
import ar.dc.uba.model.language.Symbol;
import ar.dc.uba.model.structure.SynthesizedState;
import ar.dc.uba.synthesis.SynthesisVisitor;

/**
 * An Universal Triggered Scenario
 * @author gsibay
 *
 */
public class UniversalTriggeredScenario extends TriggeredScenario {

	public UniversalTriggeredScenario(Chart prechart, Chart mainchart,
			Set<Symbol> restricted, Set<Fluent> fluents)
			throws IllegalArgumentException {
		super(prechart, mainchart, restricted, fluents);
	}
	
	public String toString() {
		return "Universal Triggered Scenario\n" + super.toString();
	}

	@Override
	public MTS<SynthesizedState, Symbol> acceptSynthesisVisitor(
			SynthesisVisitor synthesisVisitor) {
		return synthesisVisitor.visitUTriggeredScenario(this);
	}	
}
