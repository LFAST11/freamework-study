import com.example.spring2.formwork.context.ApplicationContext;

/**
 * lingfan
 * 2019-08-19 16:58
 */
public class TestIocAndDI {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContext("classpath:application.properties");
        System.out.println(applicationContext);
    }
}
