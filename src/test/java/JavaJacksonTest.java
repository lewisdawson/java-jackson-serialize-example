import com.lewisdawson.example.model.NestedCarModel;
import com.lewisdawson.example.service.NestedCarService;
import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;

/**
 * A JUnit test that demonstrates the serialization and deserialization of a {@link NestedCarModel} object using the {@link NestedCarService}.
 *
 * @author Lewis Dawson <ldawson7777@yahoo.com>
 */
public class JavaJacksonTest {

    private static final String JSON_FILE = "NestedCarModel.json";

    private static final String[] JSON_TRAVERSAL_PATH = {"cars", "japanese", "makes", "honda", "models", "civic"};

    /**
     * The service used to serialize and deserialize {@link NestedCarModel} Objects
     */
    private NestedCarService nestedCarService;

    /**
     * The json string that represents the data to be serialized and deserialized.
     */
    private String nestedCarModelJson;

    public JavaJacksonTest() throws FileNotFoundException, IOException {
        nestedCarService = new NestedCarService(JSON_TRAVERSAL_PATH);

        loadJsonFile();
    }

    private void loadJsonFile() throws FileNotFoundException, IOException {
        InputStream inputStream = JavaJacksonTest.class.getResourceAsStream(JSON_FILE);

        nestedCarModelJson = IOUtils.toString(inputStream);
    }

    @Test
    public void testService() throws Exception {
        NestedCarModel deserializedModel = nestedCarService.deserialize(nestedCarModelJson);
        int i = 0;
    }

}
