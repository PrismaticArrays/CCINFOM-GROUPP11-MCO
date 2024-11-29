import java.util.Scanner;

class RecordsManagement {
    public DBConnection dbconnect;
    public Record_Attractions rec_attractions;
    public Record_Visitors rec_visitors;
    public Record_Tickets rec_tickets;
    public Record_Staff rec_staff;
    public Record_Transactions rec_transactions;
    public Record_Assignments rec_assignments;

    public RecordsManagement(DBConnection dbconnect){
        this.dbconnect = dbconnect;
        this.rec_attractions = new Record_Attractions();
        this.rec_visitors = new Record_Visitors();
        this.rec_tickets = new Record_Tickets();
        this.rec_staff = new Record_Staff();
        this.rec_transactions = new Record_Transactions();
        this.rec_assignments = new Record_Assignments();
    }

    public void showMenu(Scanner s) throws Exception{
        int option=-1;
        while(option!=7){
            printMenu();

            String input = s.nextLine();
            option = Helper.tryParseInt(input);
            
            switch(option){
                case 1:
                    //attractions record
                    Helper.cls();
                    rec_attractions.showMenu(s, dbconnect);
                    break;
                case 2:
                    //visitor record
                    Helper.cls();
                    rec_visitors.showMenu(s, dbconnect);
                    break;
                case 3:
                    //ticket record
                    Helper.cls();
                    rec_tickets.showMenu(s, dbconnect);
                    break;
                case 4:
                    //staff record
                    Helper.cls();
                    rec_staff.showMenu(s, dbconnect);
                    break;
                case 5:
                    //transactions record
                    Helper.cls();
                    rec_transactions.showMenu(s, dbconnect);
                    break;
                case 6:
                    //assignment record
                    Helper.cls();
                    rec_assignments.showMenu(s, dbconnect);
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

    public void printMenu(){
        System.out.println("==================================================");
        System.out.println("                Records Management                ");
        System.out.println("==================================================");
        System.out.println("\t1 . . . Attractions Record");
        System.out.println("\t2 . . . Visitor Record");
        System.out.println("\t3 . . . Ticket Record");
        System.out.println("\t4 . . . Staff Record");
        System.out.println("\t5 . . . Transaction Record");
        System.out.println("\t6 . . . Assignment Record");

        System.out.println("\n\t7 . . . Cancel");
        System.out.println("==================================================");
        System.out.print(" Select option: ");
    }

}