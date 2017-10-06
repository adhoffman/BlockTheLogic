import java.util.regex.Pattern;

/**
 * Created by alexhoffman on 10/6/17.
 */
public class InputParser {


    public static Project parseProjectInput(String inputProjectString) {

   String[] fields = inputProjectString.split(Pattern.quote("|"));

   System.out.println("1:  "+fields[0]);
        System.out.println("2:  "+fields[1]);
        System.out.println("3:  "+fields[2]);
        System.out.println("4:  "+fields[3]);
        System.out.println("5:  "+fields[4]);
        System.out.println("6:  "+fields[5]);
        System.out.println("7:  "+fields[6]);
        System.out.println("8:  "+fields[7]);
        System.out.println("9:  "+fields[8]);
        System.out.println("10:  "+fields[9]);
        System.out.println("11:  "+fields[10]);
        System.out.println("12:  "+fields[11]);
        System.out.println("13:  "+fields[12]);

        return new Project();

    }


}
