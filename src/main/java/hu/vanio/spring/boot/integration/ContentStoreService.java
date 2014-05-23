package hu.vanio.spring.boot.integration;

import hu.vanio.springwsmtom.wstypes.LoadContentRequest;
import hu.vanio.springwsmtom.wstypes.LoadContentResponse;
import hu.vanio.springwsmtom.wstypes.StoreContentRequest;
import hu.vanio.springwsmtom.wstypes.StoreContentResponse;

/**
 * ContentStore service interface.
 *
 * @author Gyula Szalai <gyula.szalai@vanio.hu>
 */
public interface ContentStoreService {

    /**
     * Handles the specified request. Entry point for service activator.
     * @param request The request to be handled
     * @return The response
     */
    public Object handle(Object request);

    /**
     * Handles the specified StoreContentRequest
     * @param storeContentRequest The request
     * @return The StoreContentResponse
     */
    public StoreContentResponse storeContent(StoreContentRequest storeContentRequest);

    /**
     * Handles the specified LoadContentRequest
     * @param loadContentRequest The request
     * @return The LoadContentResponse
     */
    public LoadContentResponse loadContent(LoadContentRequest loadContentRequest);

}
