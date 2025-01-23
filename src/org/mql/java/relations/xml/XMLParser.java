package org.mql.java.relations.xml;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.mql.java.extraction.models.*;
import org.mql.java.extraction.models.PackageModel;
import org.mql.java.extraction.models.Project;

public class XMLParser {
    private Document document;

    public XMLParser(String xmlPath) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        this.document = builder.parse(new File(xmlPath));
    }

    public Project parseProject() {
        Element rootElement = document.getDocumentElement();
        String projectName = rootElement.getAttribute("name");
        String rootPath = rootElement.getAttribute("rootPath");
        Project project = new Project(projectName, rootPath);

        NodeList packageNodes = rootElement.getElementsByTagName("package");
        for (int i = 0; i < packageNodes.getLength(); i++) {
            Element packageElement = (Element) packageNodes.item(i);
            project.addPackage(parsePackage(packageElement));
        }

        return project;
    }

    private PackageModel parsePackage(Element packageElement) {
        String packageName = packageElement.getAttribute("name");
        PackageModel pkg = new PackageModel(packageName);

        Element classesElement = (Element) packageElement.getElementsByTagName("classes").item(0);
        if (classesElement != null) {
            NodeList classNodes = classesElement.getElementsByTagName("class");
            for (int i = 0; i < classNodes.getLength(); i++) {
                Element classElement = (Element) classNodes.item(i);
                pkg.addClass(parseClass(classElement));
            }
        }

        Element interfacesElement = (Element) packageElement.getElementsByTagName("interfaces").item(0);
        if (interfacesElement != null) {
            NodeList interfaceNodes = interfacesElement.getElementsByTagName("interface");
            for (int i = 0; i < interfaceNodes.getLength(); i++) {
                Element interfaceElement = (Element) interfaceNodes.item(i);
                pkg.addInterface(parseInterface(interfaceElement));
            }
        }

        Element enumsElement = (Element) packageElement.getElementsByTagName("enums").item(0);
        if (enumsElement != null) {
            NodeList enumNodes = enumsElement.getElementsByTagName("enum");
            for (int i = 0; i < enumNodes.getLength(); i++) {
                Element enumElement = (Element) enumNodes.item(i);
                pkg.addEnum(parseEnum(enumElement));
            }
        }

        Element annotationsElement = (Element) packageElement.getElementsByTagName("annotations").item(0);
        if (annotationsElement != null) {
            NodeList annotationNodes = annotationsElement.getElementsByTagName("annotation");
            for (int i = 0; i < annotationNodes.getLength(); i++) {
                Element annotationElement = (Element) annotationNodes.item(i);
                pkg.addAnnotation(parseAnnotation(annotationElement));
            }
        }

        return pkg;
    }

    private ClassInfo parseClass(Element classElement) {
        String className = classElement.getAttribute("name");
        ClassInfo classInfo = new ClassInfo(className);
        parseJavaElement(classElement, classInfo);

        Element fieldsElement = (Element) classElement.getElementsByTagName("fields").item(0);
        if (fieldsElement != null) {
            NodeList fieldNodes = fieldsElement.getElementsByTagName("field");
            for (int i = 0; i < fieldNodes.getLength(); i++) {
                Element fieldElement = (Element) fieldNodes.item(i);
                classInfo.addField(parseField(fieldElement));
            }
        }

        Element methodsElement = (Element) classElement.getElementsByTagName("methods").item(0);
        if (methodsElement != null) {
            NodeList methodNodes = methodsElement.getElementsByTagName("method");
            for (int i = 0; i < methodNodes.getLength(); i++) {
                Element methodElement = (Element) methodNodes.item(i);
                classInfo.addMethod(parseMethod(methodElement));
            }
        }

        return classInfo;
    }

    private InterfaceInfo parseInterface(Element interfaceElement) {
        String interfaceName = interfaceElement.getAttribute("name");
        InterfaceInfo interfaceInfo = new InterfaceInfo(interfaceName);
        parseJavaElement(interfaceElement, interfaceInfo);

        Element methodsElement = (Element) interfaceElement.getElementsByTagName("methods").item(0);
        if (methodsElement != null) {
            NodeList methodNodes = methodsElement.getElementsByTagName("method");
            for (int i = 0; i < methodNodes.getLength(); i++) {
                Element methodElement = (Element) methodNodes.item(i);
                interfaceInfo.addMethod(parseMethod(methodElement));
            }
        }

        return interfaceInfo;
    }

    private EnumInfo parseEnum(Element enumElement) {
        String enumName = enumElement.getAttribute("name");
        EnumInfo enumInfo = new EnumInfo(enumName);
        parseJavaElement(enumElement, enumInfo);

        Element constantsElement = (Element) enumElement.getElementsByTagName("constants").item(0);
        if (constantsElement != null) {
            NodeList constantNodes = constantsElement.getElementsByTagName("constant");
            for (int i = 0; i < constantNodes.getLength(); i++) {
                Element constantElement = (Element) constantNodes.item(i);
                enumInfo.addConstant(constantElement.getTextContent());
            }
        }

        return enumInfo;
    }

    private AnnotationInfo parseAnnotation(Element annotationElement) {
        String annotationName = annotationElement.getAttribute("name");
        AnnotationInfo annotationInfo = new AnnotationInfo(annotationName);
        parseJavaElement(annotationElement, annotationInfo);

        Element elementsElement = (Element) annotationElement.getElementsByTagName("elements").item(0);
        if (elementsElement != null) {
            NodeList elementNodes = elementsElement.getElementsByTagName("method");
            for (int i = 0; i < elementNodes.getLength(); i++) {
                Element elementElement = (Element) elementNodes.item(i);
                annotationInfo.addElement(parseMethod(elementElement));
            }
        }

        return annotationInfo;
    }

    private FieldInfo parseField(Element fieldElement) {
        String fieldName = fieldElement.getAttribute("name");
        String fieldTypeName = fieldElement.getAttribute("type");
        Class<?> fieldType = null;
        try {
            fieldType = Class.forName(fieldTypeName);
        } catch (ClassNotFoundException e) {
            fieldType = Object.class;
        }
        
        FieldInfo fieldInfo = new FieldInfo(fieldName, fieldType);
        parseJavaElement(fieldElement, fieldInfo);

        String value = fieldElement.getAttribute("value");
        if (!value.isEmpty()) {
            fieldInfo.setValue(value);
        }

        return fieldInfo;
    }

    private MethodInfo parseMethod(Element methodElement) {
        String methodName = methodElement.getAttribute("name");
        MethodInfo methodInfo = new MethodInfo(methodName);
        parseJavaElement(methodElement, methodInfo);

        String returnTypeName = methodElement.getAttribute("returnType");
        if (!returnTypeName.isEmpty()) {
            try {
                methodInfo.returnType = Class.forName(returnTypeName);
            }catch (Exception e) {
                System.err.println("Erreur : " + e.getMessage());
                e.printStackTrace();
        }}

        return methodInfo;
    }

    private void parseJavaElement(Element element, JavaElement javaElement) {
        String visibility = element.getAttribute("visibility");
        if (!visibility.isEmpty()) {
            javaElement.setVisibility(visibility);
        }

        String isStatic = element.getAttribute("isStatic");
        if (!isStatic.isEmpty()) {
            javaElement.setStatic(Boolean.parseBoolean(isStatic));
        }

        String isFinal = element.getAttribute("isFinal");
        if (!isFinal.isEmpty()) {
            javaElement.setFinal(Boolean.parseBoolean(isFinal));
        }
    }
}