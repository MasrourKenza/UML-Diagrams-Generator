package org.mql.java.extraction;

import java.util.ArrayList;
import java.util.List;

public class ClassInfo extends JavaElement {
    private List<FieldInfo> fields;
    private List<MethodInfo> methods;
    private Class<?> superclass;
    private List<Class<?>> interfaces;
    
    public ClassInfo(String name) {
        super(name);
        this.fields = new ArrayList<>();
        this.methods = new ArrayList<>();
        this.interfaces = new ArrayList<>();
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