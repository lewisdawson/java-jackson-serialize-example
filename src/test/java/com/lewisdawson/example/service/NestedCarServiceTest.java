package com.lewisdawson.example.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.lewisdawson.example.model.NestedCarModel;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

/**
 * A JUnit test that demonstrates the serialization and deserialization of a {@link NestedCarModel} object using the {@link NestedCarService}.
 *
 * @author Lewis Dawson <lew.dawson@lewdawson.com>
 */
public class NestedCarServiceTest {

    /**
     * The name of the test json file to load. Note that the unformatted version is loaded so it can be asserted.
     */
    private static final String JSON_FILE = "NestedCarModel.json";

    /**
     * The json path to the data that maps to the {@link NestedCarModel} object.
     */
    private static final String[] JSON_TRAVERSAL_PATH = {"cars", "japanese", "makes", "honda", "models", "civic"};

    /**
     * The service used to serialize and deserialize {@link NestedCarModel} Objects
     */
    private NestedCarService nestedCarService;

    /**
     * The json string that represents the data to be serialized and deserialized.
     */
    private String nestedCarModelJson;

    /**
     * The {@link NestedCarModel} that represents the json stored in the {@link #JSON_FILE}.
     */
    private NestedCarModel nestedCarModel;

    /**
     * Constructs the test. It creates a new service for (de)serialization and loads the test json file.
     *
     * @throws Exception
     *          If there's an error that occurs opening the test json file
     */
    public NestedCarServiceTest() throws Exception {
        nestedCarService = new NestedCarService(JSON_TRAVERSAL_PATH);

        loadJsonFile();
        createNestedCarModel();
    }

    /**
     * Loads the test json file.
     *
     * @throws Exception
     *          If there's an error opening the file
     */
    private void loadJsonFile() throws Exception {
        nestedCarModelJson = IOUtils.toString(NestedCarServiceTest.class.getResourceAsStream(JSON_FILE));
    }

    /**
     * Creates a {@link NestedCarModel} object that represents the json stored in the {@link #JSON_FILE}.
     */
    private void createNestedCarModel() {
        nestedCarModel = new NestedCarModel();

        nestedCarModel.setEngineSize("2.0L");
        nestedCarModel.setMileage(30000);
        nestedCarModel.setPrice(15000);
        nestedCarModel.setTireSize("215/45/17");
        nestedCarModel.setTrim("si");
        nestedCarModel.setVin("234HY345858BH9342437");
    }

    /**
     * Tests the service to verify that it deserializes properly.
     *
     * @throws Exception
     *          If an error occurs during deserialization
     */
    @Test
    public void testDeserialize() throws Exception {
        NestedCarModel deserializedModel = nestedCarService.deserialize(nestedCarModelJson);

        assertNotNull(deserializedModel);
        assertEquals(nestedCarModel, deserializedModel);
    }

    /**
     * Tests the service to verify that it serializes properly.
     *
     * @throws Exception
     *          If an error occurs during serialization
     */
    @Test
    public void testSerialize() throws Exception {
        String serializedModelJson = nestedCarService.serialize(nestedCarModel);

        assertNotNull(serializedModelJson);
        assertEquals(serializedModelJson, nestedCarModelJson);
    }

}
