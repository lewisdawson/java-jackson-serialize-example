package com.lewisdawson.example.service;

import static org.junit.Assert.assertNotNull;

import com.lewisdawson.example.model.NestedCarModel;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

/**
 * A JUnit test that demonstrates the serialization and deserialization of a {@link NestedCarModel} object using the {@link NestedCarService}.
 *
 * @author Lewis Dawson <ldawson7777@yahoo.com>
 */
public class NestedCarServiceTest {

    /**
     * The name of the test json file to load.
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
     * Constructs the test. It creates a new service for (de)serialization and loads the test json file.
     *
     * @throws Exception
     *          If there's an error that occurs opening the test json file
     */
    public NestedCarServiceTest() throws Exception {
        nestedCarService = new NestedCarService(JSON_TRAVERSAL_PATH);

        loadJsonFile();
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
     * Tests the service to verify that it (de)serializes properly.
     *
     * @throws Exception
     *          If an error occurs during (de)serialization
     */
    @Test
    public void testService() throws Exception {
        NestedCarModel deserializedModel = nestedCarService.deserialize(nestedCarModelJson);
        assertNotNull(deserializedModel);
    }

}
