import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

import static org.apache.logging.log4j.core.util.Loader.getClassLoader;

public class ApplicationProperties {
    private static Properties instance= null;


    private static final String APPLICATION_PREFIX="application";
    private static final String APPLICATION_SUFFIX="properties";

    private static  final Logger logger= (Logger) LogManager.getLogger(ApplicationProperties.class);

    public static  Properties getInstance() throws IOException {
        if(instance == null) {
            instance = loadPropertiesFile();
        }
            return instance;
        }



    private ApplicationProperties()    {

    }


    private  static Properties loadPropertiesFile() throws IOException {
        String enviroment= Optional.ofNullable(System.getenv("env"))
                .orElse("dev");
        String fileName= String.format("%s-%s.%s",APPLICATION_PREFIX,enviroment,APPLICATION_SUFFIX);

        logger.info("Property",fileName);
        Properties prop= new Properties();
        try {
            prop.load(getClassLoader().getResourceAsStream(fileName));
        }catch (IOException e){
            logger.error ("No se pudo cargar el archivo{}",fileName);
        }

    return prop;
    }
}
