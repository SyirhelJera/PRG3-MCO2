package MCO2;


import java.io.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class Laboratory {

    private static Character char1 = 'A';
    private static Character char2 = 'A';
    private static final Character maxChar = 'Z';
    private static String requestID = "" + char1 + char2;
    private static int int1 = 0;

    private static final DecimalFormat twoDigits = new DecimalFormat("00");
    private static final String result = "Pending";


    public static DateTimeFormatter year = DateTimeFormatter.ofPattern("yyyy");
    public static DateTimeFormatter month = DateTimeFormatter.ofPattern("MM");
    public static DateTimeFormatter day = DateTimeFormatter.ofPattern("dd");
    public static DateTimeFormatter time = DateTimeFormatter.ofPattern("HHmm");
    public static LocalDate dateToday = LocalDate.now(ZoneId.of("Asia/Manila"));

    private static BufferedWriter writer;
    private static BufferedWriter bWrite;
    private static BufferedReader bRead;
    private static Scanner scan;

    private static final Service s = new Service();



    public static void addRequest() throws IOException {

        scan = new Scanner(System.in);
        System.out.println("Insert Patient UID:");
        String searchPID = scan.nextLine();
        System.out.println("Insert Service Code:");
        String searchScode = scan.nextLine();
        File pFile = new File("Patients.txt");
        File sFile = new File("Services.txt");
        Scanner scf = null;
        try {
            scf = new Scanner(pFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        boolean pFound = false;
        String[] pLine= new String[100];
        int count = 0;
        while (scf.hasNextLine()){
            String line = scf.nextLine();
            if (line.contains(searchPID)){
                pFound = true;
                pLine[count] = line;
                count++;
            }
        }


        if(pFound) {
            try {
                scf = new Scanner(sFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            boolean sFound = false;
            String[] sLine= new String[100];
            count = 0;
            while (scf.hasNextLine()){
                String line = scf.nextLine();
                if (line.contains(searchScode)){
                    sFound = true;
                    sLine[count] = line;
                    count++;
                }
            }


            if(sFound) {
                try{
                    // GETS LOCAL TIME
                    Date date = new Date();
                    DateFormat format = new SimpleDateFormat("HHmm");
                    System.out.println(format.format(date));
                    // GETS LOCAL DATE
                    LocalDate current_date = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

                    // REQUEST ID
                    // SEGMENT THAT GENERATES REQUEST ID

                    requestID = "" + char1 + char2 + String.format("%02d", int1);
                    int1++;

                    writer = new BufferedWriter(new FileWriter(searchScode + "_Requests.txt", true));
                    writer.write(searchScode + LocalDate.now().format(formatter) + requestID + ";" + searchPID + ";" + format.format(date) + ";Negative;\n" );
                    writer.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }



                System.out.println("Successfully added to the " + searchScode + "_Request List.");
                System.out.println("Add another request? Y/N");
                Character choice = scan.nextLine().charAt(0);
                if(choice == 'Y' || choice == 'y') {
                    addRequest();
                }
                else {
                    Menu.initialize();
                }
            }
        }
        else{
            System.out.println("No Record Found");
            System.out.println("Search Again? Y/N");
            Character choice = scan.nextLine().charAt(0);
            if(choice == 'Y' || choice == 'y') {
                addRequest();
            }
        }
    }



   public static void searchRequest() throws FileNotFoundException {
        FileProcess fp = new FileProcess();
        Scanner sc = new Scanner(System.in);

       System.out.println("Enter Patient UID or Request Code: ");
       String searchStr = sc.nextLine();

        System.out.println("Enter Service Code: ");
        String sCode = sc.nextLine();

        fp.fileContains(sCode + "_Requests.txt", searchStr);



   }



    public static void deleteRequest() throws IOException{

        String reqUID,pUID,rTime,results;

        String requestID = "";
        String sCode = "";
        String searchLName = "";
        String searchFName = "";
        String searchBDay = "";

        Scanner sc = new Scanner(System.in);
        int trigger;

        System.out.print("Enter Request UID to be deleted: ");
        requestID = sc.nextLine();
        trigger = 1;

        System.out.println("Enter Service Code: ");
        sCode = sc.nextLine();


        String tempFile = "dumpfile.txt";
        String filePath = sCode + "_Requests.txt";

        File file = new File(filePath);
        Scanner scan = new Scanner(file);

        boolean found = false;

        String[] fLine= new String[100];
        int count = 0;

        switch(trigger){
            case 1:
                while (scan.hasNextLine()){

                    String line = scan.nextLine();
                    if (line.contains(requestID)){
                        found = true;
                        fLine[count] = line;
                        count++;

                    }
                }
                break;

            case 2:
                while (scan.hasNextLine()){

                    String line = scan.nextLine();
                    if (line.contains(sCode)){
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
            requestID = sc.nextLine();

            System.out.print("Delete Request Record? Y/N: ");
            char choice = sc.nextLine().charAt(0);


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
                        reqUID = parts[0]; // Request UID
                        pUID = parts[1]; // Patient UID
                        rTime = parts[2];  // Request Time
                        results = parts[3]; // Results
                        reason = parts[4];  // Reason


                        if (reqUID.equals(requestID))
                            pWrite.println(reqUID + ";"
                                    + pUID + ";"
                                    + rTime + ";"
                                    + results + ";"
                                    + reason + ";"
                                    + "D;"
                                    + reason + ";");
                        else
                            pWrite.println(reqUID + ";"
                                    + pUID + ";"
                                    + rTime + ";"
                                    + results + ";"
                                    + reason + ";");
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
                            "\n",
                    "Patient's UID",
                    "LastName",
                    "FirstName",
                    "MiddleName",
                    "Birthday",
                    "Gender",
                    "Address",
                    "National ID");

            String[] slice = fLine[count].split(";");
            for (String a : slice) {
                System.out.print(a + "\t\t\t\t\t");
            }

            File newFile = new File(tempFile);
            requestID = slice[0];
            System.out.println("Delete Lab Record? Y/N");
            char choice = sc.nextLine().charAt(0);
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
                        reqUID = slice[0];
                        pUID = slice[1];
                        rTime = slice[2];
                        results = slice[3];
                        reason = slice[4];


                        if (reqUID.equals(requestID))
                            pw.println(reqUID + ";"
                                    + pUID + ";"
                                    + rTime + ";"
                                    + results + ";"
                                    + reason + ";"
                                    + "D;"
                                    + reason + ";");
                        else
                            pw.println(reqUID + ";"
                                    + pUID + ";"
                                    + rTime + ";"
                                    + results + ";"
                                    + reason + ";");
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
            char choice = sc.nextLine().charAt(0);
            if(choice == 'Y' || choice == 'y') {
                deleteRequest();
            }
        }
    }

/*
    public static void editRequest() throws IOException{

        //INSERT SEARCH PROCESS, NOT YET DONE

        File fileToBeModified = new File(filePath);
        String oldContent = "";
        BufferedReader reader = null;
        FileWriter writer = null;
        String keyword = "Pending";

        try
        {
            reader = new BufferedReader(new FileReader(fileToBeModified));

            //Reading all the lines of input text file into oldContent

            String line = reader.readLine();

            while (line != null)
            {
                oldContent = oldContent + line + System.lineSeparator();

                line = reader.readLine();
            }

            //Replacing oldString with newString in the oldContent

            String newContent = oldContent.replaceAll(keyword, keyword);

            //Rewriting the input text file with newContent

            writer = new FileWriter(fileToBeModified);

            writer.write(newContent);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                //Closing the resources

                reader.close();

                writer.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
*/

}
