package org.mql.java.extraction;

import java.util.ArrayList;
import java.util.List;

public class Package extends JavaElement {
    private List<ClassInfo> classes;
    private List<InterfaceInfo> interfaces;
    private List<EnumInfo> enums;
    private List<AnnotationInfo> annotations;
    
    public Package(String name) {
        super(name);
        this.classes = new ArrayList<>();
        this.interfaces = new ArrayList<>();
        this.enums = new ArrayList<>();
        this.annotations = new ArrayList<>();
    }
    
    public void addClass(ClassInfo classInfo) {
        classes.add(classInfo);
    }
    
    public void addInterface(InterfaceInfo interfaceInfo) {
        interfaces.add(interfaceInfo);
    }
    
    public void addEnum(EnumInfo enumInfo) {
        enums.add(enumInfo);
    }
    
    public void addAnnotation(AnnotationInfo annotationInfo) {
        annotations.add(annotationInfo);
    }
    
    public List<ClassInfo> getClasses() {
        return classes;
    }
    
    public List<InterfaceInfo> getInterfaces() {
        return interfaces;
    }
    
    public List<EnumInfo> getEnums() {
        return enums;
    }
    
    public List<AnnotationInfo> getAnnotations() {
        return annotations;
    }
}