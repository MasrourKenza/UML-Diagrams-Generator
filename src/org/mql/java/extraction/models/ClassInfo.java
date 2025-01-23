package org.mql.java.extraction.models;

import java.util.List;
import java.util.Vector;

public class ClassInfo extends JavaElement {
    private List<FieldInfo> fields;
    private List<MethodInfo> methods;
    private Class<?> superclass;
    private List<Class<?>> interfaces;
    
    public ClassInfo() {
	}
    
    public ClassInfo(String name) {
    	super(name);
        this.fields = new Vector<>();
        this.methods = new Vector<>();
        this.interfaces = new Vector<>();
    }
    
    public void addField(FieldInfo field) {
        fields.add(field);
    }
    
    public void addMethod(MethodInfo method) {
        methods.add(method);
    }
    
    public void setSuperclass(Class<?> superclass) {
        this.superclass = superclass;
    }
    
    public void addInterface(Class<?> interfaceClass) {
        interfaces.add(interfaceClass);
    }
    
    public List<FieldInfo> getFields() {
        return fields;
    }
    
    public List<MethodInfo> getMethods() {
        return methods;
    }
    
    public Class<?> getSuperclass() {
        return superclass;
    }
    
    public List<Class<?>> getInterfaces() {
        return interfaces;
    }
}