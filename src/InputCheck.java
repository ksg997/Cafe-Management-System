// Utility Class for methods used throughout the program
/* Current functions:
        - isNumeric(String s) - Check if String contains numbers only
        - isAlphaNumeric(String s) - Check if String contains alphanumeric only
*/ 
public final class InputCheck {
    private InputCheck() {}

    // Check if string is strictly alpha-numeric
    public static boolean isAlphaNumeric(String s){
        String pattern= "^[a-zA-Z0-9]*$";
        return s.matches(pattern);
    }

    public static boolean isNumeric(String str) {
        try {
            if(str.contains(".")) {
                Double.parseDouble(str);
            }else{
                Integer.parseInt(str);
            }
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
