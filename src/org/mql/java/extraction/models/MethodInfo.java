package org.mql.java.extraction.models;

import java.util.List;
import java.util.Vector;

public class MethodInfo extends JavaElement {
    public Class<?> returnType;
    public List<Class<?>> parameterTypes;
    
    public MethodInfo() {
	}
    
    public MethodInfo(String name) {
        super(name);
        this.parameterTypes = new Vector<>();
    }
    
    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }
    
    public void addParameterType(Class<?> paramType) {
        this.parameterTypes.add(paramType);
    }
}