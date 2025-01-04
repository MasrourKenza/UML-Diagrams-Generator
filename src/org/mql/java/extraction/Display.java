package org.mql.java.extraction;

public class Display {
    public static void displayPackageInfo(Package pkg) {
        System.out.println("\n📦 Package: " + pkg.getName());
        System.out.println("├── Classes: " + pkg.getClasses().size());
        System.out.println("├── Interfaces: " + pkg.getInterfaces().size());
        System.out.println("├── Enums: " + pkg.getEnums().size());
        System.out.println("└── Annotations: " + pkg.getAnnotations().size());

        for (ClassInfo cls : pkg.getClasses()) {
            displayClassInfo(cls);
        }

        for (InterfaceInfo intf : pkg.getInterfaces()) {
            displayInterfaceInfo(intf);
        }

        for (EnumInfo enum_ : pkg.getEnums()) {
            displayEnumInfo(enum_);
        }

        for (AnnotationInfo annot : pkg.getAnnotations()) {
            displayAnnotationInfo(annot);
        }
    }

    public static void displayClassInfo(ClassInfo cls) {
        System.out.println("\n  🔷 Classe: " + cls.getName());
        System.out.println("  ├── Visibilité: " + cls.visibility);
        System.out.println("  ├── Statique: " + (cls.isStatic ? "oui" : "non"));
        System.out.println("  ├── Final: " + (cls.isFinal ? "oui" : "non"));

        if (cls.getSuperclass() != null) {
            System.out.println("  ├── Superclasse: " + cls.getSuperclass().getSimpleName());
        }

        if (!cls.getInterfaces().isEmpty()) {
            System.out.println("  ├── Interfaces implémentées:");
            for (Class<?> intf : cls.getInterfaces()) {
                System.out.println("  │   └── " + intf.getSimpleName());
            }
        }

        System.out.println("  ├── Field:");
        for (FieldInfo field : cls.getFields()) {
            displayFieldInfo(field);
        }

        System.out.println("  └── Méthodes:");
        for (MethodInfo method : cls.getMethods()) {
            displayMethodInfo(method);
        }
    }

    public static void displayFieldInfo(FieldInfo field) {
    	String fields = "  │   ├── "
                + (field.visibility != null ? field.visibility + " " : "")
                + (field.isStatic ? "static " : "")
                + (field.isFinal ? "final " : "")
                + field.getType().getSimpleName() + " "
                + field.getName();
        if (field.getValue() != null) {
        	fields += " = " + field.getValue();
        }
        System.out.println(fields);
    }

    public static void displayMethodInfo(MethodInfo method) {
    	  String mtd = "  │   └── "
                  + (method.visibility != null ? method.visibility + " " : "")
                  + (method.isStatic ? "static " : "")
                  + (method.returnType != null ? method.returnType.getSimpleName() + " " : "void ")
                  + method.getName() + "(";

          if (method.parameterTypes != null) {
              for (int i = 0; i < method.parameterTypes.size(); i++) {
                  if (i > 0) mtd += ", ";
                  mtd += method.parameterTypes.get(i).getSimpleName();
              }
          }
          mtd += ")";
          System.out.println(mtd);
      }

    public static void displayInterfaceInfo(InterfaceInfo intf) {
        System.out.println("\n  🔶 Interface: " + intf.getName());
        if (!intf.getExtendedInterfaces().isEmpty()) {
            System.out.println("  ├── Interfaces étendues:");
            for (Class<?> extIntf : intf.getExtendedInterfaces()) {
                System.out.println("  │   └── " + extIntf.getSimpleName());
            }
        }

        System.out.println("  └── Méthodes:");
        for (MethodInfo method : intf.getMethods()) {
            displayMethodInfo(method);
        }
    }

    public static void displayEnumInfo(EnumInfo enum_) {
        System.out.println("\n  🔸 Enum: " + enum_.getName());
        System.out.println("  ├── Constants:");
        for (String constant : enum_.getConstants()) {
            System.out.println("  │   └── " + constant);
        }

        if (!enum_.getMethods().isEmpty()) {
            System.out.println("  └── Méthodes:");
            for (MethodInfo method : enum_.getMethods()) {
                displayMethodInfo(method);
            }
        }
    }

    public static void displayAnnotationInfo(AnnotationInfo annot) {
        System.out.println("\n  🔹 Annotation: " + annot.getName());
        if (!annot.getElements().isEmpty()) {
            System.out.println("  └── Éléments:");
            for (MethodInfo element : annot.getElements()) {
                displayMethodInfo(element);
            }
        }
    }
}
