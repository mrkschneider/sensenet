package util;

import static com.tcb.cytoscape.cyLib.util.GetFromRows.getFromRows;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.errors.InvalidColumnException;

public class GetFromRowsTest {
		
	private AppColumns column;

	@Before
	public void setUp() throws Exception {
		column = AppColumns.CHAIN;
	}

	@Test
	public void testGetFromRows() {
		
		List<Double> referenceColValues = createReferenceColValues();
		List<CyRowAdapter> mockRows = createMockRows();
		attachReferenceColValuesToMockRows(referenceColValues,mockRows);
					
		try{
			List<Double> testColValues = getFromRows(mockRows, column, Double.class);
			assertEquals(referenceColValues,testColValues);
		} catch(InvalidColumnException e){
			fail("Exception should not be thrown.");
		}
					
	}
		
	private List<Double> createReferenceColValues(){
			List<Double> colValues = new ArrayList<Double>();
			colValues.add(1.0d);
			colValues.add(0.5d);
			return colValues;
	}
		
	private List<CyRowAdapter> createMockRows(){
		CyRowAdapter mockRow = Mockito.mock(CyRowAdapter.class);
		CyRowAdapter mockRow2 = Mockito.mock(CyRowAdapter.class);
		List<CyRowAdapter> mockRows = new ArrayList<CyRowAdapter>();
		mockRows.add(mockRow);
		mockRows.add(mockRow2);
		return mockRows;
	}
		
	private void attachReferenceColValuesToMockRows(List<Double> colValues, List<CyRowAdapter> mockRows) {
		for(int i=0;i<colValues.size();i++){
			CyRowAdapter mockRow = mockRows.get(i);
			Double colValue = colValues.get(i);
			when(mockRow.get(column, Double.class)).thenReturn(colValue);
			
			}
		
	}

}
