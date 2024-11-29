import java.util.Scanner;

class Record_Transactions {
    public Record_Transactions(){

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
                        System.out.println("                 Add new transaction                   ");
                        System.out.println("==================================================");
                        dbconnect.executeSQL("SELECT * FROM view_transactions");
                        dbconnect.printResult();
                        System.out.println("==================================================");
                        System.out.print("Enter staff ID: ");
                        int staffID = Helper.tryParseInt(s.nextLine());
                        int ticketID = 0;
                        int attractionID = 0;
                        int visitorID = 0;
                        String purchaseDate = "";
                        
                        if(staffID>0){
                            dbconnect.executeSQL(String.format("SELECT * FROM view_staff WHERE staffID=%d", staffID));

                            if(!dbconnect.isEmptyRS()){
                                System.out.print("Enter ticket ID: ");
                                ticketID = Helper.tryParseInt(s.nextLine());

                                if(ticketID>0){
                                    dbconnect.executeSQL(String.format("SELECT * FROM view_tickets WHERE ticketID=%d", ticketID));

                                    if(!dbconnect.isEmptyRS()){
                                        System.out.print("Enter attraction ID: ");
                                        attractionID = Helper.tryParseInt(s.nextLine());

                                        if(attractionID>0){
                                            dbconnect.executeSQL(String.format("SELECT * FROM view_attractions WHERE attractionID=%d", attractionID));

                                            if(!dbconnect.isEmptyRS()){
                                                System.out.print("Enter visitor ID: ");
                                                visitorID = Helper.tryParseInt(s.nextLine());

                                                if(visitorID>0){
                                                    dbconnect.executeSQL(String.format("SELECT * FROM view_visitors WHERE visitorID=%d", visitorID));

                                                    if(!dbconnect.isEmptyRS()){
                                                        System.out.print("Enter purchase date: ");
                                                        purchaseDate = s.nextLine();

                                                        if(Helper.tryParseDate(purchaseDate)!=null){
                                                            validSubInput=true;
                                                        }
                                                    }
                                                }
                                            }
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
                            String sql = "";
                            sql = String.format("INSERT INTO transactions (staffID, ticketID, attractionID, visitorID, purchase_date) VALUES (%d, %d, %d, %d, \'%s\')", staffID, ticketID, attractionID, visitorID, purchaseDate);
                            
                            
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
                        System.out.println("        Update transaction's purchase date        ");
                        System.out.println("==================================================");
                        dbconnect.executeSQL("SELECT * FROM view_transactions");
                        dbconnect.printResult();
                        System.out.println("==================================================");
                        System.out.print("Enter transaction ID: ");
                        int transactionID = Helper.tryParseInt(s.nextLine());
                        String purchaseDate = "";
                        
                        dbconnect.executeSQL(String.format("SELECT * FROM view_transactions WHERE transID=%d", transactionID));

                        if(!dbconnect.isEmptyRS()){
                            System.out.print("Enter transaction's purchase date: ");
                            purchaseDate = s.nextLine();
                            
                            if (Helper.tryParseDate(purchaseDate)!=null){
                                validSubInput=true;
                            }
                        }

                        if(!validSubInput){
                            Helper.cls();
                            System.out.println("==================================================");
                            System.out.println("        Invalid input! Please try again...        ");
                        }
                        else{
                            String sql = String.format("UPDATE transactions SET purchase_date = \'%s\' WHERE transID=%d", purchaseDate, transactionID);
                        
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
                        System.out.println("                 Delete transaction                    ");
                        System.out.println("==================================================");
                        dbconnect.executeSQL("SELECT * FROM view_transactions");
                        dbconnect.printResult();
                        System.out.println("==================================================");
                        System.out.print("Enter transaction ID: ");
                        int transactionID = Helper.tryParseInt(s.nextLine());
                        
                        dbconnect.executeSQL(String.format("SELECT * FROM view_transactions WHERE transID=%d", transactionID));

                        if(!dbconnect.isEmptyRS()){
                            validSubInput=true;
                        }

                        if(!validSubInput){
                            Helper.cls();
                            System.out.println("==================================================");
                            System.out.println("        Invalid input! Please try again...        ");
                        }
                        else{
                            String sql = String.format("DELETE FROM transactions WHERE transID=%d", transactionID);
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
                        System.out.println("                  View transaction                    ");
                        System.out.println("==================================================");
                        dbconnect.executeSQL("SELECT * FROM view_transactions");
                        dbconnect.printResult();
                        System.out.println("==================================================");
                        System.out.print("Enter transaction ID: ");
                        int transactionID = Helper.tryParseInt(s.nextLine());
                        
                        dbconnect.executeSQL(String.format("SELECT * FROM view_transactions WHERE transID=%d", transactionID));

                        if(!dbconnect.isEmptyRS()){
                            validSubInput=true;
                        }

                        if(!validSubInput){
                            Helper.cls();
                            System.out.println("==================================================");
                            System.out.println("        Invalid input! Please try again...        ");
                        }
                        else{
                            String sql = String.format("SELECT * FROM transactions WHERE transID=%d", transactionID);
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
                        System.out.println("  List transactions with specific purchase date   ");
                        System.out.println("==================================================");
                        System.out.print("Enter transaction's purchase date: ");
                        String transactionDate = s.nextLine();
                        
                        if(Helper.tryParseDate(transactionDate)!=null){
                            validSubInput=true;
                        }


                        if(!validSubInput){
                            Helper.cls();
                            System.out.println("==================================================");
                            System.out.println("        Invalid input! Please try again...        ");
                        }
                        else{
                            
                            System.out.println("==================================================");
                            dbconnect.executeSQL(String.format("SELECT * FROM view_transactions WHERE purchase_date=\'%s\'", transactionDate));
                            dbconnect.printResult();
                            System.out.println("==================================================");
                            System.out.print("Enter anything to continue... ");
                            s.nextLine();
                        }
                    }
                    break;
                case 6:
                    //special - bought tickets by visitor
                    Helper.cls();

                    validSubInput=false;

                    while(!validSubInput){
                        System.out.println("==================================================");
                        System.out.println("             Visitor's bought tickets             ");
                        System.out.println("==================================================");
                        dbconnect.executeSQL("SELECT * FROM view_transactions");
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
                            String sql = String.format("SELECT\ttr.transID AS Transaction_ID,\n" + //
                                                                "\t\tt.ticketID AS TicketID_Bought,\n" + //
                                                                "\t\ttr.purchase_date AS Date_of_Transaction,\n" + //
                                                                "        CONCAT(v.first_name, ' ', v.last_name) AS Visitor_Name\n" + //
                                                                "        \n" + //
                                                                "FROM transactions tr\n" + //
                                                                "JOIN tickets t ON tr.ticketID = t.ticketID\n" + //
                                                                "JOIN visitors v ON tr.visitorID = v.visitorID\n" + //
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
        System.out.println("            Transaction Record Management              ");
        System.out.println("==================================================");
        dbconnect.executeSQL("SELECT * FROM view_transactions");
        dbconnect.printResult();
        System.out.println("==================================================");
        System.out.println("\t1 . . . Add new transaction");
        System.out.println("\t2 . . . Update transaction's purchase date");
        System.out.println("\t3 . . . Delete transaction");
        System.out.println("\t4 . . . View specific transaction");
        System.out.println("\t5 . . . List transactions with specific purchase date");
        System.out.println("\t6 . . . List tickets bought by specific visitor");

        System.out.println("\n\t7 . . . Cancel");
        System.out.println("==================================================");
        System.out.print(" Select option: ");
    }

}