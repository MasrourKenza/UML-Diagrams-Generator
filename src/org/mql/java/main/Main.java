package org.mql.java.main;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.mql.java.extraction.ClassInfo;
import org.mql.java.extraction.Display;
import org.mql.java.extraction.FieldInfo;
import org.mql.java.extraction.Project;
import org.mql.java.extraction.ProjectScanner;
import org.mql.java.relations.ClassScanner;
import org.mql.java.relations.RelationAnalyzer;
import org.mql.java.relations.RelationInfo;

public class Main {
    public static void main(String[] args) {
    	  String projectPath = new File("").getAbsolutePath() + "/bin";
          
        
        try {
            System.out.println("\n=== PARTIE 1: STRUCTURE DU PROJET ===\n");
            
            ProjectScanner projectScanner = new ProjectScanner("Masrour Kenza - UML Diagrams Generator", projectPath);
            Project project = projectScanner.scanProject();

            System.out.println("=== Analyse du projet: " + project.getName() + " ===");
            System.out.println("Chemin racine: " + project.getRootPath());

            for (org.mql.java.extraction.Package pkg : project.getPackages()) {
            	if (!pkg.getName().startsWith("org.mql.java.")) {
                    continue; 
                }
                System.out.println("\nPackage: " + pkg.getName());
            }

            for (org.mql.java.extraction.Package pkg : project.getPackages()) {
                Display.displayPackageInfo(pkg);
            }

            System.out.println("\n=== PARTIE 2: ANALYSE DES RELATIONS ===\n");
            
            ClassScanner classScanner = new ClassScanner(projectPath);
            List<Class<?>> projectClasses = classScanner.scanClasses();
            RelationAnalyzer relationAnalyzer = new RelationAnalyzer();

            for (Class<?> cls : projectClasses) {
                Set<RelationInfo> relations = relationAnalyzer.analyzeClass(cls);
                if (!relations.isEmpty()) {
                    System.out.println("Relations pour " + cls.getSimpleName() + ":");
                    for (RelationInfo relation : relations) {
                        System.out.println("│ └─> " + relation.toString());
                    }
                    System.out.println("----------------------------------------");
                }
            }

            classScanner.close();

        } catch (Exception e) {
            System.err.println("Erreur lors de l'analyse : " + e.getMessage());
            e.printStackTrace();
        }
    }
}