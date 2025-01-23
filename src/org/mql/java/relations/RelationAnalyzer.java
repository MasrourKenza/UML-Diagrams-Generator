package org.mql.java.relations;

import java.lang.reflect.*;
import java.util.*;

import org.mql.java.relations.annotations.Aggregation;
import org.mql.java.relations.annotations.Composition;
import org.mql.java.relations.ennumerations.RelationType;
import org.mql.java.relations.model.RelationInfo;

public class RelationAnalyzer {
    private Set<RelationInfo> relationships;
    private Set<String> analyzClasses;
    
    public RelationAnalyzer() {
        this.relationships = new HashSet<>();
        this.analyzClasses = new HashSet<>();
    }
    
    public Set<RelationInfo> analyzClass(Class<?> cls) {
        if (!analyzClasses.add(cls.getName())) {
            return relationships;
        }
        
        analyzSuperclass(cls);
        
        analyzInterfaces(cls);
        
        analyzFields(cls);
        
        analyzMethods(cls);
        
        return relationships;
    }
    
    private void analyzSuperclass(Class<?> cls) {
        Class<?> superclass = cls.getSuperclass();
        if (superclass != null && !superclass.equals(Object.class)) {
            relationships.add(new RelationInfo(
                cls.getName(),
                superclass.getName(),
                RelationType.EXTENSTION
            ));
        }
    }
    
    private void analyzInterfaces(Class<?> cls) {
        for (Class<?> iface : cls.getInterfaces()) {
            relationships.add(new RelationInfo(
                cls.getName(),
                iface.getName(),
                RelationType.IMPLEMENTATION
            ));
        }
    }
    
    private void analyzFields(Class<?> cls) {
        for (Field field : cls.getDeclaredFields()) {
            if (field.isSynthetic()) continue;
            
            Class<?> fieldType = field.getType();
            RelationInfo relation = null;
            
            boolean isCollection = Collection.class.isAssignableFrom(fieldType);
            Class<?> targetType = isCollection ? getCollectionType(field) : fieldType;
            
            if (targetType != null) {
                if (hasCompositionAnnotation(field)) {
                    relation = new RelationInfo(cls.getName(), targetType.getName(), RelationType.COMPOSITION);
                } else if (hasAggregationAnnotation(field)) {
                    relation = new RelationInfo(cls.getName(), targetType.getName(), RelationType.AGGREGATION);
                } else {
                    relation = new RelationInfo(cls.getName(), targetType.getName(), RelationType.ASSOCIATION);
                }
                
                relation.setName(field.getName());
                relation.setCollection(isCollection);
                relationships.add(relation);
            }
        }
    }
    
    private void analyzMethods(Class<?> cls) {
        for (Method method : cls.getDeclaredMethods()) {
            if (method.isSynthetic()) continue;
            
            analyzType(cls, method.getReturnType());
            
            for (Class<?> paramType : method.getParameterTypes()) {
                analyzType(cls, paramType);
            }
            
            for (Class<?> exceptionType : method.getExceptionTypes()) {
                analyzType(cls, exceptionType);
            }
        }
    }
    
    private void analyzType(Class<?> cls, Class<?> type) {
        if (!type.isPrimitive() && !type.equals(String.class) && !type.equals(Object.class)) {
            relationships.add(new RelationInfo(
                cls.getName(),
                type.getName(),
                RelationType.UTILISATION
            ));
        }
    }
    
    private Class<?> getCollectionType(Field field) {
        try {
            Type genericType = field.getGenericType();
            if (genericType instanceof ParameterizedType) {
                Type[] typeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
                if (typeArguments.length > 0 && typeArguments[0] instanceof Class) {
                    return (Class<?>) typeArguments[0];
                }
            }
        } catch (Exception e) {
        }
        return null;
    }
    
    private boolean hasCompositionAnnotation(Field field) {
        return field.isAnnotationPresent(Composition.class);
    }
    
    private boolean hasAggregationAnnotation(Field field) {
        return field.isAnnotationPresent(Aggregation.class);
    }
    
    public Set<RelationInfo> getRelationships() {
        return Collections.unmodifiableSet(relationships);
    }
}