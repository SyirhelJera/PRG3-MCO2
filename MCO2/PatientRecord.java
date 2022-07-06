/*
 * Major Course Output 1
 * Authors:  Jera,Syirhel Vincent
 *           Marasigan, Marc Daniel
 *
 * Course: CCPROG3
 * SECTION: S12
 *
 * */

package MCO2;

import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class PatientRecord {

    public static Menu menu = new Menu();

    private static Character char1 = 'A';
    private static Character char2 = 'A';
    private static Character char3 = 'A';
    private static Character maxChar = 'Z';
    private static String patientID = "" + char1 + char2 + char3;
    private static Integer int1 = 0;
    private static DecimalFormat twoDigits = new DecimalFormat("00");


    public static DateTimeFormatter year = DateTimeFormatter.ofPattern("yyyy");
    public static DateTimeFormatter month = DateTimeFormatter.ofPattern("MM");
    public static LocalDate dateToday = LocalDate.now(ZoneId.of("Asia/Manila"));
    public static LocalDate nextMonth = dateToday.plusMonths(1);

    public static BufferedWriter writer;
    private static BufferedWriter bw;
    private static BufferedReader bRead;
    private static Scanner x;




    public static void addPatient() throws IOException {

        Scanner scan = new Scanner(System.in);
        if (dateToday.format(month).compareTo(nextMonth.format(month)) == 0) {
            char1 = 'A';
            char2 = 'A';
            char3 = 'A';
            int1 = 0;
            nextMonth = dateToday.plusMonths( 1 );
        }

        System.out.print("First Name: ");
        String firstName = scan.nextLine();

        System.out.print("Middle Name: ");
        String middleName = scan.nextLine();

        System.out.print("Last Name: ");
        String lastName = scan.nextLine();

        System.out.print("Birthday (YYYYMMDD):");
        int bDay = scan.nextInt();

        System.out.print("Gender: ");
        char gen = scan.next().charAt(0);

        String dump="";
        dump+= scan.nextLine();

        System.out.print("Address: ");
        String address="";
        address+= scan.nextLine();

        System.out.print("Phone Number: ");
        String phoneNum = scan.next();

        System.out.print("National ID: ");
        int nID = scan.nextInt();



        System.out.print("Save Patient Record[Y/N?] ");
        char saveRecord = scan.next().charAt(0);



        if (saveRecord == 'Y'){


            try{
                writer = new BufferedWriter(new FileWriter("Patients.txt", true));
                writer.write("P" + dateToday.format(year) + dateToday.format(month) + patientID + twoDigits.format(int1)
                        + ";" + lastName + ";" + firstName + ";" + middleName + ";" + bDay + ";" + gen + ";" + address + ";"
                        + phoneNum + ";" + nID + ";\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


            // SEGMENT THAT GENERATES PATIENT ID
            if(int1 == 99){
                int1 = 0;
                char3++;
                patientID = ""+ char1 + char2 + char3;
            }

            else if(char3 == maxChar){
                char2++;
                char3 = 'A';
                patientID = ""+ char1 + char2 + char3;
            }

            else if(char2 == maxChar) {
                char1++;
                char2 = 'A';
                char3 = 'A';
                patientID = "" + char1 + char2 + char3;
            }

            else {
                int1++;
            }
            System.out.println("Successfully added to the Patient List.");
            Menu.initialize();
        } else if (saveRecord =='N')
            Menu.initialize();
    }



    public static void searchPatient() {

        Scanner sc = new Scanner(System.in);
        System.out.println("Insert Key Word or Patient UID:");
        String searchStr = sc.nextLine();
        File file = new File("Patients.txt");
        Scanner scf = null;
        try {
            scf = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        boolean found = false;
        String[] fLine= new String[100];
        int count = 0;
        while (scf.hasNextLine()){
            String line = scf.nextLine();
            if (line.contains(searchStr)){
                found = true;
                fLine[count] = line;
                count++;
            }
        }


        if(found) {
            System.out.printf(        // Print FORMAT
                    "%-25s" + // pUID
                            "%-25s" + // lName
                            "%-25s" + // fName
                            "%-25s" + // mName
                            "%-25s" + // bDay
                            "%-25s" + // gender
                            "%-25s" + // address
                            "%-25s" + // mNumber
                            "%-25s" + // natID
                            " %n",
                    "Patient UID",
                    "LastName",
                    "FirstName",
                    "MiddleName",
                    "Birthday",
                    "Gender",
                    "Address",
                    "Number",
                    "NationalID");

            // PRINTS THE RETRIEVED DATA
            for (int i = 0; i < count; i++) {
                String[] parts = fLine[i].split(";");
                for (String a : parts) {
                    System.out.printf("%-25s" +
                            "",a);
                }
                System.out.println("");
            }


        }
        else{
            System.out.println("No Record Found");
        }


    }


    public static void deletePatient() throws FileNotFoundException {

        String pUID,pFirst,pLast,pMiddle,pBirth,pGender,pNumber,pAddress,pNatID;

        String searchPID = "";
        String searchNID = "";
        String searchLName = "";
        String searchFName = "";
        String searchBDay = "";

        Scanner sc = new Scanner(System.in);
        int trigger;


        System.out.println("Do you know the UID? [Y/N]");
        char choice = sc.nextLine().charAt(0);
        if(choice == 'Y' || choice == 'y') {

            System.out.print("Enter Patient's UID: ");
            searchPID = sc.nextLine();
            trigger = 1;
        }
        else{
            System.out.println("Do you know the National ID no.? [Y/N]");
            choice = sc.nextLine().charAt(0);
            if(choice == 'Y' || choice == 'y'){
                System.out.println("Enter Patient's NID: ");
                searchNID = sc.nextLine();
                trigger = 2;
            }
            else{
                System.out.println("Enter Last Name: ");
                searchLName = sc.nextLine();

                System.out.println("Enter First Name: ");
                searchFName = sc.nextLine();

                System.out.println("Birthday(YYYYMMDD): ");
                searchBDay = sc.nextLine();

                trigger = 3;
            }
        }

        String tempFile = "dumpfile.txt";
        String filePath = "Patients.txt";

        File file = new File(filePath);
        Scanner scan = new Scanner(file);

        boolean found = false;

        String[] fLine= new String[100];
        int count = 0;

        switch(trigger){
            case 1:
                while (scan.hasNextLine()){

                    String line = scan.nextLine();
                    if (line.contains(searchPID)){
                        found = true;
                        fLine[count] = line;
                        count++;

                    }
                }
                break;

            case 2:
                while (scan.hasNextLine()){

                    String line = scan.nextLine();
                    if (line.contains(searchNID)){
                        found = true;
                        fLine[count] = line;
                        count++;

                    }
                }
                break;

            case 3:
                while (scan.hasNextLine()){

                    String line = scan.nextLine();
                    if (line.contains(searchLName) && line.contains(searchFName) && line.contains(searchBDay)){
                        found = true;
                        fLine[count] = line;
                        count++;

                    }
                }
                break;

            default:
                System.out.println("An error has occurred");
                break;
        }


        if(found && count >=2) {
            System.out.println("Patients found:\n");
            System.out.printf("%-25s" +
                            "%-25s" +
                            "%-25s" +
                            "%-25s" +
                            "%-25s" +
                            "%-25s" +
                            "%-25s" +
                            "%-25s" +
                            "%-25s" +
                            "\n","Patient's UID",
                    "LastName",
                    "FirstName",
                    "MiddleName",
                    "Birthday",
                    "Gender",
                    "Address",
                    "Phone No.",
                    "National ID");

            for (int i = 0; i < count; i++) {

                String[] parts = fLine[i].split(";");
                for (String a : parts) {
                    System.out.printf("%-25s", a);

                }

                System.out.println();
            }


            File newFile = new File(tempFile);

            System.out.println("Enter Patient's UID: ");
            searchPID = sc.nextLine();

            System.out.print("Delete Patient? Y/N: ");
            choice = sc.nextLine().charAt(0);


            if(choice == 'Y' || choice == 'y') {

                System.out.println("Please type the reason for deletion:");
                String reason = sc.nextLine();

                try {
                    FileWriter fWrite = new FileWriter(tempFile, true);
                    writer = new BufferedWriter(fWrite);
                    PrintWriter pWrite = new PrintWriter(writer);
                    scan = new Scanner(new File (filePath));
                    scan.useDelimiter("[;\n]");

                    while (scan.hasNextLine()) {

                        String line = scan.nextLine();
                        String[] parts = line.split(";");
                        pUID = parts[0];
                        pFirst = parts[1];
                        pLast = parts[2];
                        pMiddle = parts[3];
                        pBirth = parts[4];
                        pGender = parts[5];
                        pNumber = parts[6];
                        pAddress = parts[7];
                        pNatID = parts[8];


                        if (pUID.equals(searchPID))
                            pWrite.println(pUID + ";"
                                    + pFirst + ";"
                                    + pLast + ";"
                                    + pMiddle + ";"
                                    + pBirth + ";"
                                    + pGender + ";"
                                    + pNumber + ";"
                                    + pAddress + ";"
                                    + pNatID + ";"
                                    + "D;"
                                    + reason + ";");
                        else
                            pWrite.println(pUID + ";"
                                    + pFirst + ";"
                                    + pLast + ";"
                                    + pMiddle + ";"
                                    + pBirth + ";"
                                    + pGender + ";"
                                    + pNumber + ";"
                                    + pAddress + ";"
                                    + pNatID + ";");
                    }

                    pWrite.flush();
                    pWrite.close();

                    fWrite = new FileWriter(filePath,false);
                    pWrite = new PrintWriter(fWrite,false);

                    pWrite.flush();
                    pWrite.close();
                    fWrite.close();

                    scan.close();

                    FileInputStream fInput = new FileInputStream(newFile);
                    bRead = new BufferedReader(new InputStreamReader(fInput));
                    fWrite = new FileWriter(file,true);
                    writer = new BufferedWriter(fWrite);
                    String line = null;

                    while((line = bRead.readLine()) != null){
                        writer.write(line);
                        writer.newLine();
                    }

                    bRead.close();
                    writer.close();
                    newFile.delete();
                    System.out.println();

                } catch (Exception e) {
                    System.out.println("An Error has occurred");
                    System.out.println();
                }
            }

            else {
                System.out.println();
            }
        }

        else if(found){
            System.out.println("Patient Details:\n");
            System.out.printf("%-25s" +
                            "%-25s" +
                            "%-25s" +
                            "%-25s" +
                            "%-25s" +
                            "%-25s" +
                            "%-25s" +
                            "%-25s" +
                            "%-25s" +
                            "\n",
                    "Patient's UID",
                    "LastName",
                    "FirstName",
                    "MiddleName",
                    "Birthday",
                    "Gender",
                    "Address",
                    "Phone No.",
                    "National ID");

            String[] slice = fLine[count].split(";");
            for (String a : slice) {
                System.out.print(a + "\t\t\t\t\t");
            }

            File newFile = new File(tempFile);
            searchPID = slice[0];
            System.out.println("Delete Service? Y/N");
            choice = sc.nextLine().charAt(0);
            if(choice == 'Y' || choice == 'y') {
                System.out.println("Please type the reason for deletion:");
                String reason = sc.nextLine();
                try {
                    FileWriter fw = new FileWriter(tempFile, true);
                    writer = new BufferedWriter(fw);
                    PrintWriter pw = new PrintWriter(writer);
                    scan = new Scanner(new File (filePath));
                    scan.useDelimiter("[;\n]");

                    while (scan.hasNextLine()) {
                        String line = scan.nextLine();
                        slice = line.split(";");
                        pUID = slice[0];
                        pFirst = slice[1];
                        pLast = slice[2];
                        pMiddle = slice[3];
                        pBirth = slice[4];
                        pGender = slice[5];
                        pNumber = slice[6];
                        pAddress = slice[7];
                        pNatID = slice[8];


                        if (pUID.equals(searchPID))
                            pw.println(pUID + ";"
                                    + pFirst + ";"
                                    + pLast + ";"
                                    + pMiddle + ";"
                                    + pBirth + ";"
                                    + pGender + ";"
                                    + pNumber + ";"
                                    + pAddress + ";"
                                    + pNatID + ";"
                                    + "D;"
                                    + reason + ";");
                        else
                            pw.println(pUID + ";"
                                    + pFirst + ";"
                                    + pLast + ";"
                                    + pMiddle + ";"
                                    + pBirth + ";"
                                    + pGender + ";"
                                    + pNumber + ";"
                                    + pAddress + ";"
                                    + pNatID + ";");
                    }
                    pw.flush();
                    pw.close();
                    fw = new FileWriter(filePath,false);
                    pw = new PrintWriter(fw,false);
                    pw.flush();
                    pw.close();
                    fw.close();

                    scan.close();

                    FileInputStream fis = new FileInputStream(newFile);
                    bRead = new BufferedReader(new InputStreamReader(fis));
                    fw = new FileWriter(file,true);
                    writer = new BufferedWriter(fw);
                    String line = null;
                    while((line = bRead.readLine()) != null){
                        writer.write(line);
                        writer.newLine();
                    }
                    bRead.close();
                    writer.close();
                    newFile.delete();
                    System.out.println();
                } catch (Exception e) {
                    System.out.println("An Error has occurred");
                    System.out.println();
                }
            }
            else {
                System.out.println();
            }
        }

        else{
            System.out.println("No Record Found");
            System.out.println("Search Again? Y/N");
            choice = sc.nextLine().charAt(0);
            if(choice == 'Y' || choice == 'y') {
                deletePatient();
            }
        }
    }



    public static void editPatient() throws IOException {

        // LOCAL VARIABLES
        String pUID,pLast, pFirst,pMiddle,pBirthday,pGender,pAddress,pNumber,pNatID;
        String patientIDf="";


        Scanner sc = new Scanner(System.in);
        System.out.println("Patient ID or Name:");
        String searchStr = sc.nextLine();
        String tempFile = "dumpfile.txt";
        String filePath = "Patients.txt";
        File file = new File(filePath);
        x = new Scanner(file);
        boolean found = false;
        String[] fLine= new String[100];
        int count = 0;
        while (x.hasNextLine()){
            String line = x.nextLine();
            if (line.contains(searchStr)){
                found = true;
                fLine[count] = line;
                count++;
            }
        }
        if(found) {
            System.out.printf("%-25s" +
                            "%-25s" +
                            "%-25s" +
                            "%-25s" +
                            "%-25s" +
                            "%-25s" +
                            "%-25s" +
                            "%-25s" +
                            "%-25s" +
                            " %n",
                    "Patient UID", "Last Name", "First Name", "Middle Name", "Birthday", "Gender", "Address",
                    "Phone Number", "National ID");

            for (int i = 0; i < count; i++) {
                String[] parts = fLine[i].split(";");
                for (String a : parts) {
                    System.out.printf("%-25s" +
                            "", a);
                }
                System.out.println();
            }

            File newFile = new File(tempFile);

            System.out.println("\nWhat data would you like edit? Input [A] for Address/ [B] for Phone Number");
            char choice = sc.nextLine().charAt(0);

            if (choice == 'A' || choice == 'a') {
                System.out.println("Please type the Patient ID that you would like to edit:");
                searchStr = sc.nextLine();

                System.out.println("Please type the updated data:");
                String newAddress = sc.nextLine();

                System.out.println("Edit record? Y/N");
                choice = sc.nextLine().charAt(0);

                if (choice == 'Y' || choice == 'y') {
                    try {
                        FileWriter fw = new FileWriter(tempFile, true);
                        bw = new BufferedWriter(fw);
                        PrintWriter pw = new PrintWriter(bw);
                        x = new Scanner(new File(filePath));
                        x.useDelimiter("[;\n]");

                        while (x.hasNextLine()) {
                            String line = x.nextLine();
                            String[] parts = line.split(";");
                            pUID = parts[0];
                            pLast = parts[1];
                            pFirst = parts[2];
                            pMiddle = parts[3];
                            pBirthday = parts[4];
                            pGender = parts[5];
                            pAddress = parts[6];
                            pNumber = parts[7];
                            pNatID = parts[8];


                            if (pUID.equals(searchStr)) {
                                patientIDf = pUID;
                                pw.println(pUID + ";"
                                        + pLast + ";"
                                        + pFirst + ";"
                                        + pMiddle + ";"
                                        + pBirthday + ";"
                                        + pGender + ";"
                                        + newAddress + ";"
                                        + pNumber + ";"
                                        + pNatID + ";");
                            } else
                                pw.println(pUID + ";"
                                        + pLast + ";"
                                        + pFirst + ";"
                                        + pMiddle + ";"
                                        + pBirthday + ";"
                                        + pGender + ";"
                                        + pAddress + ";"
                                        + pNumber + ";"
                                        + pNatID + ";");
                        }


                        pw.flush();
                        pw.close();
                        fw = new FileWriter(filePath, false);
                        pw = new PrintWriter(fw, false);
                        pw.flush();
                        pw.close();
                        fw.close();

                        x.close();

                        FileInputStream fis = new FileInputStream(newFile);
                        bRead = new BufferedReader(new InputStreamReader(fis));
                        fw = new FileWriter(file, true);
                        bw = new BufferedWriter(fw);
                        String line = null;
                        while ((line = bRead.readLine()) != null) {
                            bw.write(line);
                            bw.newLine();
                        }
                        bRead.close();
                        bw.close();
                        newFile.delete();


                        System.out.println( patientIDf + " has been updated. ");
                        System.out.println("Would you like to edit another Patient Record? Y/N ");
                        choice = sc.nextLine().charAt(0);


                        if (choice == 'Y' || choice == 'y') {
                            editPatient();
                        }else if (choice == 'N' || choice == 'n'){Menu.initialize();} ;
                    } catch (Exception e) {
                        System.out.println("An Error has occurred");
                        System.out.println();
                        Menu.initialize();
                    }


                } else {
                    System.out.println();
                    Menu.initialize();
                }

            }

            // WORK IN PROGRESS
            else if (choice == 'B' || choice == 'b'){
                {
                    System.out.println("Please type the Patient ID that you would like to edit:");
                    searchStr = sc.nextLine();

                    System.out.println("Please type the updated data:");
                    String newNumber = sc.nextLine();

                    System.out.println("Edit record? Y/N");
                    choice = sc.nextLine().charAt(0);

                    if (choice == 'Y' || choice == 'y') {
                        try {
                            FileWriter fw = new FileWriter(tempFile, true);
                            bw = new BufferedWriter(fw);
                            PrintWriter pw = new PrintWriter(bw);
                            x = new Scanner(new File(filePath));
                            x.useDelimiter("[;\n]");

                            while (x.hasNextLine()) {
                                String line = x.nextLine();
                                String[] parts = line.split(";");
                                pUID = parts[0];
                                pLast = parts[1];
                                pFirst = parts[2];
                                pMiddle = parts[3];
                                pBirthday = parts[4];
                                pGender = parts[5];
                                pAddress = parts[6];
                                pNumber = parts[7];
                                pNatID = parts[8];


                                if (pUID.equals(searchStr)) {
                                    patientIDf = pUID;
                                    pw.println(pUID + ";"
                                            + pLast + ";"
                                            + pFirst + ";"
                                            + pMiddle + ";"
                                            + pBirthday + ";"
                                            + pGender + ";"
                                            + pAddress + ";"
                                            + newNumber + ";"
                                            + pNatID + ";");
                                } else
                                    pw.println(pUID + ";"
                                            + pLast + ";"
                                            + pFirst + ";"
                                            + pMiddle + ";"
                                            + pBirthday + ";"
                                            + pGender + ";"
                                            + pAddress + ";"
                                            + pNumber + ";"
                                            + pNatID + ";");
                            }


                            pw.flush();
                            pw.close();
                            fw = new FileWriter(filePath, false);
                            pw = new PrintWriter(fw, false);
                            pw.flush();
                            pw.close();
                            fw.close();

                            x.close();

                            FileInputStream fis = new FileInputStream(newFile);
                            bRead = new BufferedReader(new InputStreamReader(fis));
                            fw = new FileWriter(file, true);
                            bw = new BufferedWriter(fw);
                            String line = null;
                            while ((line = bRead.readLine()) != null) {
                                bw.write(line);
                                bw.newLine();
                            }
                            bRead.close();
                            bw.close();
                            newFile.delete();


                            System.out.println( patientIDf + " has been updated. ");
                            System.out.println("Would you like to edit another Patient Record? Y/N ");
                            choice = sc.nextLine().charAt(0);


                            if (choice == 'Y' || choice == 'y') {
                                editPatient();
                            }else if (choice == 'N' || choice == 'n'){Menu.initialize();} ;
                        } catch (Exception e) {
                            System.out.println("An Error has occurred");
                            System.out.println();
                            Menu.initialize();
                        }


                    } else {
                        System.out.println();
                        Menu.initialize();
                    }

                }
            } else Menu.initialize();


        }
        else{
            System.out.println("No Record Found");
            Menu.initialize();
        }
    }
}



