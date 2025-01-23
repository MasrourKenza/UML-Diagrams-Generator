package org.mql.java.main;

import java.io.File;

import org.mql.java.extraction.ClassInfo;
import org.mql.java.extraction.EnumInfo;
import org.mql.java.extraction.FieldInfo;
import org.mql.java.extraction.InterfaceInfo;
import org.mql.java.extraction.MethodInfo;
import org.mql.java.extraction.Project;
import org.mql.java.extraction.ProjectScanner;
import org.mql.java.xml.XMLPersistence;

public class XMLMain {

	public XMLMain() {
	}
	
	
	    public static void main(String[] args) {
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
	}
  

