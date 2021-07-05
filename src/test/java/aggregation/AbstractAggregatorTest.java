package aggregation;

public abstract class AbstractAggregatorTest {
	/*
	protected Double shouldAggregate() {
		List<CyRowAdapter> mockRows = createMockRows();
		CyEdge mockEdge = createMockEdge();
		MetaNetwork mockMetaNetwork = createMockMetaNetwork(mockRows, mockEdge);
		Double testAggregatedWeight=null;
		try{
			testAggregatedWeight = getAggregator().aggregate(mockEdge, mockMetaNetwork);	
		}catch(InvalidColumnException e){
			fail("Exception should not be thrown.");
		}
		return testAggregatedWeight;
	}
	
	private List<CyRowAdapter> createMockRows(){
		List<CyRowAdapter> mockRows = new ArrayList<CyRowAdapter>();
		for(String timeline:getTimelines()){
			CyRowAdapter mockRow = Mockito.mock(CyRowAdapter.class);
			when(mockRow.get(AppColumns.TIMELINE, String.class)).thenReturn(timeline);
			mockRows.add(mockRow);
		}
		return mockRows;
	}
	
	private CyEdge createMockEdge(){
		CyEdge mockEdge = Mockito.mock(CyEdge.class);
		return mockEdge;
	}
	
	private MetaNetwork createMockMetaNetwork(List<CyRowAdapter> mockRows, CyEdge mockEdge){
		MetaNetwork metaEdge = Mockito.mock(MetaNetwork.class);
		CyRowAdapter mockRow = Mockito.mock(CyRowAdapter.class);
		when(metaEdge.getHiddenSubRows(mockEdge)).thenReturn(mockRows);
		when(metaEdge.getTimelineType()).thenReturn(TimelineType.TIMELINE);
		when(metaEdge.getHiddenRow(mockEdge)).thenReturn(mockRow);
		when(mockRow.get(AppColumns.IS_METAEDGE, Boolean.class))
			.thenReturn(true);
		return metaEdge;
	}
	
	protected abstract MetaAggregator<Double> getAggregator();
	protected abstract List<String> getTimelines();
	*/
	
}
