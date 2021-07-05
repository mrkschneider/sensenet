package analysis.autocorrelation.strategy;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.analysis.autocorrelation.AutocorrelationAnalysisAdapter;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.strategy.AutocorrelationTimeWeightStrategy;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.strategy.ChannelAutocorrelationTimeWeightStrategy;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.strategy.MaxAutocorrelationTimeWeightStrategy;
import com.tcb.mdAnalysis.statistics.regression.WeightedLinearRegression;

public class ChannelAutocorrelationTimeMergeStrategyTest {

	private List<AutocorrelationAnalysisAdapter> analyses;
	private AutocorrelationTimeWeightStrategy strategy;

	@Before
	public void setUp() throws Exception {
		this.analyses = createAnalyses(50.,200.,50.);
		this.strategy = new ChannelAutocorrelationTimeWeightStrategy();
	}

	private AutocorrelationAnalysisAdapter createAnalysis(Double autocorrelationTime){
		return AutocorrelationAnalysisAdapter.create(
				new ArrayList<>(), 1000, new WeightedLinearRegression(Arrays.asList()), autocorrelationTime);
	}
	
	private List<AutocorrelationAnalysisAdapter> createAnalyses(Double... autocorrelationTimes){
		return Stream.of(autocorrelationTimes)
				.map(a -> createAnalysis(a))
				.collect(Collectors.toList());			
	}
	
	@Test
	public void testMerge() {
		assertEquals(600.0,strategy.merge(analyses),0.01);
	}
	
	@Test
	public void testMergeEqualValues() {
		List<AutocorrelationAnalysisAdapter> times = createAnalyses(100.0,100.0,100.0);
		assertEquals(100.0,strategy.merge(times),0.01);
	}
	
	@Test
	public void testMergeCloseValues() {
		List<AutocorrelationAnalysisAdapter> times = createAnalyses(100.0,90.0,95.0);
		assertEquals(105.45,strategy.merge(times),0.01);
	}
	
	@Test
	public void testMergeDistantValues() {
		List<AutocorrelationAnalysisAdapter> times = createAnalyses(70.0,5.0,5.0);
		assertEquals(676.66,strategy.merge(times),0.01);
	}
	
	@Test
	public void testMergeWithNaN() {
		List<AutocorrelationAnalysisAdapter> times = new ArrayList<>(analyses);
		times.add(createAnalysis(Double.NaN));
		assertEquals(10450.0,strategy.merge(times),0.01);
	}
	
	@Test
	public void testMergeWithOnlyNan() {
		List<AutocorrelationAnalysisAdapter> times = createAnalyses(Double.NaN,Double.NaN,Double.NaN);
		assertEquals(1.0,strategy.merge(times),0.01);
	}
	
}
