package org.mql.java.extraction;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        String projectPath = new File("").getAbsolutePath() + "/bin";
        ProjectScanner scanner = new ProjectScanner("Masrour Kenza - UML Diagrams Generator", projectPath);
        Project project = scanner.scanProject();

        System.out.println("=== Analyse du projet: " + project.getName() + " ===");
        System.out.println("Chemin racine: " + project.getRootPath());

        
        for (Package pkg : project.getPackages()) {
            System.out.println("**Package: " + pkg.getName());
            
            for (ClassInfo cls : pkg.getClasses()) {
                System.out.println(" ==> Classe: " + cls.getName());
                for (FieldInfo field : cls.getFields()) {
                    System.out.println("│  └─> Fileld: " + field.getName());
                }
            }
            }
        for (Package pkg : project.getPackages()) {
            Display.displayPackageInfo(pkg);
        }
    }
}
