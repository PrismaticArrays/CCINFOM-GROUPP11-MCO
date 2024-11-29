import java.time.LocalDate;
import java.util.Scanner;

class Transactions {
    DBConnection dbconnect;

    public Transactions(DBConnection dbconnect){
        this.dbconnect = dbconnect;
    }

    public void showMenu(Scanner s) throws Exception{
        int option=-1;
        while(option!=3){
            printMenu();

            String input = s.nextLine();
            option = Helper.tryParseInt(input);

            boolean validSubInput;
            
            switch(option){
                case 1:
                    //attractions record
                    Helper.cls();
                    validSubInput=false;

                    while(!validSubInput){
                        System.out.println("==================================================");
                        System.out.println("                  Sell a ticket                   ");
                        System.out.println("==================================================");
                        dbconnect.executeSQL("SELECT * FROM view_attractions");
                        dbconnect.printResult();
                        System.out.println("==================================================");
                        System.out.print("Enter attraction ID: ");
                        int attractionID = Helper.tryParseInt(s.nextLine());
                        String firstName = "";
                        String lastName = "";
                        String purchaseDate = "";

                        if(attractionID>0){
                            dbconnect.executeSQL(String.format("SELECT * FROM view_attractions WHERE attractionID=%d", attractionID));

                            if(!dbconnect.isEmptyRS()){
                                System.out.print("Enter visitor's first name (max 20 characters): ");
                                firstName = s.nextLine();

                                if(firstName.length()<=20){
                                    System.out.print("Enter visitor's last name (max 20 characters): ");
                                    lastName = s.nextLine();
        
                                    if(lastName.length()<=20){
                                        System.out.print("Enter purchase date: ");
                                        purchaseDate = s.nextLine();

                                        if(Helper.tryParseDate(purchaseDate)!=null){
                                            validSubInput=true;
                                        }


                                        
                                    }
                                }
                            }
                        }



                        

                        if(!validSubInput){
                            Helper.cls();
                            System.out.println("==================================================");
                            System.out.println("        Invalid input! Please try again...        ");
                        }
                        else{
                            Helper.cls();

                            //check if visitor exists in record
                            dbconnect.executeSQL(String.format("SELECT * FROM view_visitors WHERE last_name = \'%s\' AND first_name = \'%s\'", lastName, firstName));

                            if(!dbconnect.isEmptyRS()){
                                System.out.println("==================================================");
                                System.out.println("> Visitor exists in records!");
                            }
                            else{
                                System.out.println("==================================================");
                                System.out.println("> Visitor does not exist in records! New record created!");
                                dbconnect.executeSQLNoRS(String.format("INSERT INTO visitors (last_name, first_name) VALUES (\'%s\', \'%s\')", lastName, firstName));
                            }

                            boolean validSubInput2=false;
                            int staffID=0;

                            while(!validSubInput2){
                                System.out.println("==================================================");
                                System.out.println("                  Sell a ticket                   ");
                                System.out.println("==================================================");
                                System.out.printf("Attraction ID: %d\n", attractionID);
                                System.out.printf("Visitor name: %s, %s\n", lastName, firstName);
                                System.out.printf("Purchase date: %s\n", purchaseDate);
                                System.out.println("==================================================");
                                System.out.println("Available staff:\n");
                                dbconnect.executeSQL(String.format("SELECT\ts.staffID AS staffID,\n" + //
                                                                    "\t\tCONCAT(s.first_name, ' ', s.last_name) AS staff_Name\n" + //
                                                                    "FROM assignments am\n" + //
                                                                    "JOIN staff s ON am.staffID = s.staffID\n" + //
                                                                    "JOIN attractions a ON am.attractionID = a.attractionID\n" + //
                                                                    "WHERE a.attractionID = %d AND am.to_date IS NULL;", attractionID));
                                dbconnect.printResult();
                                System.out.println("==================================================");
                                System.out.print("Enter staff ID of transaction handler: ");
                                staffID = Helper.tryParseInt(s.nextLine());

                                if(staffID>0){
                                    dbconnect.executeSQL(String.format("SELECT s.staffID AS staffID\n" + //
                                                                                "FROM assignments am\n" + //
                                                                                "JOIN staff s ON am.staffID = s.staffID\n" + //
                                                                                "JOIN attractions a ON am.attractionID = a.attractionID\n" + //
                                                                                "WHERE a.attractionID = %d AND am.to_date IS NULL AND s.staffID = %d;", attractionID, staffID));
                                    if(!dbconnect.isEmptyRS()){
                                        validSubInput2=true;
                                    }
                                }


                                if(!validSubInput2){
                                    Helper.cls();
                                    System.out.println("==================================================");
                                    System.out.println("        Invalid input! Please try again...        ");
                                }
                                else{
                                    Helper.cls();
                                    System.out.println("==================================================");
                                    System.out.println("                  Sell a ticket                   ");
                                    System.out.println("==================================================");
                                    System.out.printf("Attraction ID: %d\n", attractionID);
                                    System.out.printf("Visitor name: %s, %s\n", lastName, firstName);

                                    dbconnect.executeSQL(String.format("SELECT * FROM view_visitors WHERE last_name = \'%s\' AND first_name = \'%s\'", lastName, firstName));
                                    int visitorID = Helper.tryParseInt(dbconnect.getFirstValueAtColumn("visitorID"));
                                    System.out.printf("Visitor ID: %d\n", visitorID);

                                    System.out.printf("Purchase date: %s\n", purchaseDate);
                                    System.out.printf("Staff ID: %d\n", staffID);

                                    dbconnect.executeSQLNoRS("INSERT INTO tickets (use_date) VALUES (NULL)");
                                    dbconnect.executeSQL("SELECT max(ticketID) FROM tickets");

                                    int ticketID = Helper.tryParseInt(dbconnect.getFirstValueAtColumn("max(ticketID)"));
                                    System.out.printf("Ticket ID: %d\n", ticketID);
                                    System.out.println("==================================================");
                                    dbconnect.executeSQLNoRS(String.format("INSERT INTO transactions (staffID, ticketID, attractionID, visitorID, purchase_date) VALUES (%d, %d, %d, %d, \'%s\')", staffID, ticketID, attractionID, visitorID, purchaseDate));
                                    System.out.print("Enter anything to continue... ");
                                    s.nextLine();
                                }

                            }
                        }

                    }
                    
                    break;
                case 2:
                    //visitor record
                    Helper.cls();

                    validSubInput=false;
                    
                    while(!validSubInput){
                        System.out.println("==================================================");
                        System.out.println("            Hire/assign a staff member            ");
                        System.out.println("==================================================");
                        System.out.print("Enter first name (max 20 characters): ");
                        String firstName = s.nextLine();
                        String lastName = "";
                        int staffID=0;

                        if(firstName.length()<=20){
                            System.out.print("Enter last name (max 20 characters): ");
                            lastName = s.nextLine();

                            if(lastName.length()<=20){
                                validSubInput=true;
                            }
                        }
                        

                        if(!validSubInput){
                            Helper.cls();
                            System.out.println("==================================================");
                            System.out.println("        Invalid input! Please try again...        ");
                        }
                        else{
                            Helper.cls();
                            boolean validSubInput2=false;

                            dbconnect.executeSQL(String.format("SELECT * FROM view_staff WHERE last_name = \'%s\' AND first_name = \'%s\'", lastName, firstName));

                            if(!dbconnect.isEmptyRS()){
                                staffID = Helper.tryParseInt(dbconnect.getFirstValueAtColumn("staffID"));
                                System.out.println("==================================================");
                                System.out.println("> Staff exists in records!");

                            }
                            else{
                                String empDate = "";
                                float salary = 0;

                                while(!validSubInput2){
                                    System.out.println("==================================================");
                                    System.out.println("> Staff does not exist in records! Please enter info!");
                                    System.out.println("==================================================");
                                    System.out.println("            Hire/assign a staff member            ");
                                    System.out.println("==================================================");
                                    System.out.printf("Staff Name: %s, %s\n", lastName, firstName);
                                    System.out.print("Enter employment date: ");
                                    empDate = s.nextLine();

                                    if(Helper.tryParseDate(empDate)!=null){
                                        System.out.print("Enter salary: ");
                                        salary = Helper.tryParseFloat(s.nextLine());

                                        if(salary>0){
                                            validSubInput2=true;
                                        }
                                    }
                                    

                                    if(!validSubInput2){
                                        Helper.cls();
                                        System.out.println("==================================================");
                                        System.out.println("        Invalid input! Please try again...        ");
                                    }
                                    else{
                                        dbconnect.executeSQLNoRS(String.format("INSERT INTO staff (last_name, first_name, emp_date, salary) VALUES (\'%s\', \'%s\', \'%s\', %.2f)", lastName, firstName, empDate, salary));
                                    }

                                }
                                dbconnect.executeSQL(String.format("SELECT * FROM view_staff WHERE last_name = \'%s\' AND first_name = \'%s\'", lastName, firstName));
                                staffID = Helper.tryParseInt(dbconnect.getFirstValueAtColumn("staffID"));
                            }

                            Helper.cls();
                            
                            boolean validSubInput3 = false;
                            int attractionID=0;

                            while(!validSubInput3){
                                System.out.println("==================================================");
                                System.out.println("            Hire/assign a staff member            ");
                                System.out.println("==================================================");
                                System.out.printf("Staff ID: %d\n", staffID);
                                System.out.printf("Staff Name: %s, %s\n", lastName, firstName);
                                System.out.println("==================================================");
                                
                                dbconnect.executeSQL(String.format("SELECT a.attractionID, a.attractionName\n" + //
                                                                    "FROM attractions a\n" + //
                                                                    "LEFT OUTER JOIN assignments am ON a.attractionID = am.attractionID AND to_date IS NULL \n" + //
                                                                    "WHERE am.assignmentID IS NULL;"));
                                
                                

                                //force assignment to unassigned attractions if there are any
                                if(!dbconnect.isEmptyRS()){
                                    System.out.println("Unassigned attractions:");
                                    dbconnect.printResult();
                                    System.out.println("==================================================");

                                    System.out.print("Enter attraction ID of unassigned attraction: ");
                                    attractionID = Helper.tryParseInt(s.nextLine());

                                    if(attractionID>0){
                                        dbconnect.executeSQL(String.format("SELECT a.attractionID, a.attractionName\n" + //
                                                                                    "FROM attractions a\n" + //
                                                                                    "LEFT OUTER JOIN assignments am ON a.attractionID = am.attractionID AND to_date IS NULL \n" + //
                                                                                    "WHERE am.assignmentID IS NULL AND a.attractionID = %d;", attractionID));

                                        if(!dbconnect.isEmptyRS()){
                                            validSubInput3=true;
                                        }

                                    }

                                    if(!validSubInput3){
                                        Helper.cls();
                                        System.out.println("==================================================");
                                        System.out.println("        Invalid input! Please try again...        ");
                                    }
                                }
                                else{
                                    System.out.println("==================================================");
                                    System.out.print("Enter attraction ID of any attraction: ");
                                    attractionID = Helper.tryParseInt(s.nextLine());

                                    if(attractionID>0){
                                        dbconnect.executeSQL(String.format("SELECT * FROM view_attractions WHERE attractionID=%d", attractionID));
                                        
                                        if(!dbconnect.isEmptyRS()){
                                            validSubInput3=true;
                                        }
                                    }

                                    if(!validSubInput3){
                                        Helper.cls();
                                        System.out.println("==================================================");
                                        System.out.println("        Invalid input! Please try again...        ");
                                    }
                                }
                            }
                            
                            Helper.cls();

                            boolean validSubInput4 = false;
                            String fromDate = "";
                            String toDate = "";

                            while(!validSubInput4){
                                System.out.println("==================================================");
                                System.out.println("            Hire/assign a staff member            ");
                                System.out.println("==================================================");
                                System.out.printf("Staff ID: %d\n", staffID);
                                System.out.printf("Staff Name: %s, %s\n", lastName, firstName);
                                System.out.printf("Attraction ID: %d\n", attractionID);
                                System.out.println("==================================================");
                                System.out.print("Enter from date: ");
                                fromDate = s.nextLine();

                                if(Helper.tryParseDate(fromDate)!=null){
                                    System.out.print("Enter to date (enter null if staff will be assigned currently): ");
                                    toDate = s.nextLine();

                                    if(toDate.toLowerCase().equals("null")){
                                        validSubInput4=true;
                                    }
                                    else if (Helper.tryParseDate(toDate)!=null) {
                                        LocalDate date1 = Helper.tryParseDate(fromDate);
                                        LocalDate date2 = Helper.tryParseDate(toDate);

                                        //check if date 1 is earlier than date 2
                                        if(date1.compareTo(date2)<0){
                                            validSubInput4 = true;
                                        }
                                    }
                                }

                                if(!validSubInput4){
                                    Helper.cls();
                                    System.out.println("==================================================");
                                    System.out.println("        Invalid input! Please try again...        ");
                                }
                            }
                            
                            System.out.println("==================================================");
                            System.out.println("            Hire/assign a staff member            ");
                            System.out.println("==================================================");
                            System.out.printf("Staff ID: %d\n", staffID);
                            System.out.printf("Staff Name: %s, %s\n", lastName, firstName);
                            System.out.printf("Attraction ID: %d\n", attractionID);
                            System.out.printf("From date: %s\n", fromDate);
                            System.out.printf("To date: %s\n", toDate);
                            System.out.println("==================================================");
                            
                            if(toDate.toLowerCase().equals("null")){
                                dbconnect.executeSQLNoRS(String.format("INSERT INTO assignments (staffID, attractionID, from_date, to_date) VALUES (%d, %d, \'%s\', NULL)", staffID, attractionID, fromDate));
                            }
                            else{
                                dbconnect.executeSQLNoRS(String.format("INSERT INTO assignments (staffID, attractionID, from_date, to_date) VALUES (%d, %d, \'%s\', \'%s\')", staffID, attractionID, fromDate, toDate));
                            }
                            System.out.print("Enter anything to continue... ");
                            s.nextLine();
                        }
                    
                
                    }
                    break;
                
                case 3:
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

    public void printMenu(){
        System.out.println("==================================================");
        System.out.println("                Make a Transaction                ");
        System.out.println("==================================================");
        System.out.println("\t1 . . . Sell a ticket");
        System.out.println("\t2 . . . Hire/assign a staff member");

        System.out.println("\n\t3 . . . Cancel");
        System.out.println("==================================================");
        System.out.print(" Select option: ");
    }

}