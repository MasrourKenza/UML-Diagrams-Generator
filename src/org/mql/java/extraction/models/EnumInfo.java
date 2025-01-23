package org.mql.java.extraction.models;

import java.util.List;
import java.util.Vector;

public class EnumInfo extends JavaElement {
    List<String> constants;
    private List<MethodInfo> methods;
    
    public EnumInfo() {
	}
    
    public EnumInfo(String name) {
        super(name);
        this.constants = new Vector<>();
        this.methods = new Vector<>();
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