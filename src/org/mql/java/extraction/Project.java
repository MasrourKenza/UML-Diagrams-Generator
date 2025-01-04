package org.mql.java.extraction;

import java.util.ArrayList;
import java.util.List;

public class Project extends JavaElement {
    private List<Package> packages;
    private String rootPath;
    
    public Project(String name, String rootPath) {
        super(name);
        this.rootPath = rootPath;
        this.packages = new ArrayList<>();
    }
    
    public void addPackage(Package pkg) {
        packages.add(pkg);
    }
    
    public List<Package> getPackages() {
        return packages;
    }
    
    public String getRootPath() {
        return rootPath;
    }
}