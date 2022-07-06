package MCO2;

public class TextUI {

    public void mainMenu(){

        System.out.println("");
        System.out.println("=====================================");
        System.out.println("Medical Laboratory Information System");
        System.out.println("[1] Manage Patient Records");
        System.out.println("[2] Manage Services");
        System.out.println("[3] Manage Laboratory Results");
        System.out.println("");
        System.out.print("Select Transactions: ");

    }

    public void p1Menu(){

        System.out.println("");
        System.out.println("____________________________________");
        System.out.println("Manage Patient Records");
        System.out.println("[1] Add New Patient");
        System.out.println("[2] Search Patient Record");
        System.out.println("[3] Edit Patient Record");
        System.out.println("[4] Delete Patient Record");
        System.out.println("[0] Return to Main Menu");
        System.out.println("");
        System.out.print("Select Transactions: ");

    }

    public void s1Menu(){

        System.out.println("");
        System.out.println("____________________________________");
        System.out.println("Manage Services");
        System.out.println("[1] Add Services");
        System.out.println("[2] Search Services");
        System.out.println("[3] Delete Services");
        System.out.println("[4] Edit Services");
        System.out.println("[0] Return Menu");
        System.out.println("");
        System.out.print("Select Transactions: ");

    }

    public void l1Menu(){

        System.out.println("");
        System.out.println("____________________________________");
        System.out.println("Manage Laboratory Requests");
        System.out.println("[1] Add New Laboratory Request");
        System.out.println("[2] Search Laboratory Requests");
        System.out.println("[3] Delete Laboratory Requests");
        System.out.println("[4] Edit Laboratory Requests");
        System.out.println("[0] Return Menu");
        System.out.println("");
        System.out.print("Select Transactions: ");

    }


    public void pickMenu(int c1){

        switch (c1){
            case 1: p1Menu();
                break;
            case 2: s1Menu();
                break;
            case 3: l1Menu();
                break;
            default: {
                System.out.println("Invalid input!");
                break;
            }

        }
    }

}
