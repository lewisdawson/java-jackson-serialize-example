package com.lewisdawson.example.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.lewisdawson.example.deserialize.NestedCarModelDeserializer;
import com.lewisdawson.example.model.NestedCarModel;

import java.io.IOException;

/**
 * A service that can be instantiated to (de)serialize {@link NestedCarModel} objects to and from json.
 *
 * @author Lewis Dawson <ldawson7777@yahoo.com>
 */
public class NestedCarService {

    /**
     * The json path to the data that maps to the {@link NestedCarModel} object.
     */
    private final String[] modelTraversalPath;

    private ObjectMapper objectMapper;

    public NestedCarService(String[] modelTraversalPath) {
        this.modelTraversalPath = modelTraversalPath;

        initObjectMapper();
    }

    /**
     * Initializes the {@link ObjectMapper} used by the service. This includes the registration of custom (de)serializers.
     */
    private void initObjectMapper() {
        objectMapper = new ObjectMapper();

        // Registers the custom deserializer
        objectMapper.registerModule(new DeserializerModule(new DeserializerModifier(objectMapper, modelTraversalPath)));
    }

    /**
     * Deserializes the parameter json to a {@link NestedCarModel} object.
     *
     * @param nestedCarModelJson
     *          The json {@link String} to deserialize.
     * @return The {@link NestedCarModel} object that represents the parameter json String
     */
    public NestedCarModel deserialize(String nestedCarModelJson) throws IOException, JsonParseException, JsonMappingException {
        return objectMapper.readValue(nestedCarModelJson, NestedCarModel.class);
    }

    /**
     * @param nestedCarModel
     * @return
     */
    public String serialize(NestedCarModel nestedCarModel) {
        return null;
    }

    /**
     * A Jackson {@link SimpleModule} used to register a customized deserializer.
     *
     * @author Lewis Dawson <ldawson7777@yahoo.com>
     */
    private static final class DeserializerModule extends SimpleModule {

        private BeanDeserializerModifier deserializerModifier;

        public DeserializerModule(BeanDeserializerModifier deserializerModifier) {
            super("DeserializerModule", Version.unknownVersion());
            this.deserializerModifier = deserializerModifier;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setupModule(SetupContext context) {
            super.setupModule(context);
            context.addBeanDeserializerModifier(deserializerModifier);
        }

    }

    /**
     * A Jackson {@link BeanDeserializerModifier} instance used to define the {@link NestedCarModelDeserializer} as the deserializer for a {@link NestedCarModel} instance. Note that the {@link NestedCarModelDeserializer} is only used when the bean being deserialized is a {@link NestedCarModel}.
     *
     * @author Lewis Dawson <lewis_dawson@intuit.com>
     */
    private static final class DeserializerModifier extends BeanDeserializerModifier {

        private final ObjectMapper objectMapper;

        private final String[] modelTraversalPath;

        public DeserializerModifier(ObjectMapper objectMapper, String[] modelTraversalPath) {
            this.objectMapper = objectMapper;
            this.modelTraversalPath = modelTraversalPath;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config, BeanDescription beanDesc, JsonDeserializer<?> deserializer) {
            // If the bean being deserialized is a NestedCarModel, use the customer deserializer
            if (NestedCarModel.class.equals(beanDesc.getBeanClass())) {
                return new NestedCarModelDeserializer(deserializer, objectMapper, modelTraversalPath);
            }

            return super.modifyDeserializer(config, beanDesc, deserializer);
        }

    }

}
