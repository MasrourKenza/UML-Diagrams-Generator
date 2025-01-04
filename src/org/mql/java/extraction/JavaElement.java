package org.mql.java.extraction;

public abstract class JavaElement {
    protected String name;
    protected String visibility;
    protected boolean isStatic;
    protected boolean isFinal;
    
    public JavaElement(String name) {
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