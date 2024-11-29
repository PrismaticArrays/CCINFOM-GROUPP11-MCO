import java.util.Scanner;

class Record_Tickets {
    public Record_Tickets(){

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
                        System.out.println("                 Add new ticket                   ");
                        System.out.println("==================================================");
                        dbconnect.executeSQL("SELECT * FROM view_tickets");
                        dbconnect.printResult();
                        System.out.println("==================================================");
                        System.out.print("Enter ticket's use date (enter 'null' if ticket is unused): ");
                        String ticketDate = s.nextLine();
                        
                        if(ticketDate.toLowerCase().equals("null")){
                            validSubInput=true;
                        }
                        else if (Helper.tryParseDate(ticketDate)!=null){
                            validSubInput=true;
                        }

                        if(!validSubInput){
                            Helper.cls();
                            System.out.println("==================================================");
                            System.out.println("        Invalid input! Please try again...        ");
                        }
                        else{
                            String sql = "";
                            if(ticketDate.toLowerCase().equals("null")){
                                sql = String.format("INSERT INTO tickets (use_date) VALUES (NULL)");
                            }
                            else{
                                sql = String.format("INSERT INTO tickets (use_date) VALUES (\'%s\')", ticketDate);
                            }
                            
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
                        System.out.println("              Update ticket's date                ");
                        System.out.println("==================================================");
                        dbconnect.executeSQL("SELECT * FROM view_tickets");
                        dbconnect.printResult();
                        System.out.println("==================================================");
                        System.out.print("Enter ticket ID: ");
                        int ticketID = Helper.tryParseInt(s.nextLine());
                        String ticketDate = "";
                        
                        dbconnect.executeSQL(String.format("SELECT * FROM view_tickets WHERE ticketID=%d", ticketID));

                        if(!dbconnect.isEmptyRS()){
                            System.out.print("Enter ticket's use date (enter 'null' if ticket is unused): ");
                            ticketDate = s.nextLine();
                            
                            if(ticketDate.toLowerCase().equals("null")){
                                validSubInput=true;
                            }
                            else if (Helper.tryParseDate(ticketDate)!=null){
                                validSubInput=true;
                            }
                        }

                        if(!validSubInput){
                            Helper.cls();
                            System.out.println("==================================================");
                            System.out.println("        Invalid input! Please try again...        ");
                        }
                        else{
                            String sql = "";
                            if(ticketDate.toLowerCase().equals("null")){
                                sql = String.format("UPDATE tickets SET use_date = NULL WHERE ticketID=%d", ticketID);
                            }
                            else{
                                sql = String.format("UPDATE tickets SET use_date = \'%s\' WHERE ticketID=%d", ticketDate, ticketID);
                            }
                            
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
                        System.out.println("                 Delete ticket                    ");
                        System.out.println("==================================================");
                        dbconnect.executeSQL("SELECT * FROM view_tickets");
                        dbconnect.printResult();
                        System.out.println("==================================================");
                        System.out.print("Enter ticket ID: ");
                        int ticketID = Helper.tryParseInt(s.nextLine());
                        
                        dbconnect.executeSQL(String.format("SELECT * FROM view_tickets WHERE ticketID=%d", ticketID));

                        if(!dbconnect.isEmptyRS()){
                            validSubInput=true;
                        }

                        if(!validSubInput){
                            Helper.cls();
                            System.out.println("==================================================");
                            System.out.println("        Invalid input! Please try again...        ");
                        }
                        else{
                            String sql = String.format("DELETE FROM tickets WHERE ticketID=%d", ticketID);
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
                        System.out.println("                  View ticket                    ");
                        System.out.println("==================================================");
                        dbconnect.executeSQL("SELECT * FROM view_tickets");
                        dbconnect.printResult();
                        System.out.println("==================================================");
                        System.out.print("Enter ticket ID: ");
                        int ticketID = Helper.tryParseInt(s.nextLine());
                        
                        dbconnect.executeSQL(String.format("SELECT * FROM view_tickets WHERE ticketID=%d", ticketID));

                        if(!dbconnect.isEmptyRS()){
                            validSubInput=true;
                        }

                        if(!validSubInput){
                            Helper.cls();
                            System.out.println("==================================================");
                            System.out.println("        Invalid input! Please try again...        ");
                        }
                        else{
                            String sql = String.format("SELECT * FROM tickets WHERE ticketID=%d", ticketID);
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
                        System.out.println("        List tickets with specific use date       ");
                        System.out.println("==================================================");
                        System.out.print("Enter ticket's use date (enter 'null' if ticket is unused): ");
                        String ticketDate = s.nextLine();
                        
                        if(ticketDate.toLowerCase().equals("null")){
                            validSubInput=true;
                        }
                        else if (Helper.tryParseDate(ticketDate)!=null){
                            validSubInput=true;
                        }


                        if(!validSubInput){
                            Helper.cls();
                            System.out.println("==================================================");
                            System.out.println("        Invalid input! Please try again...        ");
                        }
                        else{
                            
                            System.out.println("==================================================");
                            if(ticketDate.toLowerCase().equals("null")){
                                dbconnect.executeSQL(String.format("SELECT * FROM view_tickets WHERE use_date IS NULL"));
                            }
                            else{
                                dbconnect.executeSQL(String.format("SELECT * FROM view_tickets WHERE use_date=\'%s\'", ticketDate));
                            }
                            
                            dbconnect.printResult();
                            System.out.println("==================================================");
                            System.out.print("Enter anything to continue... ");
                            s.nextLine();
                        }
                    }
                    break;
                case 6:
                    //special - ticket profits
                    Helper.cls();

                    validSubInput=false;

                    while(!validSubInput){
                        System.out.println("==================================================");
                        System.out.println("          Tickets bought at certain date          ");
                        System.out.println("==================================================");
                        dbconnect.executeSQL("SELECT * FROM view_tickets");
                        dbconnect.printResult();
                        System.out.println("==================================================");
                        System.out.print("Enter purchase date: ");
                        String purchaseDate = s.nextLine();
                        
                        if(Helper.tryParseDate(purchaseDate)!=null){
                            validSubInput=true;
                        }

                        if(!validSubInput){
                            Helper.cls();
                            System.out.println("==================================================");
                            System.out.println("        Invalid input! Please try again...        ");
                        }
                        else{
                            String sql = String.format("SELECT\tt.ticketID AS Ticket_ID,\n" + //
                                                                "\t\ttr.purchase_date AS Purchase_Date\n" + //
                                                                "FROM tickets t \n" + //
                                                                "JOIN transactions tr ON tr.ticketID = t.ticketID\n" + //
                                                                "WHERE tr.purchase_date = \'%s\';", purchaseDate);
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
        System.out.println("            Ticket Record Management              ");
        System.out.println("==================================================");
        dbconnect.executeSQL("SELECT * FROM view_tickets");
        dbconnect.printResult();
        System.out.println("==================================================");
        System.out.println("\t1 . . . Add new ticket");
        System.out.println("\t2 . . . Update ticket's use date");
        System.out.println("\t3 . . . Delete ticket");
        System.out.println("\t4 . . . View specific ticket");
        System.out.println("\t5 . . . List tickets with specific use date");
        System.out.println("\t6 . . . List tickets bought at specific date");

        System.out.println("\n\t7 . . . Cancel");
        System.out.println("==================================================");
        System.out.print(" Select option: ");
    }

}