package org.mql.java.relations;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ClassScanner {
    private String projectPath;
    private URLClassLoader classLoader;
    private List<Class<?>> discoveredClasses;

    public ClassScanner(String projectPath) {
        this.projectPath = projectPath;
        this.discoveredClasses = new Vector<>();
        initializeClassLoader();
    }

    private void initializeClassLoader() {
        try {
            URL url = new File(projectPath).toURI().toURL();
            this.classLoader = new URLClassLoader(new URL[]{url});
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Class<?>> scanClasses() {
        discoveredClasses.clear();
        scanDirectory(new File(projectPath), "");
        return discoveredClasses;
    }

    private void scanDirectory(File directory, String packageName) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    String newPackage = packageName.isEmpty() ? 
                        file.getName() : packageName + "." + file.getName();
                    
                    scanDirectory(file, newPackage);
                    
                } else if (file.getName().endsWith(".class")) {
                    loadClass(file, packageName);
                }
            }
        }
    }

    private void loadClass(File file, String packageName) {
        try {
            String className = file.getName().replace(".class", "");
            if (!packageName.isEmpty()) {
                className = packageName + "." + className;
            }
            
            Class<?> cls = classLoader.loadClass(className);
            discoveredClasses.add(cls);
            
        } catch (Exception e) {
            System.err.println( e.getMessage());
        }
    }

    public void close() {
        try {
            if (classLoader != null) {
                classLoader.close();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public String getProjectPath() {
        return projectPath;
    }

    public List<Class<?>> getDiscoveredClasses() {
        return new ArrayList<>(discoveredClasses);
    }
}