package org.mql.java.extraction.models;

import java.util.List;
import java.util.Vector;

public class InterfaceInfo extends JavaElement {
    private List<MethodInfo> methods;
    private List<Class<?>> extendedInterfaces;
    
    public InterfaceInfo(String name) {
        super(name);
        this.methods = new Vector<>();
        this.extendedInterfaces = new Vector<>();
    }
    
    public void addMethod(MethodInfo method) {
        methods.add(method);
    }
    
    public void addExtendedInterface(Class<?> interfaceClass) {
        extendedInterfaces.add(interfaceClass);
    }
    
    public List<MethodInfo> getMethods() {
        return methods;
    }
    
    public List<Class<?>> getExtendedInterfaces() {
        return extendedInterfaces;
    }
}