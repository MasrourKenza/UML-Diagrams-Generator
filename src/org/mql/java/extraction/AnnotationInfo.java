package org.mql.java.extraction;

import java.util.ArrayList;
import java.util.List;

public class AnnotationInfo extends JavaElement {
    private List<MethodInfo> elements;
    
    public AnnotationInfo(String name) {
        super(name);
        this.elements = new ArrayList<>();
    }
    
    public void addElement(MethodInfo element) {
        this.elements.add(element);
    }
    
    public List<MethodInfo> getElements() {
        return elements;
    }
}