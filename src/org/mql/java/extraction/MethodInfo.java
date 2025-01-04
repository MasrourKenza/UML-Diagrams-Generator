package org.mql.java.extraction;

import java.util.ArrayList;
import java.util.List;

public class MethodInfo extends JavaElement {
    Class<?> returnType;
    List<Class<?>> parameterTypes;
    
    public MethodInfo(String name) {
        super(name);
        this.parameterTypes = new ArrayList<>();
    }
    
    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }
    
    public void addParameterType(Class<?> paramType) {
        this.parameterTypes.add(paramType);
    }
}