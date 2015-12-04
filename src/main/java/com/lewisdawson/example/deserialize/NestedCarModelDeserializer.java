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
 * A custom deserializer used to deserialize json to a {@link NestedCarModel} object.
 *
 * @author Lewis Dawson <lew.dawson@lewdawson.com>
 */
public class NestedCarModelDeserializer extends StdDeserializer<NestedCarModel> implements ResolvableDeserializer {

    /**
     * The default {@link JsonDeserializer}, created by Jackson, that we will delegate to for deserialization of the json.
     */
    private final JsonDeserializer<?> defaultJsonDeserializer;

    /**
     * The {@link ObjectMapper} used to create tree data.
     */
    private final ObjectMapper objectMapper;

    /**
     * The json path to the data that maps to the {@link NestedCarModel} object.
     */
    private final String[] modelTraversalPath;

    /**
     * The {@link JsonFactory} used to create {@link JsonParser} instances.
     */
    private final JsonFactory jsonFactory;

    /**
     *
     * @param defaultJsonDeserializer
     *          The {@link JsonDeserializer} that will be used to delegate deserialization to
     * @param objectMapper
     *          Used to create tree data
     * @param modelTraversalPath
     *          The json path to the data that maps to the {@link NestedCarModel} object.
     */
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
        // Close the original parser since we don't need it anymore
        jsonParser.close();

        String treeString = objectMapper.writeValueAsString(objectMapper.treeToValue(node, Object.class));
        // Create a new parser where the root of the json is the NestedCarModel portion
        JsonParser newParser = jsonFactory.createParser(treeString);
        // Queue the first token for the deserialization (NOTE: this is important--you get a NullPointerException without it)
        newParser.nextToken();

        return (NestedCarModel) defaultJsonDeserializer.deserialize(newParser, deserializationContext);
    }

    /**
     * {@inheritDoc}
     */
    public void resolve(DeserializationContext deserializationContext) throws JsonMappingException {
        ((ResolvableDeserializer) defaultJsonDeserializer).resolve(deserializationContext);
    }
}
