package config;


import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.yaml.snakeyaml.Yaml;
import utils.constantes.ConfigConstants;

import java.util.Map;

@Getter
@Log4j2
@Singleton
public class ConfigYaml {

    private String user;
    private String passw;
    private String path;
    private String driver;
    private String host;

    void cargar() {

        try {
            Yaml yaml = new Yaml();
            Iterable<Object> it = null;

            it = yaml
                    .loadAll(this.getClass().getClassLoader().getResourceAsStream(ConfigConstants.CONFIG_PATH));

            Map<String, String> m = (Map) it.iterator().next();
            this.path = m.get(ConfigConstants.PATH);
            this.passw = m.get(ConfigConstants.PASSW);
            this.user = m.get(ConfigConstants.USER);
            this.driver = m.get(ConfigConstants.DRIVER);
            this.host = m.get(ConfigConstants.HOST);


        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }


}
