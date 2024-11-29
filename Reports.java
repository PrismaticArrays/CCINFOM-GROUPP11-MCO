import java.util.Scanner;

class Reports {
    public DBConnection dbconnect;

    public Reports(DBConnection dbconnect){
        this.dbconnect = dbconnect;
    }

    public void showMenu(Scanner s) throws Exception{
        int option=-1;
        while(option!=3){
            printMenu();

            String input = s.nextLine();
            option = Helper.tryParseInt(input);
            
            switch(option){
                case 1:
                    //profit report
                    Helper.cls();
                    boolean validSubInput = false;
                    int year=0;

                    while(!validSubInput){
                        System.out.println("==================================================");
                        System.out.println("              Attraction Sales Report             ");
                        System.out.println("==================================================");
                        System.out.print("Enter year for monthly profit report: ");
                        year = Helper.tryParseInt(s.nextLine());

                        if(year>0){
                            validSubInput=true;
                        }
                        else{
                            Helper.cls();
                            System.out.println("==================================================");
                            System.out.println("        Invalid input! Please try again...        ");
                        }
                    }



                    Helper.cls();
                    System.out.println("========================================================================");
                    System.out.printf("                   Attraction Sales Report: Year %d        \n", year);
                    System.out.println("========================================================================");
                    
                    int yearSumTickets = 0;
                    float yearSumSales = 0;

                    for(int month=1; month<=12; month++){
                        //uses view for easy use of sum()
                        dbconnect.executeSQLNoRS(String.format("CREATE OR REPLACE VIEW monthlyreport AS\n" + //
                                                        "SELECT \ta.attractionName AS Attraction_Name,\n" + //
                                                        "\t\ta.ticketPrice AS Ticket_Price,\n" + //
                                                        "\t\tCOUNT(a.ticketPrice) AS Tickets_Sold,\n" + //
                                                        "        SUM(a.ticketPrice) AS Ticket_Sales\n" + //
                                                        "FROM attractions a\n" + //
                                                        "JOIN transactions tr ON a.attractionID = tr.attractionID\n" + //
                                                        "WHERE month(tr.purchase_date) = %d AND year(tr.purchase_date)=%d\n" + //
                                                        "GROUP BY a.attractionName, Ticket_Price;", month, year));
                        dbconnect.executeSQL("SELECT * FROM monthlyreport");

                        System.out.printf(">> %s, %d <<\n", returnMonthName(month), year);
                        System.out.println("========================================================================");
                        dbconnect.printResult();

                        dbconnect.executeSQL("SELECT sum(Tickets_Sold) FROM monthlyreport");
                        int monthSumTickets = Helper.tryParseInt(dbconnect.getFirstValueAtColumn("sum(Tickets_Sold)"));
                        yearSumTickets += monthSumTickets;

                        dbconnect.executeSQL("SELECT sum(Ticket_Sales) FROM monthlyreport");
                        float monthSumSales = Helper.tryParseFloat(dbconnect.getFirstValueAtColumn("sum(Ticket_Sales)"));
                        yearSumSales += monthSumSales;
                    
                        System.out.println("                                        ------------        ------------");
                        System.out.printf("                                        %-12d        %-10.2f\n", monthSumTickets, monthSumSales);
                        System.out.println("========================================================================");
                    }

                    dbconnect.executeSQL("SELECT (sum(salary)*12) AS yearly_summary FROM view_staff");
                        
                    float yearSalary = Helper.tryParseFloat(dbconnect.getFirstValueAtColumn("yearly_summary"));

                    System.out.printf(">>> Year %d Overview <<<\n\n", year);
                    System.out.printf("-> Total Sold Tickets:\t %d\n", yearSumTickets);
                    System.out.printf("-> Total Sales:\t\t %.2f\n", yearSumSales);
                    System.out.printf("-> Total Salary:\t %.2f\n", yearSalary);

                    System.out.printf("\n-> Net Profit:\t\t %.2f\n", yearSumSales-yearSalary);
                    System.out.println("========================================================================");
                    System.out.print("Enter anything to continue... ");
                    s.nextLine();

                    break;
                case 2:
                    //visitor report
                    Helper.cls();
                    
                    validSubInput = false;
                    String date="";

                    while(!validSubInput){
                        System.out.println("==================================================");
                        System.out.println("               Customer Visit Report              ");
                        System.out.println("==================================================");
                        System.out.print("Enter date for customer visit report: ");
                        date = s.nextLine();

                        if(Helper.tryParseDate(date)!=null){
                            validSubInput=true;
                        }
                        else{
                            Helper.cls();
                            System.out.println("==================================================");
                            System.out.println("        Invalid input! Please try again...        ");
                        }
                    }

                    Helper.cls();
                    System.out.println("======================================================");
                    System.out.printf("           Customer Visit Report: %s        \n", date);
                    System.out.println("======================================================");
                    dbconnect.executeSQL(String.format("SELECT t.ticketID, CONCAT(v.first_name, ' ', v.last_name) AS Visitor_Name, a.attractionName FROM tickets t \n" + //
                                                "INNER JOIN transactions tr ON t.ticketID = tr.ticketID \n" + //
                                                "INNER JOIN visitors v on v.visitorID = tr.visitorID\n" + //
                                                "INNER JOIN attractions a on a.attractionID = tr.attractionID\n" + //
                                                "WHERE use_date = \'%s\';", date));
                    
                    dbconnect.printResult();

                    
                    System.out.println("======================================================");
                    System.out.print("Enter anything to continue... ");
                    s.nextLine();
                    Helper.cls();

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
        System.out.println("                Reports Generation                ");
        System.out.println("==================================================");
        System.out.println("\t1 . . . Attraction Sales Report");
        System.out.println("\t2 . . . Customer Visit Report");

        System.out.println("\n\t3 . . . Cancel");
        System.out.println("==================================================");
        System.out.print(" Select option: ");
    }

    public String returnMonthName(int num){
        String result = "";
        switch(num){
            case 1:
                result = "JANUARY";
                break;
            case 2:
                result = "FEBRUARY";
                break;
            case 3:
                result = "MARCH";
                break;
            case 4:
                result = "APRIL";
                break;
            case 5:
                result = "MAY";
                break;
            case 6:
                result = "JUNE";
                break;
            case 7:
                result = "JULY";
                break;
            case 8:
                result = "AUGUST";
                break;
            case 9:
                result = "SEPTEMBER";
                break;
            case 10:
                result = "OCTOBER";
                break;
            case 11:
                result = "NOVEMBER";
                break;
            case 12:
                result = "DECEMBER";
                break;
            default:
                result = "ERROR!";
                break;

        }
        return result;
    }

}