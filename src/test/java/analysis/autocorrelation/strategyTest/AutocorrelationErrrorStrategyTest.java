package analysis.autocorrelation.strategyTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.analysis.autocorrelation.AutocorrelationAnalysisAdapter;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.ReplicaAutocorrelationErrorAnalysis;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.strategy.AutocorrelationTimeWeightStrategy;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.strategy.AverageAutocorrelationTimeWeightStrategy;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.strategy.ChannelAutocorrelationTimeWeightStrategy;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.strategy.MaxAutocorrelationTimeWeightStrategy;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.strategy.MinAutocorrelationTimeWeightStrategy;
import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.meta.timeline.MetaTimelineImpl;
import com.tcb.sensenet.internal.meta.timeline.RandomIntegerMetaTimelineFactory;
import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.matrix.TriangularMatrix;

import analysis.autocorrelation.strategy.MaxAutocorrelationTimeMergeStrategyTest;

public class AutocorrelationErrrorStrategyTest {

	private List<AutocorrelationTimeWeightStrategy> strategies;
	private TriangularMatrix transitions;
	private RandomIntegerMetaTimelineFactory metaTimelineFactory;

	@Before
	public void setUp() throws Exception {
		this.strategies = Arrays.asList(
				new ChannelAutocorrelationTimeWeightStrategy(),
				new MaxAutocorrelationTimeWeightStrategy(),
				new AverageAutocorrelationTimeWeightStrategy(),
				new MinAutocorrelationTimeWeightStrategy());
		transitions = new TriangularMatrix(3);
		transitions.set(0, 0, 0.98);
		transitions.set(0, 1, 0.02);
		transitions.set(0, 2, 0.00);
		transitions.set(1, 1, 0.98);
		transitions.set(1, 2, 0.0);
		transitions.set(2, 2, 0.5);
		
		transitions.set(0, 0, 0.99);
		transitions.set(0,1,0.01);
		transitions.set(1,1,0.99);
		
		this.metaTimelineFactory = new RandomIntegerMetaTimelineFactory(1, 50000, transitions);
	}

	//@Test
	public void testCompare(){
		for(AutocorrelationTimeWeightStrategy strategy:strategies){
			System.out.println(strategy.getClass().getSimpleName());
			Double autocorrelationTime = getStrategyAutocorrelationTime(strategy);
			System.out.println(autocorrelationTime);
		}
	}
	
	private Double getStrategyAutocorrelationTime(AutocorrelationTimeWeightStrategy strategy){
		ReplicaAutocorrelationErrorAnalysis errorFactory = 
				new ReplicaAutocorrelationErrorAnalysis(10,0.05,strategy);
		List<Double> autocorrelationTimes = new ArrayList<>();
		for(int i=0;i<100;i++){
			List<Double> observations = new ArrayList<>();
			for(int replica=0;replica<10;replica++){
				MetaTimeline metaTimeline = metaTimelineFactory.create();
				PrimitiveIterator.OfDouble it = metaTimeline.doubles();
				while(it.hasNext()) observations.add(it.nextDouble());
			}
			double[] data = observations.stream()
					.mapToDouble(d -> d)
					.toArray();
			ObjMap analysis = errorFactory.analyse(MetaTimelineImpl.create(data));
			autocorrelationTimes.add(analysis.get("autocorrelationTime",Double.class));
		}
		Double avg = autocorrelationTimes.stream()
				.mapToDouble(d -> d)
				.average().getAsDouble();
		return avg;
	}
	
}
