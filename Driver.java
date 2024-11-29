import java.util.Scanner;

// use the following command to run the file: java -cp .;mysql-connector-j-9.1.0.jar Driver

class Driver {
    public static void main(String[] args) throws Exception{
        Helper.cls();

        DBConnection dbconnect = new DBConnection();
        RecordsManagement rm = new RecordsManagement(dbconnect);
        Transactions trans = new Transactions(dbconnect);
        Reports reports = new Reports(dbconnect);
        Scanner s = new Scanner(System.in);

        int option = -1;
        while (option!=4){
            showMenuMain();
            String input = s.nextLine();
            option = Helper.tryParseInt(input);

            switch(option){
                case 1:
                    //records management
                    Helper.cls();
                    rm.showMenu(s);
                    break;
                case 2:
                    //transactions
                    Helper.cls();
                    trans.showMenu(s);
                    break;
                case 3:
                    //reports
                    Helper.cls();
                    reports.showMenu(s);
                    break;
                case 4:
                    //exit
                    Helper.cls();
                    dbconnect.exit();
                    break;
                default:
                    Helper.cls();
                    System.out.println("==================================================");
                    System.out.println("        Invalid input! Please try again...        ");
                    break;

            }


        }

        dbconnect.exit();
        

    }

    public static void showMenuMain(){
        System.out.println("==================================================");
        System.out.println(" Welcome to the Amusement Park Management System! ");
        System.out.println("==================================================");
        System.out.println("\t1 . . . Records Management");
        System.out.println("\t2 . . . Make a Transaction");
        System.out.println("\t3 . . . Reports Generation");

        System.out.println("\n\t4 . . . Exit program");
        System.out.println("==================================================");
        System.out.print(" Select option: ");

    }


}