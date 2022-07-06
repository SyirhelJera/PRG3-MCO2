package MCO2;

import java.io.IOException;
import java.util.Scanner;

public class Logic {
    MCO2.TextUI ui = new TextUI();
    Scanner sc = new Scanner(System.in);


    PatientRecord pr = new PatientRecord();
    Laboratory laboratory = new Laboratory();
    Service services = new Service();

    public void start() throws IOException {
        Boolean running = true;
        int c1,c2;


        while(running){
            ui.mainMenu();
            c1 = sc.nextInt();
            ui.pickMenu(c1);
            c2 = sc.nextInt();
            director(c1,c2);
        }

    }
    // INITIALIZES THE PROGRAM
    public void director(int c1, int c2) throws IOException {
        if (c1 == 1){                                       // Patients
            if (c2 == 1){pr.addPatient();}                  // Add Patients
            else if (c2 == 2){pr.searchPatient();}          // Search Patients
            else if (c2 == 3){pr.editPatient();}            // Edit Patients
            else if (c2 == 4){pr.deletePatient();}          // Delete Patients
        }

        else if (c1 == 2){ //Services
            if (c2 == 1){services.addService();}            // Add Services
            else if (c2 == 2){services.searchService();}   // search Services
            else if (c2 == 3){services.deleteService();}    // edit Services
            else if  (c2 == 4){services.editService();}     // delete Services
        }

        else if (c1 == 3){
            if (c2 == 1){laboratory.addRequest();}            // Add Laboratory Result
            else if (c2 == 2){laboratory.searchRequest();}   // search Laboratory Results
            //else if (c2 == 3){laboratory.deleteRequest();}     // delete Laboratory Results
            //else if (c2 == 4){laboratory.editRequest();}    // edit Laboratory Results

        }

    }
}
