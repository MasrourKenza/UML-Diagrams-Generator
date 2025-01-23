package org.mql.java.extraction.models;


public class FieldInfo extends JavaElement {
    private Class<?> type;
    private Object value;
    
   
    public FieldInfo(String name, Class<?> type) {
        super(name);
        this.type = type;
    }
    
    public void setValue(Object value) {
        this.value = value;
    }
    
    public Class<?> getType() {
        return type;
    }
    
    public Object getValue() {
        return value;
    }
}