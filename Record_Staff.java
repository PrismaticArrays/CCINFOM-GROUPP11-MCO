import java.util.Scanner;

class Record_Staff {
    public Record_Staff(){

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
                        System.out.println("                  Add new staff                   ");
                        System.out.println("==================================================");
                        dbconnect.executeSQL("SELECT * FROM view_staff");
                        dbconnect.printResult();
                        System.out.println("==================================================");
                        System.out.print("Enter staff's last name (max 20 characters): ");
                        String lastName = s.nextLine();
                        String firstName = "";
                        String employmentDate = "";
                        float salary = 0;

                        if(lastName.length()<=20){
                            System.out.print("Enter staff's first name (max 20 characters): ");
                            firstName = s.nextLine();

                            if(firstName.length()<=20){
                                System.out.print("Enter staff's employment date: ");
                                employmentDate = s.nextLine();

                                if(Helper.tryParseDate(employmentDate)!=null){
                                    System.out.print("Enter staff's salary: ");
                                    salary = Helper.tryParseFloat(s.nextLine());

                                    if(salary>0){
                                        validSubInput=true;
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
                            String sql = String.format("INSERT INTO staff (last_name, first_name, emp_date, salary) VALUES (\'%s\', \'%s\', \'%s\', %.2f)", lastName, firstName, employmentDate, salary);
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
                        System.out.println("              Update staff's salary               ");
                        System.out.println("==================================================");
                        dbconnect.executeSQL("SELECT * FROM view_staff");
                        dbconnect.printResult();
                        System.out.println("==================================================");
                        System.out.print("Enter staff ID: ");
                        int staffID = Helper.tryParseInt(s.nextLine());
                        float salary = 0;
                        
                        dbconnect.executeSQL(String.format("SELECT * FROM view_staff WHERE staffID=%d", staffID));

                        if(!dbconnect.isEmptyRS()){
                            System.out.print("Enter staff's salary: ");
                            salary = Helper.tryParseFloat(s.nextLine());

                            if(salary>0){
                                validSubInput=true;
                            }
                        }

                        if(!validSubInput){
                            Helper.cls();
                            System.out.println("==================================================");
                            System.out.println("        Invalid input! Please try again...        ");
                        }
                        else{
                            String sql = String.format("UPDATE staff SET salary=%.2f WHERE staffID=%d", salary, staffID);
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
                        System.out.println("                 Delete staff                   ");
                        System.out.println("==================================================");
                        dbconnect.executeSQL("SELECT * FROM view_staff");
                        dbconnect.printResult();
                        System.out.println("==================================================");
                        System.out.print("Enter staff ID: ");
                        int staffID = Helper.tryParseInt(s.nextLine());
                        
                        dbconnect.executeSQL(String.format("SELECT * FROM view_staff WHERE staffID=%d", staffID));

                        if(!dbconnect.isEmptyRS()){
                            validSubInput=true;
                        }

                        if(!validSubInput){
                            Helper.cls();
                            System.out.println("==================================================");
                            System.out.println("        Invalid input! Please try again...        ");
                        }
                        else{
                            String sql = String.format("DELETE FROM staff WHERE staffID=%d", staffID);
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
                        System.out.println("                   View staff                     ");
                        System.out.println("==================================================");
                        dbconnect.executeSQL("SELECT * FROM view_staff");
                        dbconnect.printResult();
                        System.out.println("==================================================");
                        System.out.print("Enter staff ID: ");
                        int staffID = Helper.tryParseInt(s.nextLine());
                        
                        dbconnect.executeSQL(String.format("SELECT * FROM view_staff WHERE staffID=%d", staffID));

                        if(!dbconnect.isEmptyRS()){
                            validSubInput=true;
                        }

                        if(!validSubInput){
                            Helper.cls();
                            System.out.println("==================================================");
                            System.out.println("        Invalid input! Please try again...        ");
                        }
                        else{
                            String sql = String.format("SELECT * FROM staff WHERE staffID=%d", staffID);
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
                        System.out.println("             List staff with salary...            ");
                        System.out.println("==================================================");
                        System.out.println("\t1 . . . less than or equal to X");
                        System.out.println("\t2 . . . greater than X");
                        System.out.println("==================================================");
                        System.out.print("Enter option: ");
                        int subOption = Helper.tryParseInt(s.nextLine());

                        float salary = 0;
                        
                        if(subOption==1 || subOption==2){
                            System.out.print("Enter salary: ");
                            salary = Helper.tryParseFloat(s.nextLine()); 

                            if(salary>0){
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
                                dbconnect.executeSQL(String.format("SELECT * FROM staff WHERE salary<=%.2f", salary)); 
                            }
                            else{
                                dbconnect.executeSQL(String.format("SELECT * FROM staff WHERE salary>%.2f", salary));
                            }
                            dbconnect.printResult();
                            System.out.println("==================================================");
                            System.out.print("Enter anything to continue... ");
                            s.nextLine();
                        }
                    }
                    break;
                case 6:
                    //special - staff tickets
                    Helper.cls();

                    validSubInput=false;

                    while(!validSubInput){
                        System.out.println("==================================================");
                        System.out.println("            Staff's transacted tickets            ");
                        System.out.println("==================================================");
                        dbconnect.executeSQL("SELECT * FROM view_staff");
                        dbconnect.printResult();
                        System.out.println("==================================================");
                        System.out.print("Enter staff ID: ");
                        int staffID = Helper.tryParseInt(s.nextLine());
                        
                        dbconnect.executeSQL(String.format("SELECT * FROM view_staff WHERE staffID=%d", staffID));

                        if(!dbconnect.isEmptyRS()){
                            validSubInput=true;
                        }

                        if(!validSubInput){
                            Helper.cls();
                            System.out.println("==================================================");
                            System.out.println("        Invalid input! Please try again...        ");
                        }
                        else{
                            String sql = String.format("SELECT\tCONCAT(s.first_name, ' ', s.last_name) AS Staff_Name,\n" + //
                                                                "\t\tt.ticketID AS IDs_of_Tickets_Transacted\n" + //
                                                                "FROM staff s\n" + //
                                                                "JOIN transactions tr ON s.staffID = tr.staffID\n" + //
                                                                "JOIN tickets t ON tr.ticketID = t.ticketID\n" + //
                                                                "WHERE s.staffID = %d;", staffID);
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
        System.out.println("             Staff Record Management              ");
        System.out.println("==================================================");
        dbconnect.executeSQL("SELECT * FROM view_staff");
        dbconnect.printResult();
        System.out.println("==================================================");
        System.out.println("\t1 . . . Add new staff");
        System.out.println("\t2 . . . Update staff's name");
        System.out.println("\t3 . . . Delete staff");
        System.out.println("\t4 . . . View specific staff");
        System.out.println("\t5 . . . List staff with with salary lesser/greater than X");
        System.out.println("\t6 . . . List staff's transacted tickets");

        System.out.println("\n\t7 . . . Cancel");
        System.out.println("==================================================");
        System.out.print(" Select option: ");
    }

}