package org.mql.java.extraction.models;

import java.util.List;
import java.util.Vector;
import org.mql.java.extraction.models.PackageModel;


public class Project extends JavaElement {
    private List<PackageModel> packages;
    private String rootPath;
    
    public Project() {
        this.packages = new Vector<>();
    }
    
    public Project(String name, String rootPath) {
        super(name);
        this.rootPath = rootPath;
        this.packages = new Vector<>();
    }
    
    public void addPackage(PackageModel pkg) {
        packages.add(pkg);
    }
    
    public List<PackageModel> getPackages() {
        return packages;
    }
    
    public String getRootPath() {
        return rootPath;
    }
}