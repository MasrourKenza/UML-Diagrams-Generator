package org.mql.java.extraction;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.mql.java.extraction.models.*;
import org.mql.java.extraction.models.PackageModel;

public class ProjectScanner {
    private Project project;
    
    public ProjectScanner(String projectName, String rootPath) {
        File rootDir = new File(rootPath);
        if (!rootDir.exists() || !rootDir.isDirectory()) {
            throw new IllegalArgumentException("Le chemin spécifié n'existe pas ou n'est pas un répertoire valide.");
        }
        this.project = new Project(projectName, rootPath);
    }

    public Project scanProject() {
        File rootDir = new File(project.getRootPath());
        scanDirectory(rootDir, "");
        return project;
    }
    
    private void scanDirectory(File fil, String currentPackage) {
        File[] files = fil.listFiles();
        if (files == null) return;
        
        PackageModel currentPkg = new PackageModel(currentPackage);
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
    
    private void scanClass(File file, PackageModel pkg) {
        try {
            String className = file.getName().replace(".class", "");
            Class<?> cls = Class.forName(pkg.getName() + "." + className);
            
            if (cls.isAnnotation()) {
                scanAnnotation(cls, pkg);
            } else if (cls.isInterface()) {
                scanInterface(cls, pkg);
            } else if (cls.isEnum()) {
                scanEnum(cls, pkg);
            } else {
                scanClss(cls, pkg);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Erreur lors du scan de la classe: " + e.getMessage());
        }
    }
    
    private void scanClss(Class<?> cls, PackageModel pkg) {
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
    
    private void scanInterface(Class<?> cls, PackageModel pkg) {
        InterfaceInfo interfaceInfo = new InterfaceInfo(cls.getSimpleName());
        pkg.addInterface(interfaceInfo);
    }
    
    private void scanEnum(Class<?> cls, PackageModel pkg) {
        EnumInfo enumInfo = new EnumInfo(cls.getSimpleName());
        for (Object constant : cls.getEnumConstants()) {
            enumInfo.getConstants().add(constant.toString());
        }
        pkg.addEnum(enumInfo);
    }
    
    private void scanAnnotation(Class<?> cls, PackageModel pkg) {
        AnnotationInfo annotationInfo = new AnnotationInfo(cls.getSimpleName());
        pkg.addAnnotation(annotationInfo);
    }
}