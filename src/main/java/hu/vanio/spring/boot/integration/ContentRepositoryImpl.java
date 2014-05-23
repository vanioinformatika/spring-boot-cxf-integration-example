package hu.vanio.spring.boot.integration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Content repository implementation 
 * 
 * @author Gyula Szalai <gyula.szalai@vanio.hu>
 */
@Component("contentRepository")
public class ContentRepositoryImpl implements ContentRepository{

    /** Logger */
    final static Logger logger = LoggerFactory.getLogger(ContentRepositoryImpl.class);
    
    /** Path to the filestore */
    @Value(value = "#{ systemProperties['java.io.tmpdir'] }")
    private String fileStorePath;
    
    @Override
    public File loadContent(String name) {
        return new File(this.fileStorePath, name + ".tmp");
    }

    @Override
    public void storeContent(String name, DataHandler content) throws IOException {
        File outFile = new File(this.fileStorePath, name + ".tmp");
        logger.info("Storing content in file: {}", outFile.getAbsolutePath());
        InputStream dhis = content.getInputStream();
        logger.info("******************************************** dhis: {}", dhis.getClass().getName());
        int i = 0;
        byte[] buffer = new byte[1024];
        try (InputStream is = content.getInputStream()) {
            try (OutputStream outStream = new FileOutputStream(outFile)) {
                while ((i = is.read(buffer, 0, buffer.length)) > 0) {
                    outStream.write(buffer, 0, i);
                }
            }
        }
        logger.info("Content stored.");
        
    }

    /**
     * Path to the filestore
     * @return the fileStorePath
     */
    public String getFileStorePath() {
        return fileStorePath;
    }

    /**
     * Path to the filestore
     * @param fileStorePath the fileStorePath to set
     */
    public void setFileStorePath(String fileStorePath) {
        this.fileStorePath = fileStorePath;
    }
    
    
}
