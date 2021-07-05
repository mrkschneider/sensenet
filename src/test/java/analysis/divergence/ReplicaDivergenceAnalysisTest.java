package analysis.divergence;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.tcb.sensenet.internal.analysis.divergence.JensenShannonDivergence;
import com.tcb.sensenet.internal.analysis.divergence.ReplicaDivergenceAnalysis;
import com.tcb.sensenet.internal.analysis.divergence.SymmetricKullbackLeiblerDivergence;
import com.tcb.sensenet.internal.meta.timeline.MetaTimelineImpl;
import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.sensenet.internal.util.iterator.IntIterable;

public class ReplicaDivergenceAnalysisTest {

	private List<Integer> xs;
	private int blocks;

	@Before
	public void setUp() throws Exception {
		this.xs = Arrays.asList(
				0,0,0,1,1,2,2,2,2,3,
				0,0,0,0,1,1,2,2,3,3,
				0,0,1,2,2,2,2,3,3,3);
		this.blocks = 3;
	}

	@Ignore
	@Test
	public void testCreate() {
		ReplicaDivergenceAnalysis analysis = 
				new ReplicaDivergenceAnalysis(new JensenShannonDivergence());
		
		ObjMap ana = analysis.analyse(
				MetaTimelineImpl.create(IntIterable.of(xs)),
				blocks, 0.1);
				
		
		List<Double> refs = Arrays.asList(
			0.495, 0.495,
			0.439, 0.331,
			0.283, 0.224,
			0.156, 0.108,
			0.076, 0.041);
		
		@SuppressWarnings("unchecked")
		List<Double> divs = ana.get("divergences",List.class);
		assertEquals(10,divs.size());
		for(int i=0;i<refs.size();i++){
			assertEquals(refs.get(i),divs.get(i),0.001);
		}
	}

}
