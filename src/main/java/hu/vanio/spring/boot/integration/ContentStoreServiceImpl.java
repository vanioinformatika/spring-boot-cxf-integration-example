package hu.vanio.spring.boot.integration;

import java.io.File;
import java.io.IOException;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import hu.vanio.springwsmtom.wstypes.LoadContentRequest;
import hu.vanio.springwsmtom.wstypes.LoadContentResponse;
import hu.vanio.springwsmtom.wstypes.ObjectFactory;
import hu.vanio.springwsmtom.wstypes.StoreContentRequest;
import hu.vanio.springwsmtom.wstypes.StoreContentResponse;

/**
 * ContentStore service implementation
 * 
 * @author Gyula Szalai <gyula.szalai@vanio.hu>
 */
@Component("contentStoreService")
public class ContentStoreServiceImpl implements ContentStoreService {

    /** logger */
    private final Logger logger = LoggerFactory.getLogger(ContentStoreServiceImpl.class);
    
    @Autowired
    private ContentRepository contentRepository;
    
    @Override
    public Object handle(Object request) {
        logger.debug("Handling request of type: {}", request.getClass().getName());
        if (request instanceof StoreContentRequest) {
            return storeContent((StoreContentRequest)request);
        } else if (request instanceof LoadContentRequest) {
            return loadContent((LoadContentRequest)request);
        } else {
            logger.debug("Unhandled request type: {}", request.getClass().getName());
            throw new MessagingException("Unhandled request type: " + request.getClass().getName());
        }
    }
    
    @Override
    public LoadContentResponse loadContent(LoadContentRequest request) {
        logger.debug("LoadContentResponse received, name: {}", request.getName());
        LoadContentResponse resp = new ObjectFactory().createLoadContentResponse();
        File content = this.contentRepository.loadContent(request.getName());
        logger.debug("Content found interface repository: {}", content.getAbsolutePath());
        resp.setContent(new DataHandler(new FileDataSource(content)));
        logger.debug("Content put into response");
        return resp;
    }
    
    @Override
    public StoreContentResponse storeContent(StoreContentRequest request) {
        logger.debug("StoreContentResponse received, name: {}", request.getName());
        try {
            this.contentRepository.storeContent(request.getName(), request.getContent());
        } catch (IOException ex) {
            throw new MessagingException("Storing content failed", ex);
        }
        StoreContentResponse resp = new ObjectFactory().createStoreContentResponse();
        resp.setMessage("Content successfully stored");
        logger.debug("StoreContentResponse constructed!");
        return resp;
    }
}
