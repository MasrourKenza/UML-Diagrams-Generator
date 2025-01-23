package org.mql.java.examples;

import java.io.File;

import org.mql.java.extraction.ProjectScanner;
import org.mql.java.extraction.models.AnnotationInfo;
import org.mql.java.extraction.models.ClassInfo;
import org.mql.java.extraction.models.EnumInfo;
import org.mql.java.extraction.models.FieldInfo;
import org.mql.java.extraction.models.InterfaceInfo;
import org.mql.java.extraction.models.JavaElement;
import org.mql.java.extraction.models.MethodInfo;
import org.mql.java.extraction.models.PackageModel;
import org.mql.java.extraction.models.Project;
import org.mql.java.xml.XMIGenerator;
import org.mql.java.xml.XMLParser;
import org.mql.java.xml.XMLPersistence;

public class XMLExemples {

	public XMLExemples() {
		exp01_3();
		//exp01_4();
		//exp01_6();
	}
	
	void exp01_3() {
		   try {
	            Project project = createTestProject();
	            
	            XMLPersistence persistence = new XMLPersistence();
	            persistence.persistProject(project, "resources/project_structure.xml");
	            System.out.println("L'ensemble de la structure de données a bien persisté dans le fichier XML : project_structure.xml");
	        } catch (Exception e) {
	            System.err.println("Error: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }
	    
	    private static Project createTestProject() {
	    	  String projectPath = new File("").getAbsolutePath() + "/bin";
	    	  ProjectScanner projectScanner = new ProjectScanner("Masrour Kenza - UML Diagrams Generator", projectPath);
	          Project project = projectScanner.scanProject();
	        return project;
	    
	}
	    
	    
	    void exp01_4() {
	    	   try {
	               XMLParser parser = new XMLParser("resources/project_structure.xml");
	               Project project = parser.parseProject();
	               
	               XMIGenerator generator = new XMIGenerator(project);
	               generator.generateXMI("resources/project_structure.xmi");
	               
	               System.out.println("Fichier XMI généré avec succès.");
	           } catch (Exception e) {
	               e.printStackTrace();
	           }
	    }
	    
	    void exp01_6() {
	        try {
	            String xmlPath = "resources/project_structure.xml"; 
	            XMLParser parser = new XMLParser(xmlPath);
	            org.mql.java.extraction.models.Project project = parser.parseProject();

	            System.out.println("Projet : " + project.getName());
	            System.out.println("Chemin racine : " + project.getRootPath());
	            System.out.println("Nombre de packages : " + project.getPackages().size());

	            for (PackageModel pkg : project.getPackages()) {
	                System.out.println("\n--- Package : " + pkg.getName() + " ---");
	                
	                System.out.println("Classes :");
	                for (ClassInfo classInfo : pkg.getClasses()) {
	                    System.out.println("  - " + classInfo.getName());
	                    displayAttributes(classInfo);
	                    
	                    System.out.println("    Champs :");
	                    for (FieldInfo field : classInfo.getFields()) {
	                        System.out.println("      * " + field.getName() + " : " + field.getType().getSimpleName());
	                    }
	                    
	                    System.out.println("    Méthodes :");
	                    for (MethodInfo method : classInfo.getMethods()) {
	                        System.out.println("      * " + method.getName());
	                    }
	                }

	                System.out.println("Interfaces :");
	                for (InterfaceInfo interfaceInfo : pkg.getInterfaces()) {
	                    System.out.println("  - " + interfaceInfo.getName());
	                    displayAttributes(interfaceInfo);
	                }

	                System.out.println("Enums :");
	                for (EnumInfo enumInfo : pkg.getEnums()) {
	                    System.out.println("  - " + enumInfo.getName());
	                    
	                }

	                System.out.println("Annotations :");
	                for (AnnotationInfo annotationInfo : pkg.getAnnotations()) {
	                    System.out.println("  - " + annotationInfo.getName());
	                   
	                }
	            }

	        } catch (Exception e) {
	            System.err.println("Erreur lors du parsing du fichier XML : " + e.getMessage());
	            e.printStackTrace();
	        }
	    }

	    private static void displayAttributes(JavaElement element) {
	        System.out.println("    Attributs :");
	        System.out.println("      * Nom : " + element.getName());
	        System.out.println("      * Statique : " + element.isStatic);
	        System.out.println("      * Final : " + element.isFinal);
	    
	    }
	    
	    

	    public static void main(String[] args) {
			new XMLExemples();
		}
}
