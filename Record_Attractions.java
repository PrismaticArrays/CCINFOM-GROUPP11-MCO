import java.util.Scanner;

class Record_Attractions {
    public Record_Attractions(){

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
                        System.out.println("               Add new attraction                 ");
                        System.out.println("==================================================");
                        dbconnect.executeSQL("SELECT * FROM view_attractions");
                        dbconnect.printResult();
                        System.out.println("==================================================");
                        System.out.print("Enter attraction name (max 20 characters): ");
                        String attractionName = s.nextLine();

                        float ticketPrice = 0;

                        if(attractionName.length()<=20){
                            System.out.print("Enter ticket price: ");
                            ticketPrice = Helper.tryParseFloat(s.nextLine());
                            
                            
                            if(ticketPrice>0){
                                validSubInput=true;
                            }
                        }

                        if(!validSubInput){
                            Helper.cls();
                            System.out.println("==================================================");
                            System.out.println("        Invalid input! Please try again...        ");
                        }
                        else{
                            String sql = String.format("INSERT INTO attractions (attractionName, ticketPrice) VALUES (\'%s\', %.2f)", attractionName, ticketPrice);
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
                        System.out.println("               Update ticket price                ");
                        System.out.println("==================================================");
                        dbconnect.executeSQL("SELECT * FROM view_attractions");
                        dbconnect.printResult();
                        System.out.println("==================================================");
                        System.out.print("Enter attraction ID: ");
                        int attractionID = Helper.tryParseInt(s.nextLine());
                        float ticketPrice = 0;
                        
                        dbconnect.executeSQL(String.format("SELECT * FROM view_attractions WHERE attractionID=%d", attractionID));

                        if(!dbconnect.isEmptyRS()){
                            System.out.print("Enter new ticket price: ");
                            ticketPrice = Helper.tryParseFloat(s.nextLine());

                            if(ticketPrice>0){
                                validSubInput=true;
                            }
                        }

                        if(!validSubInput){
                            Helper.cls();
                            System.out.println("==================================================");
                            System.out.println("        Invalid input! Please try again...        ");
                        }
                        else{
                            String sql = String.format("UPDATE attractions SET ticketPrice=%.2f WHERE attractionID=%d", ticketPrice, attractionID);
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
                        System.out.println("                Delete attraction                 ");
                        System.out.println("==================================================");
                        dbconnect.executeSQL("SELECT * FROM view_attractions");
                        dbconnect.printResult();
                        System.out.println("==================================================");
                        System.out.print("Enter attraction ID: ");
                        int attractionID = Helper.tryParseInt(s.nextLine());
                        
                        dbconnect.executeSQL(String.format("SELECT * FROM view_attractions WHERE attractionID=%d", attractionID));

                        if(!dbconnect.isEmptyRS()){
                            validSubInput=true;
                        }

                        if(!validSubInput){
                            Helper.cls();
                            System.out.println("==================================================");
                            System.out.println("        Invalid input! Please try again...        ");
                        }
                        else{
                            String sql = String.format("DELETE FROM attractions WHERE attractionID=%d", attractionID);
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
                        System.out.println("                 View attraction                  ");
                        System.out.println("==================================================");
                        dbconnect.executeSQL("SELECT * FROM view_attractions");
                        dbconnect.printResult();
                        System.out.println("==================================================");
                        System.out.print("Enter attraction ID: ");
                        int attractionID = Helper.tryParseInt(s.nextLine());
                        
                        dbconnect.executeSQL(String.format("SELECT * FROM view_attractions WHERE attractionID=%d", attractionID));

                        if(!dbconnect.isEmptyRS()){
                            validSubInput=true;
                        }

                        if(!validSubInput){
                            Helper.cls();
                            System.out.println("==================================================");
                            System.out.println("        Invalid input! Please try again...        ");
                        }
                        else{
                            String sql = String.format("SELECT * FROM attractions WHERE attractionID=%d", attractionID);
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
                        System.out.println("       List attraction with ticket price...       ");
                        System.out.println("==================================================");
                        System.out.println("\t1 . . . less than or equal to X");
                        System.out.println("\t2 . . . greater than X");
                        System.out.println("==================================================");
                        System.out.print("Enter option: ");
                        int subOption = Helper.tryParseInt(s.nextLine());

                        float ticketPrice = 0;
                        
                        if(subOption==1 || subOption==2){
                            System.out.print("Enter ticket price: ");
                            ticketPrice = Helper.tryParseFloat(s.nextLine()); 

                            if(ticketPrice>0){
                                validSubInput=true;
                            }
                        }


                        if(!validSubInput){
                            Helper.cls();
                            System.out.println("==================================================");
                            System.out.println("        Invalid input! Please try again...        ");
                        }
                        else{
                            
                            System.out.println("==================================================");
                            if(subOption==1){
                                dbconnect.executeSQL(String.format("SELECT * FROM attractions WHERE ticketPrice<=%.2f", ticketPrice)); 
                            }
                            else{
                                dbconnect.executeSQL(String.format("SELECT * FROM attractions WHERE ticketPrice>%.2f", ticketPrice));
                            }
                            dbconnect.printResult();
                            System.out.println("==================================================");
                            System.out.print("Enter anything to continue... ");
                            s.nextLine();
                        }
                    }
                    break;
                case 6:
                    //special - attraction profits
                    Helper.cls();

                    validSubInput=false;

                    while(!validSubInput){
                        System.out.println("==================================================");
                        System.out.println("        Attraction ticket sales and profit        ");
                        System.out.println("==================================================");
                        dbconnect.executeSQL("SELECT * FROM view_attractions");
                        dbconnect.printResult();
                        System.out.println("==================================================");
                        System.out.print("Enter attraction ID: ");
                        int attractionID = Helper.tryParseInt(s.nextLine());
                        
                        dbconnect.executeSQL(String.format("SELECT * FROM view_attractions WHERE attractionID=%d", attractionID));

                        if(!dbconnect.isEmptyRS()){
                            validSubInput=true;
                        }

                        if(!validSubInput){
                            Helper.cls();
                            System.out.println("==================================================");
                            System.out.println("        Invalid input! Please try again...        ");
                        }
                        else{
                            String sql = String.format("SELECT \ta.attractionName AS Attraction_Name,\n" + //
                                                                "\t\ta.ticketPrice AS Ticket_Price,\n" + //
                                                                "\t\tCOUNT(a.ticketPrice) AS Tickets_Sold,\n" + //
                                                                "        SUM(a.ticketPrice) AS Ticket_Sales\n" + //
                                                                "FROM attractions a\n" + //
                                                                "JOIN transactions tr ON a.attractionID = tr.attractionID\n" + //
                                                                "AND a.attractionID = %d\n" + //
                                                                "GROUP BY a.attractionName, Ticket_Price;", attractionID);
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
        System.out.println("          Attractions Record Management           ");
        System.out.println("==================================================");
        dbconnect.executeSQL("SELECT * FROM view_attractions");
        dbconnect.printResult();
        System.out.println("==================================================");
        System.out.println("\t1 . . . Add new attraction");
        System.out.println("\t2 . . . Update ticket price");
        System.out.println("\t3 . . . Delete attraction");
        System.out.println("\t4 . . . View specific attraction");
        System.out.println("\t5 . . . List attraction with ticket price lesser/greater than X");
        System.out.println("\t6 . . . View ticket sales and profit of specific attraction");

        System.out.println("\n\t7 . . . Cancel");
        System.out.println("==================================================");
        System.out.print(" Select option: ");
    }

}