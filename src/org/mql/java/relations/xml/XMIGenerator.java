package org.mql.java.relations.xml;

import org.mql.java.extraction.models.*;
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMIGenerator {
    private Project project;
    private Document document;

    public XMIGenerator(Project project) throws ParserConfigurationException {
        this.project = project;
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        document = docBuilder.newDocument();
    }

    public void generateXMI(String outputPath) throws TransformerException, IOException {
        Element rootElement = document.createElement("xmi:XMI");
        rootElement.setAttribute("xmlns:xmi", "http://www.omg.org/XMI");
        rootElement.setAttribute("xmlns:uml", "http://www.omg.org/spec/UML/20110701");
        document.appendChild(rootElement);

        Element modelElement = document.createElement("uml:Model");
        modelElement.setAttribute("name", project.getName());
        rootElement.appendChild(modelElement);

        for (PackageModel pkg : project.getPackages()) {
            Element packageElement = document.createElement("packagedElement");
            packageElement.setAttribute("xmi:type", "uml:Package");
            packageElement.setAttribute("name", pkg.getName());
            modelElement.appendChild(packageElement);

            processPackageElements(pkg, packageElement);
        }

        transformXMI(outputPath);
    }

    private void processPackageElements(PackageModel pkg, Element packageElement) {
        for (ClassInfo classInfo : pkg.getClasses()) {
            Element classElement = document.createElement("Classes");
            classElement.setAttribute("xmi:type", "uml:Class");
            classElement.setAttribute("name", classInfo.getName());
            
            if (classInfo.visibility != null) {
                classElement.setAttribute("visibility", classInfo.visibility);
            }
            
            classElement.setAttribute("isStatic", String.valueOf(classInfo.isStatic));
            classElement.setAttribute("isFinal", String.valueOf(classInfo.isFinal));

            Element attributesElement = document.createElement("Attributs");
            for (FieldInfo field : classInfo.getFields()) {
                Element attributeElement = document.createElement("Attributs");
                attributeElement.setAttribute("name", field.getName());
                attributeElement.setAttribute("type", field.getType().getSimpleName());
                
                if (field.visibility != null) {
                    attributeElement.setAttribute("visibility", field.visibility);
                }
                
                attributeElement.setAttribute("isStatic", String.valueOf(field.isStatic));
                attributeElement.setAttribute("isFinal", String.valueOf(field.isFinal));
                
                attributesElement.appendChild(attributeElement);
            }
            classElement.appendChild(attributesElement);

            Element methodsElement = document.createElement("Méthodes");
            for (MethodInfo method : classInfo.getMethods()) {
                Element methodElement = document.createElement("Méthodes");
                methodElement.setAttribute("name", method.getName());
                
                if (method.visibility != null) {
                    methodElement.setAttribute("visibility", method.visibility);
                }
                
                methodElement.setAttribute("isStatic", String.valueOf(method.isStatic));
                methodElement.setAttribute("isFinal", String.valueOf(method.isFinal));
                
                if (method.returnType != null) {
                    Element returnElement = document.createElement("Parameter");
                    returnElement.setAttribute("direction", "return");
                    returnElement.setAttribute("type", method.returnType.getSimpleName());
                    methodElement.appendChild(returnElement);
                }
                
                methodsElement.appendChild(methodElement);
            }
            classElement.appendChild(methodsElement);

            packageElement.appendChild(classElement);
        }

        for (InterfaceInfo interfaceInfo : pkg.getInterfaces()) {
            Element interfaceElement = document.createElement("Interfaces");
            interfaceElement.setAttribute("xmi:type", "uml:Interface");
            interfaceElement.setAttribute("name", interfaceInfo.getName());
            packageElement.appendChild(interfaceElement);
        }

        for (EnumInfo enumInfo : pkg.getEnums()) {
            Element enumElement = document.createElement("Enums");
            enumElement.setAttribute("xmi:type", "uml:Enumeration");
            enumElement.setAttribute("name", enumInfo.getName());
            packageElement.appendChild(enumElement);
        }
    }

    private void transformXMI(String outputPath) throws TransformerException, IOException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new FileWriter(outputPath));

        transformer.transform(source, result);
    }

    
    
}