package org.mql.java.exemples;

import java.io.File;
import java.util.List;
import java.util.Set;
import org.mql.java.extraction.models.*;
import org.mql.java.extraction.models.PackageModel;
import org.mql.java.extraction.Display;
import org.mql.java.extraction.ProjectScanner;
import org.mql.java.relations.ClassScanner;
import org.mql.java.relations.RelationAnalyzer;
import org.mql.java.relations.model.RelationInfo;

public class Exemples {
	
	public Exemples() {
		exp01_1();
		//exp01_2();
	}
	
	void exp01_1(){
		
		 String projectPath = new File("").getAbsolutePath() + "/bin";
		 try {
            System.out.println("\n=== PARTIE 1: STRUCTURE DU PROJET ===\n");
            
            ProjectScanner projectScanner = new ProjectScanner("Masrour Kenza - UML Diagrams Generator", projectPath);
            Project project = projectScanner.scanProject();

            System.out.println("=== Analyse du projet: " + project.getName() + " ===");
            System.out.println("Chemin racine: " + project.getRootPath());

            for (PackageModel pkg : project.getPackages()) {
            	if (!pkg.getName().startsWith("org.mql.java.")) {
                    continue; 
                }
                System.out.println("\nPackage: " + pkg.getName());
            }

            for (PackageModel pkg : project.getPackages()) {
                Display.displayPackageInfo(pkg);
            }
            
	 } catch (Exception e) {
         System.err.println("Erreur lors de l'analyse : " + e.getMessage());
         e.printStackTrace();
     }
	}
	
	void exp01_2() {
		  String projectPath = new File("").getAbsolutePath() + "/bin";
          
	        try {
	           

	            System.out.println("\n=== PARTIE 2: ANALYSE DES RELATIONS ===\n");
	            
	            ClassScanner classScanner = new ClassScanner(projectPath);
	            List<Class<?>> projectClasses = classScanner.scanClasses();
	            RelationAnalyzer relationAnalyz = new RelationAnalyzer();

	            for (Class<?> cls : projectClasses) {
	                Set<RelationInfo> relations = relationAnalyz.analyzClass(cls);
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
	
    public static void main(String[] args) {
    	new Exemples();
    }
}