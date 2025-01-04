package org.mql.java.extraction;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ProjectScanner {
    private Project project;
    
    public ProjectScanner(String projectName, String rootPath) {
        this.project = new Project(projectName, rootPath);
    }
    
    public Project scanProject() {
        File rootDir = new File(project.getRootPath());
        scanDirectory(rootDir, "");
        return project;
    }
    
    private void scanDirectory(File dir, String currentPackage) {
        File[] files = dir.listFiles();
        if (files == null) return;
        
        Package currentPkg = new Package(currentPackage);
        project.addPackage(currentPkg);
        
        for (File file : files) {
            if (file.isDirectory()) {
                String newPackage = currentPackage.isEmpty() ? 
                    file.getName() : currentPackage + "." + file.getName();
                scanDirectory(file, newPackage);
            } else if (file.getName().endsWith(".class")) {
                scanClass(file, currentPkg);
            }
        }
    }
    
    private void scanClass(File file, Package pkg) {
        try {
            String className = file.getName().replace(".class", "");
            Class<?> cls = Class.forName(pkg.getName() + "." + className);
            
            if (cls.isInterface()) {
                scanInterface(cls, pkg);
            } else if (cls.isEnum()) {
                scanEnum(cls, pkg);
            } else if (cls.isAnnotation()) {
                scanAnnotation(cls, pkg);
            } else {
                scanRegularClass(cls, pkg);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Erreur lors du scan de la classe: " + e.getMessage());
        }
    }
    
    private void scanRegularClass(Class<?> cls, Package pkg) {
        ClassInfo classInfo = new ClassInfo(cls.getSimpleName());
        
        for (Field field : cls.getDeclaredFields()) {
            FieldInfo fieldInfo = new FieldInfo(field.getName(), field.getType());
            fieldInfo.setStatic(Modifier.isStatic(field.getModifiers()));
            fieldInfo.setFinal(Modifier.isFinal(field.getModifiers()));
            classInfo.addField(fieldInfo);
        }
        
        for (Method method : cls.getDeclaredMethods()) {
            MethodInfo methodInfo = new MethodInfo(method.getName());
            methodInfo.setStatic(Modifier.isStatic(method.getModifiers()));
            classInfo.addMethod(methodInfo);
        }
        
        pkg.addClass(classInfo);
    }
    
    private void scanInterface(Class<?> cls, Package pkg) {
        InterfaceInfo interfaceInfo = new InterfaceInfo(cls.getSimpleName());
        pkg.addInterface(interfaceInfo);
    }
    
    private void scanEnum(Class<?> cls, Package pkg) {
        EnumInfo enumInfo = new EnumInfo(cls.getSimpleName());
        for (Object constant : cls.getEnumConstants()) {
            enumInfo.constants.add(constant.toString());
        }
        pkg.addEnum(enumInfo);
    }
    
    private void scanAnnotation(Class<?> cls, Package pkg) {
        AnnotationInfo annotationInfo = new AnnotationInfo(cls.getSimpleName());
        pkg.addAnnotation(annotationInfo);
    }
}