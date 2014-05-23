package hu.vanio.spring.boot.integration;

import java.io.File;
import java.io.IOException;

import javax.activation.DataHandler;

/**
 * Content repository interface
 * 
 * @author Gyula Szalai <gyula.szalai@vanio.hu>
 */
public interface ContentRepository {
    
    /**
     * Loads the specified content
     * @param name The name of the content
     * @return A file that stores the content
     */
    File loadContent(String name);
    
    /**
     * Stores the specified content
     * @param name The name of the content
     * @param content The content
     * @throws java.io.IOException If an error occurs during storing the content
     */
    void storeContent(String name, DataHandler content) throws IOException;
    
}
