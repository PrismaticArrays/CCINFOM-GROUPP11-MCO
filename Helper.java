import java.time.*;
import java.time.format.DateTimeParseException;

class Helper {

    public static void cls(){ //clears screen
        System.out.print("\033[H\033[2J"); 
    }

    public static Integer tryParseInt(String s){
        try{
            return Integer.parseInt(s);
        }
        catch (NumberFormatException e){
            return 0;
        }
    }

    public static Float tryParseFloat(String s){
        try{
            return Float.parseFloat(s);
        }
        catch (Exception e){
            return (float)0;
        }
    }

    public static LocalDate tryParseDate(String s){
        try{
            return LocalDate.parse(s);
        }
        catch (DateTimeParseException e){
            return null;
        }
    }

}