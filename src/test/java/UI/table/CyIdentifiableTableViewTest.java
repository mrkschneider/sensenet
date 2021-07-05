package UI.table;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.cytoscape.model.CyEdge;
import org.cytoscape.util.swing.FileUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.tcb.sensenet.internal.UI.table.CyIdentifiableTableView;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;

public class CyIdentifiableTableViewTest {

	private CyNetworkAdapter network;
	private FileUtil fileUtil;
	
	protected List<String> columns = Arrays.asList(
			DefaultColumns.SUID.toString(),
			DefaultColumns.NAME.toString());
	protected List<Long> suids = Arrays.asList(1l,2l,3l);
	protected List<String> names = Arrays.asList("a","b","c");
	protected List<Boolean> visible = Arrays.asList(true,false,true);
	protected List<Integer> visibleRows = getVisibleRows(visible);
	protected List<Integer> allRows = IntStream.range(0,suids.size())
			.boxed().collect(Collectors.toList());
	protected List<List<?>> values = Arrays.asList(
			suids,names);
	
	private CyIdentifiableTableView tableView;
	

	@Before
	public void setUp() throws Exception {
		this.network = mockNetwork();
		this.fileUtil = mockFileUtil();
		this.tableView = new CyIdentifiableTableView(suids,columns,values,network,fileUtil);
	}
	
	protected List<Integer> getVisibleRows(List<Boolean> visible){
		List<Integer> result = new ArrayList<Integer>();
		for(int i=0;i<visible.size();i++){
			if(visible.get(i)){
				result.add(i);
			}
		}
		return result;
	}
	
	private CyNetworkAdapter mockNetwork(){
		CyNetworkAdapter m = Mockito.mock(CyNetworkAdapter.class);
		for(int i=0;i<suids.size();i++){
			CyEdge e = null;
			if(visible.get(i)){
				e = Mockito.mock(CyEdge.class);
			}
			when(m.getNodeOrEdge(suids.get(i))).thenReturn(e);
		}
		return m;
	}
	
	private FileUtil mockFileUtil(){
		FileUtil m = Mockito.mock(FileUtil.class);
		return m;
	}
		
	@Test
	public void testGetVisibleRows() {
		assertEquals(visibleRows, tableView.getVisibleRows());
	}

	@Test
	public void testSetFilter() {
		tableView.setFilter(false);
		assertEquals(allRows, tableView.getVisibleRows());
		
		tableView.setFilter(true);
		assertEquals(visibleRows, tableView.getVisibleRows());
	}
	
	private File createTempFile() throws IOException{
		File temp = File.createTempFile("test", ".tmp");
		temp.deleteOnExit();
		return temp;
	}
	
	private int getNumberOfLines(File f) throws IOException{
		FileReader fr = new FileReader(f);
	    LineNumberReader lnr = new LineNumberReader(fr);
	    while(lnr.readLine()!=null) continue;
	    lnr.close();
	    return lnr.getLineNumber();
	}
	
	@Test
	public void testSaveTable() throws IOException {
		File temp = createTempFile();
		
		tableView.setFilter(true);
		tableView.saveTable(temp);
		
		assertEquals(visibleRows.size()+1, getNumberOfLines(temp));
		
		tableView.setFilter(false);
		tableView.saveTable(temp);
		
		assertEquals(allRows.size()+1, getNumberOfLines(temp));
						
	}

}
