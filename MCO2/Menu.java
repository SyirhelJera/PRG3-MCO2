package MCO2;

import java.io.IOException;
import java.util.Scanner;
public class Menu {


    public static void initialize() throws IOException {
            mMenu();
            int c1 = firstInput();
            processInput(c1);
            int choice2 = secondInput();
            processSecond(c1,choice2);
    }
    private static void mMenu() {

        System.out.println("\nMedical Laboratory Information System");
        System.out.println("[1] Manage Patient Records");
        System.out.println("[2] Manage Services");
        System.out.println("[3] Manage Laboratory Results\n");
        System.out.print("Select transaction: ");



    }

    private static void pMenu() {

        System.out.println("\nManage Patient Records");
        System.out.println("[1] Add New Patient");
        System.out.println("[2] Edit Patient Record");
        System.out.println("[3] Delete Patient Record");
        System.out.println("[4] Search Patient Record");
        System.out.println("[0] Return to Main Menu\n");
        System.out.print("Select transaction: ");
    }
    private static void sMenu() {

        System.out.println("\nManage Services");
        System.out.println("[1] Add New Service");
        System.out.println("[2] Edit Service");
        System.out.println("[3] Delete Service");
        System.out.println("[4] Search Service");
        System.out.println("[0] Return to Main Menu\n");
        System.out.print("Select transaction: ");


    }

    private static void lMenu() {

        System.out.println("\nManage Laboratory Request");
        System.out.println("[1] Add New Laboratory Request");
        System.out.println("[2] Edit Laboratory Request");
        System.out.println("[3] Delete Laboratory Request");
        System.out.println("[4] Search Laboratory Request\n");
        System.out.print("Select transaction: ");

    }

    private static int firstInput() {
        Scanner scan = new Scanner(System.in);
        int choice=-1;
        while(choice < 1 || choice > 3){
            try{
                choice =Integer.parseInt(scan.nextLine());
            }
            catch(NumberFormatException e){
                System.out.println("Invalid Option. Please try again");
            }
        }
        return choice;
    }


    private static int secondInput() { // ACCEPTS SECOND USER INPUT AND CHECKS WHETHER THE INPUT IS VALID
        Scanner scan = new Scanner(System.in);
        int choice=-1;
        while(choice < 0 || choice > 4){
            try{
                choice =Integer.parseInt(scan.nextLine());
            }
            catch(NumberFormatException e){
                System.out.println("Invalid Option. Please try again");
            }
        }
        return choice;
    }

    private static void processInput(int choice){ // DIRECTS WHICH MENU TO BE OPENED AFTER FIRST USER INPUT

        switch (choice) {

            case 1 -> pMenu();
            case 2 -> sMenu();
            case 3 -> lMenu();

            default -> System.out.println("An error occurred.");
        }


    }

    private static void processSecond(int c1, int c2) throws IOException {
    /*
     HANDLES THE SECOND USER INPUT PROCESS THAT IS USED TO DETERMINE WHAT METHODS TO CALL
     */
        switch (c1){

            case 1:

                switch (c2) {

                    case 0 -> initialize();
                    case 1 -> PatientRecord.addPatient();
                    case 2 -> PatientRecord.editPatient();
                    case 3 -> PatientRecord.deletePatient();
                    case 4 -> PatientRecord.searchPatient();
                    default -> System.out.println("An error occurred.");

                }

                break;

            case 2:
                switch (c2) {

                    case 0 -> initialize();
                    case 1 -> Service.addService();
                    case 2 -> Service.editService();
                    case 3 -> Service.deleteService();
                    case 4 -> Service.searchService();
                    default -> System.out.println("An error occurred.");

                }
                break;

            case 3:
                switch (c2) {

                    case 0 -> initialize();
                    case 1 -> Laboratory.addRequest();
                    case 2 -> System.out.println("TO FOLLOW2");
                    case 3 -> System.out.println("TO FOLLOW3");
                    case 4 -> System.out.println("TO FOLLOW4");
                    default -> System.out.println("An error occurred.");

                }
                break;

            default:
                System.out.println("An error occurred.");

        }

    }
}
