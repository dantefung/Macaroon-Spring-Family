import org.junit.Test;

import java.util.Properties;

/**
 * @author Zhang Qiang
 * @date 2019/11/19 9:41
 */

public class PropertiesTest {

    @Test
    public void propertiesTest(){
        Properties properties=System.getProperties();
        System.out.println("user.dir: " + properties.get("user.dir"));;
        properties.list(System.out);
    }

}
