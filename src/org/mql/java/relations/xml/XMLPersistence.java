package org.mql.java.relations.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.mql.java.extraction.models.*;
import org.mql.java.extraction.models.PackageModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;

public class XMLPersistence {
    private final Document document;
    
    public XMLPersistence() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        this.document = builder.newDocument();
    }
    
    public void persistProject(Project project, String filePath) throws Exception {
        Element rootElement = document.createElement("project");
        rootElement.setAttribute("name", project.getName());
        rootElement.setAttribute("rootPath", project.getRootPath());
        document.appendChild(rootElement);
        
        Element pkgElement = document.createElement("packages");
        rootElement.appendChild(pkgElement);
        
        for (PackageModel pkg : project.getPackages()) {
            persistPackage(pkg, pkgElement);
        }
        
        saveDocument(filePath);
    }
    
    private void persistPackage(PackageModel pkg, Element parentElement) {
        Element packgElement = document.createElement("package");
        packgElement.setAttribute("name", pkg.getName());
        parentElement.appendChild(packgElement);
        
        Element clssElement = document.createElement("classes");
        packgElement.appendChild(clssElement);
        for (ClassInfo cls : pkg.getClasses()) {
            persistClass(cls, clssElement);
        }
        
        Element interfElement = document.createElement("interfaces");
        packgElement.appendChild(interfElement);
        for (InterfaceInfo iface : pkg.getInterfaces()) {
            persistInterface(iface, interfElement);
        }
        
        Element enumElement = document.createElement("enums");
        packgElement.appendChild(enumElement);
        for (EnumInfo enumInfo : pkg.getEnums()) {
            persistEnum(enumInfo, enumElement);
        }
        
        Element annotationElement = document.createElement("annotations");
        packgElement.appendChild(annotationElement);
        for (AnnotationInfo anno : pkg.getAnnotations()) {
            persistAnnotation(anno, annotationElement);
        }
    }
    
    private void persistClass(ClassInfo cls, Element parentElement) {
        Element classElement = document.createElement("class");
        classElement.setAttribute("name", cls.getName());
        persistJavaElement(cls, classElement);
        
        if (cls.getSuperclass() != null) {
            classElement.setAttribute("superclass", cls.getSuperclass().getName());
        }
        
        Element interfacesElement = document.createElement("implements");
        for (Class<?> interfac : cls.getInterfaces()) {
            Element interfElm = document.createElement("interface");
            interfElm.setAttribute("name", interfac.getName());
            interfacesElement.appendChild(interfElm);
        }
        classElement.appendChild(interfacesElement);
        
        Element fieldsElement = document.createElement("fields");
        for (FieldInfo field : cls.getFields()) {
            persistField(field, fieldsElement);
        }
        classElement.appendChild(fieldsElement);
        
        Element methodsElement = document.createElement("methods");
        for (MethodInfo method : cls.getMethods()) {
            persistMethod(method, methodsElement);
        }
        classElement.appendChild(methodsElement);
        
        parentElement.appendChild(classElement);
    }
    
    private void persistInterface(InterfaceInfo iface, Element parentElement) {
        Element ifaceElement = document.createElement("interface");
        ifaceElement.setAttribute("name", iface.getName());
        persistJavaElement(iface, ifaceElement);
        
        Element extendedElement = document.createElement("extends");
        for (Class<?> extended : iface.getExtendedInterfaces()) {
            Element extElement = document.createElement("interface");
            extElement.setAttribute("name", extended.getName());
            extendedElement.appendChild(extElement);
        }
        ifaceElement.appendChild(extendedElement);
        
        Element methodsElement = document.createElement("methods");
        for (MethodInfo method : iface.getMethods()) {
            persistMethod(method, methodsElement);
        }
        ifaceElement.appendChild(methodsElement);
        
        parentElement.appendChild(ifaceElement);
    }
    
    private void persistEnum(EnumInfo enumInfo, Element parentElement) {
        Element enumElement = document.createElement("enum");
        enumElement.setAttribute("name", enumInfo.getName());
        persistJavaElement(enumInfo, enumElement);
        
        Element constantsElement = document.createElement("constants");
        for (String constant : enumInfo.getConstants()) {
            Element constElement = document.createElement("constant");
            constElement.setTextContent(constant);
            constantsElement.appendChild(constElement);
        }
        enumElement.appendChild(constantsElement);
        
        Element methodsElement = document.createElement("methods");
        for (MethodInfo method : enumInfo.getMethods()) {
            persistMethod(method, methodsElement);
        }
        enumElement.appendChild(methodsElement);
        
        parentElement.appendChild(enumElement);
    }
    
    private void persistAnnotation(AnnotationInfo anno, Element parentElement) {
        Element annoElement = document.createElement("annotation");
        annoElement.setAttribute("name", anno.getName());
        persistJavaElement(anno, annoElement);
        
        Element elementsElement = document.createElement("elements");
        for (MethodInfo element : anno.getElements()) {
            persistMethod(element, elementsElement);
        }
        annoElement.appendChild(elementsElement);
        
        parentElement.appendChild(annoElement);
    }
    
    private void persistField(FieldInfo field, Element parentElement) {
        Element fieldElement = document.createElement("field");
        fieldElement.setAttribute("name", field.getName());
        persistJavaElement(field, fieldElement);
        
        fieldElement.setAttribute("type", field.getType().getName());
        if (field.getValue() != null) {
            fieldElement.setAttribute("value", field.getValue().toString());
        }
        
        parentElement.appendChild(fieldElement);
    }
    
    private void persistMethod(MethodInfo method, Element parentElement) {
        Element methodElement = document.createElement("method");
        methodElement.setAttribute("name", method.getName());
        persistJavaElement(method, methodElement);
        
        if (method.returnType != null) {
            methodElement.setAttribute("returnType", method.returnType.getName());
        }
        
        Element paramsElement = document.createElement("parameters");
        for (Class<?> paramType : method.parameterTypes) {
            Element paramElement = document.createElement("parameter");
            paramElement.setAttribute("type", paramType.getName());
            paramsElement.appendChild(paramElement);
        }
        methodElement.appendChild(paramsElement);
        
        parentElement.appendChild(methodElement);
    }
    
    private void persistJavaElement(JavaElement element, Element xmlElement) {
        if (element.visibility != null) {
            xmlElement.setAttribute("visibility", element.visibility);
        }
        xmlElement.setAttribute("isStatic", String.valueOf(element.isStatic));
        xmlElement.setAttribute("isFinal", String.valueOf(element.isFinal));
    }
    
    private void saveDocument(String filePath) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(filePath));
        transformer.transform(source, result);
    }
}