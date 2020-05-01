import ro.mpp.spring.UI.Console;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args){

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "ro.mpp.spring"
                );

        context.getBean(Console.class).menu();


    }

}
