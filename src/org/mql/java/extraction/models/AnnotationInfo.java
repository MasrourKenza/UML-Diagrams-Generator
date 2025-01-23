package org.mql.java.extraction.models;

import java.util.List;
import java.util.Vector;


public class AnnotationInfo extends JavaElement {
    private List<MethodInfo> elements;
    
public AnnotationInfo() {
}    
    public AnnotationInfo(String name) {
        super(name);
        this.elements = new Vector<>();
    }
    
    public void addElement(MethodInfo element) {
        this.elements.add(element);
    }
    
    public List<MethodInfo> getElements() {
        return elements;
    }
	
}