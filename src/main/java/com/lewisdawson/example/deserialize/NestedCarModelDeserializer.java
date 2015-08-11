package com.lewisdawson.example.deserialize;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.lewisdawson.example.model.NestedCarModel;

import java.io.IOException;

/**
 * Created by ldawson on 8/10/15.
 */
public class NestedCarModelDeserializer extends StdDeserializer<NestedCarModel> implements ResolvableDeserializer {

    private final JsonDeserializer<?> defaultJsonDeserializer;

    private final ObjectMapper objectMapper;

    private final String[] modelTraversalPath;

    private final JsonFactory jsonFactory;

    public NestedCarModelDeserializer(JsonDeserializer<?> defaultJsonDeserializer, ObjectMapper objectMapper, String[] modelTraversalPath) {
        super(NestedCarModel.class);

        this.defaultJsonDeserializer = defaultJsonDeserializer;
        this.objectMapper = objectMapper;
        this.modelTraversalPath = modelTraversalPath;
        this.jsonFactory = new JsonFactory();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NestedCarModel deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        // Iterate down the json tree until we get to the content portion...the portion that maps to the NestedCarModel
        for(String nodePath : modelTraversalPath) {
            node = node.findValue(nodePath);
            if(node == null) {
                throw new IllegalStateException("Unexpected json traversal path format!");
            }
        }
        jsonParser.close();

        ObjectMapper om = new ObjectMapper();
        String treeString = om.writeValueAsString(om.treeToValue(node, Object.class));
        JsonParser newParser = jsonFactory.createParser(treeString);
        // Queue the first token for the deserialization
        newParser.nextToken();

        NestedCarModel model = (NestedCarModel) defaultJsonDeserializer.deserialize(newParser, deserializationContext);

        return model;
    }

    /**
     * {@inheritDoc}
     */
    public void resolve(DeserializationContext deserializationContext) throws JsonMappingException {
        ((ResolvableDeserializer) defaultJsonDeserializer).resolve(deserializationContext);
    }
}
