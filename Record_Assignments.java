import java.time.*;
import java.util.Scanner;

class Record_Assignments {
    public Record_Assignments(){

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
                        System.out.println("                Add new assignment                ");
                        System.out.println("==================================================");
                        dbconnect.executeSQL("SELECT * FROM view_assignments");
                        dbconnect.printResult();
                        System.out.println("==================================================");
                        System.out.print("Enter staff ID: ");
                        int staffID = Helper.tryParseInt(s.nextLine());
                        int attractionID = 0;
                        String from_date = "";
                        String to_date = "";

                        if(staffID>0){
                            dbconnect.executeSQL(String.format("SELECT * FROM view_staff WHERE staffID=%d", staffID));

                            if(!dbconnect.isEmptyRS()){
                                System.out.print("Enter attraction ID: ");
                                attractionID = Helper.tryParseInt(s.nextLine());

                                if(attractionID>0){
                                    dbconnect.executeSQL(String.format("SELECT * FROM view_staff WHERE staffID=%d", staffID));

                                    if(!dbconnect.isEmptyRS()){
                                        System.out.print("Enter from date: ");
                                        from_date = s.nextLine();

                                        if(Helper.tryParseDate(from_date)!=null){
                                            System.out.print("Enter to date (enter 'null' if staff is currently assigned): ");
                                            to_date = s.nextLine();
                                            
                                            if(to_date.toLowerCase().equals("null")){
                                                validSubInput = true;
                                            }
                                            else if(Helper.tryParseDate(to_date)!=null){
                                                LocalDate date1 = Helper.tryParseDate(from_date);
                                                LocalDate date2 = Helper.tryParseDate(to_date);

                                                //check if date 1 is earlier than date 2
                                                if(date1.compareTo(date2)<0){
                                                    validSubInput = true;
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
                            String sql="";
                            if(to_date.toLowerCase().equals("null")){
                                sql = String.format("INSERT INTO assignments (staffID, attractionID, from_date, to_date) VALUES (%d, %d, \'%s\', NULL)", staffID, attractionID, from_date);
                            }
                            else{
                                sql = String.format("INSERT INTO assignments (staffID, attractionID, from_date, to_date) VALUES (%d, %d, \'%s\', \\'%s\\')", staffID, attractionID, from_date, to_date);
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
                        System.out.println("           Update assignment's to_date            ");
                        System.out.println("==================================================");
                        dbconnect.executeSQL("SELECT * FROM view_assignments");
                        dbconnect.printResult();
                        System.out.println("==================================================");
                        System.out.print("Enter assignment ID: ");
                        int assignmentID = Helper.tryParseInt(s.nextLine());
                        String to_date = "";
                        
                        dbconnect.executeSQL(String.format("SELECT * FROM view_assignments WHERE assignmentID=%d", assignmentID));

                        if(!dbconnect.isEmptyRS()){
                            System.out.print("Enter to date (enter 'null' if staff will be assigned currently): ");
                            to_date = s.nextLine();

                            if(to_date.toLowerCase().equals("null")){
                                validSubInput = true;
                            }
                            else if (Helper.tryParseDate(to_date)!=null) {

                                String from_date = dbconnect.getFirstValueAtColumn("from_date");

                                LocalDate date1 = Helper.tryParseDate(from_date);
                                LocalDate date2 = Helper.tryParseDate(to_date);

                                //check if date 1 is earlier than date 2
                                if(date1.compareTo(date2)<0){
                                    validSubInput = true;
                                }
                            }
                        }

                        if(!validSubInput){
                            Helper.cls();
                            System.out.println("==================================================");
                            System.out.println("        Invalid input! Please try again...        ");
                        }
                        else{
                            String sql="";
                            if(to_date.toLowerCase().equals("null")){
                                sql = String.format("UPDATE assignments SET to_date=NULL WHERE assignmentID=%d", assignmentID);
                            }
                            else{
                                sql = String.format("UPDATE assignments SET to_date=\'%s\' WHERE assignmentID=%d", to_date, assignmentID);
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
                        System.out.println("                 Delete assignment                   ");
                        System.out.println("==================================================");
                        dbconnect.executeSQL("SELECT * FROM view_assignments");
                        dbconnect.printResult();
                        System.out.println("==================================================");
                        System.out.print("Enter assignment ID: ");
                        int assignmentID = Helper.tryParseInt(s.nextLine());
                        
                        dbconnect.executeSQL(String.format("SELECT * FROM view_assignments WHERE assignmentID=%d", assignmentID));

                        if(!dbconnect.isEmptyRS()){
                            validSubInput=true;
                        }

                        if(!validSubInput){
                            Helper.cls();
                            System.out.println("==================================================");
                            System.out.println("        Invalid input! Please try again...        ");
                        }
                        else{
                            String sql = String.format("DELETE FROM assignments WHERE assignmentID=%d", assignmentID);
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
                        System.out.println("                   View assignment                     ");
                        System.out.println("==================================================");
                        dbconnect.executeSQL("SELECT * FROM view_assignments");
                        dbconnect.printResult();
                        System.out.println("==================================================");
                        System.out.print("Enter assignment ID: ");
                        int assignmentID = Helper.tryParseInt(s.nextLine());
                        
                        dbconnect.executeSQL(String.format("SELECT * FROM view_assignments WHERE assignmentID=%d", assignmentID));

                        if(!dbconnect.isEmptyRS()){
                            validSubInput=true;
                        }

                        if(!validSubInput){
                            Helper.cls();
                            System.out.println("==================================================");
                            System.out.println("        Invalid input! Please try again...        ");
                        }
                        else{
                            String sql = String.format("SELECT * FROM view_assignments WHERE assignmentID=%d", assignmentID);
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
                        System.out.println("       List assignment with specific to_date      ");
                        System.out.println("==================================================");
                        System.out.print("Enter to date (enter 'null' if staff will be assigned currently): ");
                        String to_date = s.nextLine();

                        if(to_date.toLowerCase().equals("null")){
                            validSubInput=true;
                        }
                        else if(Helper.tryParseDate(to_date)!=null){
                            validSubInput=true;
                        }


                        if(!validSubInput){
                            Helper.cls();
                            System.out.println("==================================================");
                            System.out.println("        Invalid input! Please try again...        ");
                        }
                        else{
                            
                            System.out.println("==================================================");
                            if(to_date.toLowerCase().equals("null")){
                                dbconnect.executeSQL(String.format("SELECT * FROM view_assignments WHERE to_date IS NULL")); 
                            }
                            else{
                                dbconnect.executeSQL(String.format("SELECT * FROM view_assignments WHERE to_date =\'%s\'", to_date));
                            }
                            dbconnect.printResult();
                            System.out.println("==================================================");
                            System.out.print("Enter anything to continue... ");
                            s.nextLine();
                        }
                    }
                    break;
                case 6:
                    //special - assignment profits
                    Helper.cls();

                    validSubInput=false;

                    while(!validSubInput){
                        System.out.println("==================================================");
                        System.out.println("           Attraction's assigned staff            ");
                        System.out.println("==================================================");
                        dbconnect.executeSQL("SELECT * FROM view_assignments");
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
                            String sql = String.format("SELECT\ta.attractionName AS Attraction,\n" + //
                                                                "\t\tCONCAT(s.first_name, ' ', s.last_name) AS Staff_Assigned\n" + //
                                                                "FROM assignments am\n" + //
                                                                "JOIN staff s ON am.staffID = s.staffID\n" + //
                                                                "JOIN attractions a ON am.attractionID = a.attractionID\n" + //
                                                                "WHERE a.attractionID = %d AND am.to_date IS NULL;", attractionID);
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
        System.out.println("           Assignment Record Management           ");
        System.out.println("==================================================");
        dbconnect.executeSQL("SELECT * FROM view_assignments");
        dbconnect.printResult();
        System.out.println("==================================================");
        System.out.println("\t1 . . . Add new assignment");
        System.out.println("\t2 . . . Update assignment's to_date");
        System.out.println("\t3 . . . Delete assignment");
        System.out.println("\t4 . . . View specific assignment");
        System.out.println("\t5 . . . List assignment with specific to_date");
        System.out.println("\t6 . . . List staff currently assigned to specific attraction");

        System.out.println("\n\t7 . . . Cancel");
        System.out.println("==================================================");
        System.out.print(" Select option: ");
    }

}