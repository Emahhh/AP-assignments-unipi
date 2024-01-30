/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class XMLSerializer {

    /**
     *For each object in the array arr, the method should introspect its class searching for information
    * (provided using annotations) to serialize the object. The output must be an XML file called
    * fileName.xml containing one element for each object in arr. Each element must have as main
    * tag the name of the class of the corresponding object.
    */
    public static void serialize(Object [ ] arr, String fileName) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");

            for (Object obj : arr) {
                // I check if the object has the `@XMLable` annotation.
                Class cls = obj.getClass();

                // if the annotation is not present, I print `<notXMLable />` and skip to the next object
                if (!cls.isAnnotationPresent(XMLable.class)) {
                    writer.write("<notXMLable />\n");
                    continue;
                }

                // otherwise, I print `<className>fields</className>` (the fields are printed in another method)
                writer.write("<" + cls.getName() + ">\n");
                serializeFields(obj, writer);
                writer.write("</" + cls.getName() + ">\n");


                
            }
            System.out.println("File written successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        
    }

    static private void serializeFields(Object obj, BufferedWriter writer) throws IOException, IllegalAccessException {

        Class cls = obj.getClass();

        // itero tutti i metodi dell'oggetto
        for (java.lang.reflect.Field field : cls.getDeclaredFields()) {

            // if the method dosen't have `@XMLfield`, I skip it
            if (!field.isAnnotationPresent(XMLfield.class)) {
                continue;
            }

            field.setAccessible(true);

            // I get the name of the field
            String fieldName = field.getName();

            // I get the `type` argument of the annotation
            String type = field.getAnnotation(XMLfield.class).type();

            // I get the `name` optional argument of the annotation
            String annotationName = field.getAnnotation(XMLfield.class).name();

            // if the name is not empty, I use the field name
            if (!annotationName.isEmpty()) {
                fieldName = annotationName;
            }

            Object fieldValue = field.get(obj);
			String value = fieldValue == null ? "" : fieldValue.toString();

            // I finally print `<fieldName>fieldValue</fieldName>`
            writer.write("\t<" + fieldName + ">" + fieldValue + "</" + fieldName + ">\n");

            field.setAccessible(false);

        }
    }
    
}
