package analysis.autocorrelation.strategy;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.analysis.autocorrelation.AutocorrelationAnalysisAdapter;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.strategy.AverageAutocorrelationTimeWeightStrategy;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.strategy.MaxAutocorrelationTimeWeightStrategy;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.strategy.MinAutocorrelationTimeWeightStrategy;
import com.tcb.mdAnalysis.statistics.regression.Regression;
import com.tcb.mdAnalysis.statistics.regression.WeightedLinearRegression;

public class MinAutocorrelationTimeMergeStrategyTest {

	private List<AutocorrelationAnalysisAdapter> analyses;
	private MinAutocorrelationTimeWeightStrategy strategy;

	@Before
	public void setUp() throws Exception {
		this.analyses = Arrays.asList(
				createAnalysis(50.),
				createAnalysis(200.),
				createAnalysis(50.));
		this.strategy = new MinAutocorrelationTimeWeightStrategy();
	}
	
	private AutocorrelationAnalysisAdapter createAnalysis(Double autocorrelationTime){
		return AutocorrelationAnalysisAdapter.create(
				new ArrayList<>(), 1000, new WeightedLinearRegression(new ArrayList<>()),
				autocorrelationTime);
	}

	@Test
	public void testMerge() {
		assertEquals(50.0,strategy.merge(analyses),0.01);
	}
	
	@Test
	public void testMergeWithNaN() {
		List<AutocorrelationAnalysisAdapter> analyses = new ArrayList<>(this.analyses);
		analyses.add(createAnalysis(Double.NaN));
		assertEquals(50.0,strategy.merge(analyses),0.01);
	}
	
	@Test
	public void testMergeWithOnlyNaN() {
		List<AutocorrelationAnalysisAdapter> analyses = new ArrayList<>();
		analyses.add(createAnalysis(Double.NaN));
		analyses.add(createAnalysis(Double.NaN));
		assertEquals(1.0,strategy.merge(analyses),0.01);
	}

}
