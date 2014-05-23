package hu.vanio.spring.boot.integration.tests;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.URLDataSource;

import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import hu.vanio.spring.boot.integration.ExampleIntegrationApplication;
import hu.vanio.spring.boot.integration.client.JaxWsClient;

/**
 * Integration test
 *
 * @author Gyula Szalai <gyula.szalai@vanio.hu>
 */
public class SampleIntegrationApplicationTest {

    /** Spring context */
    private static ConfigurableApplicationContext context;

    /** Webservice client */
    private final static JaxWsClient client = new JaxWsClient();
    
    /** Test content URL */
    static public final URL TEST_CONTENT_URL = Thread.currentThread().getContextClassLoader().getResource("spring-ws-logo.png");
    
    @BeforeClass
    public static void start() throws Exception {
        context = SpringApplication.run(ExampleIntegrationApplication.class);
    }

    @AfterClass
    public static void stop() {
        if (context != null) {
            context.close();
        }
    }

    @Test
    public void test() throws Exception {
        String message = client.storeContent("test", new DataHandler(new URLDataSource(TEST_CONTENT_URL)));
        System.out.println("Server message: " + message);
        assertEquals("Content successfully stored", message);
        
        DataHandler dh = client.loadContent("test");
        assertNotNull(dh);
        File tempFile = new File(System.getProperty("java.io.tmpdir"), "spring_mtom_jaxws_tmp.bin");
        tempFile.deleteOnExit();
        long size = saveContentToFile(dh, tempFile);
        assertTrue(size > 0);
        assertTrue(tempFile.length()>0);
    }

    /**
     * Saves the specified content to the specified file
     * 
     * @param content The content
     * @param outFile The output file
     * @throws IOException If an error occurs during saving
     */
    static public long saveContentToFile(DataHandler content, File outFile) throws IOException {
        long size = 0;
        byte[] buffer = new byte[1024];
        try (InputStream is = content.getInputStream()) {
            try (OutputStream outStream = new FileOutputStream(outFile)) {
                for (int readBytes; (readBytes = is.read(buffer, 0, buffer.length)) > 0;) {
                    size += readBytes;
                    outStream.write(buffer, 0, readBytes);
                }
            }
        }
        return size;
    }
}
