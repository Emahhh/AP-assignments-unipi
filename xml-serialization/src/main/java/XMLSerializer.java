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
    void serialize(Object [ ] arr, String fileName) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            // I iterate through the objects
                // I check if the object has the `@XMLable` annotation.
                    //If not, I print `<notXMLable />`
                    // Otherwise, I print `<className>fields</className>` (the fields are printed in another method)

            
            writer.write();
            System.out.println("File written successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
}
