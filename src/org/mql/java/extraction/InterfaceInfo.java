package org.mql.java.extraction;

import java.util.ArrayList;
import java.util.List;

public class InterfaceInfo extends JavaElement {
    private List<MethodInfo> methods;
    private List<Class<?>> extendedInterfaces;
    
    public InterfaceInfo(String name) {
        super(name);
        this.methods = new ArrayList<>();
        this.extendedInterfaces = new ArrayList<>();
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