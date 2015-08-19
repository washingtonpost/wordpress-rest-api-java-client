package com.washingtonpost.wordpress.rest.api.transformers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.washingtonpost.wordpress.rest.api.model.WaPoPost;
import java.io.IOException;
import java.util.List;

/**
 * <p>A transformer to turn JSON in to a WaPoPost</p>
 */
public class WaPoTransformer extends AbstractTransformer<WaPoPost> {

    @Override
    public List<WaPoPost> transform(String json) throws IOException {
        return super.objectMapper.readValue(json, new TypeReference<List<WaPoPost>>() {});
    }
}
