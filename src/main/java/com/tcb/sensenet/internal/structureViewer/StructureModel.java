package com.tcb.sensenet.internal.structureViewer;

import java.awt.Color;
import java.io.IOException;
import java.util.Collection;

import com.tcb.atoms.interactions.Interaction;
import com.tcb.atoms.residues.Residue;
import com.tcb.netmap.structureViewer.StructureViewer;
import com.tcb.netmap.util.limiter.TooManyItemsException;


public class StructureModel {
	
	private String model;
	private Boolean isPaused;
	
	public StructureModel(String model){
		this.model = model;
		this.isPaused = false;
	}
	
	public Boolean isPaused(){
		return isPaused;
	}
	
	public void setPaused(Boolean value){
		isPaused = value;
	}
		
	public String getName(){
		return model;
	}

	public void center(StructureViewer viewer)  throws IOException {
		if(isPaused) return;
		viewer.center(model);
	}


	public void deleteModel(StructureViewer viewer)  throws IOException {
		if(isPaused) return;
		viewer.deleteModel(model);		
	}


	public void hideInteractions(StructureViewer viewer, Collection<Interaction> interactions)  throws IOException {
		if(isPaused) return;
		viewer.hideInteractions(model, interactions);		
	}


	public void hideModel(StructureViewer viewer)  throws IOException {
		if(isPaused) return;
		viewer.hideModel(model);
	}


	public void hideResidues(StructureViewer viewer, Collection<Residue> residues)  throws IOException {
		if(isPaused) return;
		viewer.hideResidues(model, residues);		
	}

	public void setFrame(StructureViewer viewer, Integer n)  throws IOException {
		if(isPaused) return;
		viewer.setFrame(model, n);
	}


	public void showInteractions(StructureViewer viewer, Collection<Interaction> interactions, Color color)  throws TooManyItemsException, IOException {
		if(isPaused) return;
		viewer.showInteractions(model, interactions, color);
	}


	public void showModel(StructureViewer viewer)  throws IOException {
		if(isPaused) return;
		viewer.showModel(model);		
	}


	public void showResidues(StructureViewer viewer, Collection<Residue> residues, Color color)  throws TooManyItemsException, IOException {
		if(isPaused) return;
		viewer.showResidues(model, residues, color);
	}

	public void undoZoom(StructureViewer viewer)  throws IOException {
		if(isPaused) return;
		viewer.undoZoom();		
	}
	
	public void zoomModel(StructureViewer viewer)  throws IOException {
		if(isPaused) return;
		viewer.zoomModel(model);
	}

	public void zoomResidues(StructureViewer viewer, Collection<Residue> residues)  throws IOException {
		if(isPaused) return;
		viewer.zoomResidues(model, residues);		
	}

	public void zoomInteractions(StructureViewer viewer, Collection<Interaction> interactions)  throws IOException {
		if(isPaused) return;
		viewer.zoomInteractions(model, interactions);		
	}
	
	public void colorResidues(StructureViewer viewer, Collection<Residue> residues, Color color) throws IOException {
		if(isPaused) return;
		viewer.colorResidues(model, residues, color);
	}
	
	public void resetColors(StructureViewer viewer) throws IOException {
		if(isPaused) return;
		viewer.resetColors(model);
	}
}
