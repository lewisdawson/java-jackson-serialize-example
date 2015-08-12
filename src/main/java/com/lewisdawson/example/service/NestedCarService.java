package com.lewisdawson.example.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.lewisdawson.example.deserialize.NestedCarModelDeserializer;
import com.lewisdawson.example.deserialize.NestedCarModelSerializer;
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

    private JsonFactory jsonFactory;

    public NestedCarService(String[] modelTraversalPath) {
        this.modelTraversalPath = modelTraversalPath;
        jsonFactory = new JsonFactory();

        initObjectMapper();
    }

    /**
     * Initializes the {@link ObjectMapper} used by the service. This includes the registration of custom
     * (de)serializers.
     */
    private void initObjectMapper() {
        objectMapper = new ObjectMapper();

        // Registers the custom deserializer
        objectMapper.registerModule(new DeserializerModule(new DeserializerModifier()));
        // Register the custom serializer
        objectMapper.registerModule(new SerializerModule(new SerializerModifier()));
    }

    /**
     * Deserializes the parameter json to a {@link NestedCarModel} object.
     *
     * @param nestedCarModelJson
     *         The json {@link String} to deserialize.
     * @return The {@link NestedCarModel} object that represents the parameter json String
     * @throws IOException
     *         If the Jackson object mapper is unable to deserialize the json string
     */
    public NestedCarModel deserialize(String nestedCarModelJson) throws IOException {
        return objectMapper.readValue(nestedCarModelJson, NestedCarModel.class);
    }

    /**
     * Serializes the parameter nestedCarModel to a json string.
     *
     * @param nestedCarModel
     *         The {@link NestedCarModel} object to serialize
     * @return A json string that represents the paramter nestedCarModel
     * @throws IOException
     *         If the Jackson object mapper is unable to serialize the model
     */
    public String serialize(NestedCarModel nestedCarModel) throws IOException {
        return objectMapper.writeValueAsString(nestedCarModel);
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
     * A Jackson {@link BeanDeserializerModifier} instance used to define the {@link NestedCarModelDeserializer} as the
     * deserializer for a {@link NestedCarModel} instance. Note that the {@link NestedCarModelDeserializer} is only used
     * when the bean being deserialized is a {@link NestedCarModel}.
     *
     * @author Lewis Dawson <lewis_dawson@intuit.com>
     */
    private final class DeserializerModifier extends BeanDeserializerModifier {

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

    /**
     * A Jackson {@link SimpleModule} used to register a customized serializer.
     *
     * @author Lewis Dawson <ldawson7777@yahoo.com>
     */
    private static final class SerializerModule extends SimpleModule {

        private BeanSerializerModifier serializerModifier;

        public SerializerModule(BeanSerializerModifier serializerModifier) {
            super("SerializerModule", Version.unknownVersion());
            this.serializerModifier = serializerModifier;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setupModule(SetupContext context) {
            super.setupModule(context);
            context.addBeanSerializerModifier(serializerModifier);
        }

    }

    /**
     * A Jackson {@link BeanSerializerModifier} instance used to define the {@link NestedCarModelSerializer} as the
     * serializer for a {@link NestedCarModel} instance. Note that the {@link NestedCarModelSerializer} is only used
     * when the bean being serialized is a {@link NestedCarModel}.
     *
     * @author Lewis Dawson <lewis_dawson@intuit.com>
     */
    private final class SerializerModifier extends BeanSerializerModifier {

        @Override
        public JsonSerializer<?> modifySerializer(SerializationConfig config, BeanDescription beanDesc, JsonSerializer<?> serializer) {
            // If the bean being serialized is a NestedCarModel, use the customer serializer
            if(NestedCarModel.class.equals(beanDesc.getBeanClass())) {
                return new NestedCarModelSerializer(jsonFactory, (JsonSerializer<Object>) serializer, modelTraversalPath);
            }

            return super.modifySerializer(config, beanDesc, serializer);
        }
    }

}
