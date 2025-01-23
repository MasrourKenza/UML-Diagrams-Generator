package org.mql.java.extraction.models;

public abstract class JavaElement {
    protected String name;
    public String visibility;
    public boolean isStatic;
    public boolean isFinal;
    
    public JavaElement() {
	}
    
    public JavaElement(String name) {
    	super();
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
    
    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }
    
    public void setFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }
}