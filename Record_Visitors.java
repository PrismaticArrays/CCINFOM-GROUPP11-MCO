import java.util.Scanner;

class Record_Visitors {
    public Record_Visitors(){

    }

    public void showMenu(Scanner s, DBConnection dbconnect) throws Exception{
        int option=-1;
        while(option!=7){
            boolean validSubInput;
            printMenu(dbconnect);

            String input = s.nextLine();
            option = Helper.tryParseInt(input);
            
            switch(option){
                case 1:
                    //add new record
                    validSubInput = false;
                    Helper.cls();

                    while(!validSubInput){
                        System.out.println("==================================================");
                        System.out.println("               Add new visitor                 ");
                        System.out.println("==================================================");
                        dbconnect.executeSQL("SELECT * FROM view_visitors");
                        dbconnect.printResult();
                        System.out.println("==================================================");
                        System.out.print("Enter visitor's last name (max 20 characters): ");
                        String lastName = s.nextLine();
                        String firstName = "";

                        if(lastName.length()<=20){
                            System.out.print("Enter visitor's first name (max 20 characters): ");
                            firstName = s.nextLine();
                            
                            
                            if(firstName.length()<=20){
                                validSubInput=true;
                            }
                        }

                        if(!validSubInput){
                            Helper.cls();
                            System.out.println("==================================================");
                            System.out.println("        Invalid input! Please try again...        ");
                        }
                        else{
                            String sql = String.format("INSERT INTO visitors (last_name, first_name) VALUES (\'%s\', \'%s\')", lastName, firstName);
                            dbconnect.executeSQLNoRS(sql);
                        }

                    }
                    
                    break;
                case 2:
                    //update record
                    validSubInput = false;
                    Helper.cls();

                    while(!validSubInput){
                        System.out.println("==================================================");
                        System.out.println("              Update visitor's name               ");
                        System.out.println("==================================================");
                        dbconnect.executeSQL("SELECT * FROM view_visitors");
                        dbconnect.printResult();
                        System.out.println("==================================================");
                        System.out.print("Enter visitor ID: ");
                        int visitorID = Helper.tryParseInt(s.nextLine());
                        String lastName = "";
                        String firstName = "";
                        
                        dbconnect.executeSQL(String.format("SELECT * FROM view_visitors WHERE visitorID=%d", visitorID));

                        if(!dbconnect.isEmptyRS()){
                            System.out.print("Enter visitor's last name (max 20 characters): ");
                            lastName = s.nextLine();

                            if(lastName.length()<=20){
                                System.out.print("Enter visitor's first name (max 20 characters): ");
                                firstName = s.nextLine();
                                
                                
                                if(firstName.length()<=20){
                                    validSubInput=true;
                                }
                            }
                        }

                        if(!validSubInput){
                            Helper.cls();
                            System.out.println("==================================================");
                            System.out.println("        Invalid input! Please try again...        ");
                        }
                        else{
                            String sql = String.format("UPDATE visitors SET last_name=\'%s\', first_name=\'%s\' WHERE visitorID=%d", lastName, firstName, visitorID);
                            dbconnect.executeSQLNoRS(sql);
                        }
                    }
                    break;
                case 3:
                    //delete record
                    
                    validSubInput = false;
                    Helper.cls();

                    while(!validSubInput){
                        System.out.println("==================================================");
                        System.out.println("                 Delete visitor                   ");
                        System.out.println("==================================================");
                        dbconnect.executeSQL("SELECT * FROM view_visitors");
                        dbconnect.printResult();
                        System.out.println("==================================================");
                        System.out.print("Enter visitor ID: ");
                        int visitorID = Helper.tryParseInt(s.nextLine());
                        
                        dbconnect.executeSQL(String.format("SELECT * FROM view_visitors WHERE visitorID=%d", visitorID));

                        if(!dbconnect.isEmptyRS()){
                            validSubInput=true;
                        }

                        if(!validSubInput){
                            Helper.cls();
                            System.out.println("==================================================");
                            System.out.println("        Invalid input! Please try again...        ");
                        }
                        else{
                            String sql = String.format("DELETE FROM visitors WHERE visitorID=%d", visitorID);
                            dbconnect.executeSQLNoRS(sql);
                        }
                    }
                    break;
                case 4:
                    //view specific record
                    
                    validSubInput = false;
                    Helper.cls();

                    while(!validSubInput){
                        System.out.println("==================================================");
                        System.out.println("                  View visitor                    ");
                        System.out.println("==================================================");
                        dbconnect.executeSQL("SELECT * FROM view_visitors");
                        dbconnect.printResult();
                        System.out.println("==================================================");
                        System.out.print("Enter visitor ID: ");
                        int visitorID = Helper.tryParseInt(s.nextLine());
                        
                        dbconnect.executeSQL(String.format("SELECT * FROM view_visitors WHERE visitorID=%d", visitorID));

                        if(!dbconnect.isEmptyRS()){
                            validSubInput=true;
                        }

                        if(!validSubInput){
                            Helper.cls();
                            System.out.println("==================================================");
                            System.out.println("        Invalid input! Please try again...        ");
                        }
                        else{
                            String sql = String.format("SELECT * FROM visitors WHERE visitorID=%d", visitorID);
                            dbconnect.executeSQL(sql);
                            System.out.println("==================================================");
                            dbconnect.printResult();
                            System.out.println("==================================================");
                            System.out.print("Enter anything to continue... ");
                            s.nextLine();
                        }
                    }
                    break;
                case 5:
                    //list records
                    Helper.cls();

                    validSubInput = false;
                    Helper.cls();

                    while(!validSubInput){
                        System.out.println("==================================================");
                        System.out.println("       List visitors with specific last name      ");
                        System.out.println("==================================================");
                        System.out.print("Enter last name: ");
                        String lastName = s.nextLine();

                        dbconnect.executeSQL(String.format("SELECT * FROM view_visitors WHERE last_name=\'%s\'", lastName));

                        if(!dbconnect.isEmptyRS()){
                            validSubInput=true;
                        }


                        if(!validSubInput){
                            Helper.cls();
                            System.out.println("==================================================");
                            System.out.println("        Invalid input! Please try again...        ");
                        }
                        else{
                            
                            System.out.println("==================================================");
                            dbconnect.executeSQL(String.format("SELECT * FROM view_visitors WHERE last_name=\'%s\'", lastName));
                            dbconnect.printResult();
                            System.out.println("==================================================");
                            System.out.print("Enter anything to continue... ");
                            s.nextLine();
                        }
                    }
                    break;
                case 6:
                    //special - visited attractions
                    Helper.cls();

                    validSubInput=false;

                    while(!validSubInput){
                        System.out.println("==================================================");
                        System.out.println("           Visitor's visited attractions          ");
                        System.out.println("==================================================");
                        dbconnect.executeSQL("SELECT * FROM view_visitors");
                        dbconnect.printResult();
                        System.out.println("==================================================");
                        System.out.print("Enter visitor ID: ");
                        int visitorID = Helper.tryParseInt(s.nextLine());
                        
                        dbconnect.executeSQL(String.format("SELECT * FROM view_visitors WHERE visitorID=%d", visitorID));

                        if(!dbconnect.isEmptyRS()){
                            validSubInput=true;
                        }

                        if(!validSubInput){
                            Helper.cls();
                            System.out.println("==================================================");
                            System.out.println("        Invalid input! Please try again...        ");
                        }
                        else{
                            String sql = String.format("SELECT  CONCAT(v.first_name, ' ', v.last_name) AS Visitor_Name,\n" + //
                                                                "        a.attractionName AS Attraction_Visited,\n" + //
                                                                "        t.use_date AS Date_of_Visit\n" + //
                                                                "FROM visitors v\n" + //
                                                                "JOIN transactions tr ON v.visitorID = tr.visitorID\n" + //
                                                                "JOIN tickets t ON tr.ticketID = t.ticketID\n" + //
                                                                "JOIN attractions a ON tr.attractionID = a.attractionID\n" + //
                                                                "WHERE v.visitorID = %d;", visitorID);
                            dbconnect.executeSQL(sql);
                            System.out.println("==================================================");
                            dbconnect.printResult();
                            System.out.println("==================================================");
                            System.out.print("Enter anything to continue... ");
                            s.nextLine();
                        }

                    }
                    break;
                case 7:
                    //cancel
                    Helper.cls();
                    break;
                default:
                    Helper.cls();
                    System.out.println("==================================================");
                    System.out.println("        Invalid input! Please try again...        ");
                    break;
            }
        }

    }

    public void printMenu(DBConnection dbconnect) throws Exception{
        System.out.println("==================================================");
        System.out.println("            Visitor Record Management             ");
        System.out.println("==================================================");
        dbconnect.executeSQL("SELECT * FROM view_visitors");
        dbconnect.printResult();
        System.out.println("==================================================");
        System.out.println("\t1 . . . Add new visitor");
        System.out.println("\t2 . . . Update visitor's name");
        System.out.println("\t3 . . . Delete visitor");
        System.out.println("\t4 . . . View specific visitor");
        System.out.println("\t5 . . . List visitors with specific last name");
        System.out.println("\t6 . . . List specific visitor's visited attractions");

        System.out.println("\n\t7 . . . Cancel");
        System.out.println("==================================================");
        System.out.print(" Select option: ");
    }

}