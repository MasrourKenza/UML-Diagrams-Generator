package org.mql.java.extraction;

import java.util.ArrayList;
import java.util.List;

public class EnumInfo extends JavaElement {
    List<String> constants;
    private List<MethodInfo> methods;
    
    public EnumInfo(String name) {
        super(name);
        this.constants = new ArrayList<>();
        this.methods = new ArrayList<>();
    }
    
    public void addConstant(String constant) {
        constants.add(constant);
    }
    
    public void addMethod(MethodInfo method) {
        methods.add(method);
    }
    
    public List<String> getConstants() {
        return constants;
    }
    
    public List<MethodInfo> getMethods() {
        return methods;
    }
}