package MCO2;

import java.util.*;
import java.io.*;

public class Service {

    private static BufferedWriter bWrite;
    private static BufferedReader bRead;
    private static Scanner x;
    public static ArrayList <String>list = new ArrayList<String>();

    public static void addService() throws IOException {

        String filePath = "Services.txt";
        Scanner sc = new Scanner(System.in);
        System.out.print("New Service Code: ");
        String sCode = sc.nextLine();
        System.out.print("New Service Description: ");
        String sDesc = sc.nextLine();
        System.out.print("New Service use cost: ");
        int sCost = Integer.parseInt(sc.nextLine());
        System.out.print("Save new Service? Y/N: ");
        char choice = sc.nextLine().charAt(0);

        if(choice == 'Y' || choice == 'y'){
            list.add(sCode);
            try {
                bWrite = new BufferedWriter(new FileWriter(filePath,true));
                bWrite.write(sCode+";"+sDesc+";"+sCost+";\n");
                bWrite.close();
            }
            catch(IOException e){
                e.printStackTrace();
                System.out.println("An Error has occurred");
                Menu.initialize();
            }
            System.out.println("Service added.");
        }
        else
            Menu.initialize();
    }

    public static void searchService() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Service Code or Keyword:");
        String searchStr = sc.nextLine();
        String filePath = "Services.txt";
        File file = new File(filePath);
        Scanner scf = new Scanner(file);
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
            System.out.println("--------------------------------------");
            System.out.printf("\n\n%-20s%-20s%-20s\n","Service Code","Description","Price");

            for (int i = 0; i < count; i++) {
                String[] parts = fLine[i].split(";");
                for (String a : parts) {
                    System.out.printf("%-20s",a);
                }
                System.out.println("\n");
            }
        }

        else{
            System.out.println("No Record Found");
            Menu.initialize();

        }


    }


    public static void deleteService() throws IOException{

        String sCode,sDesc,sPrice;
        String serviceCode="";
        String sDescrip="";

        Scanner sc = new Scanner(System.in);
        System.out.println("Service Code or Keyword:");

        String stringInput = sc.nextLine();

        String tempFile = "temp.txt";
        String filePath = "Services.txt";
        File file = new File(filePath);

        x = new Scanner(file);
        boolean found = false;

        String[] fLine= new String[100];

        int count = 0;

        while (x.hasNextLine()){

            String line = x.nextLine();
            if (line.contains(stringInput)){
                found = true;
                fLine[count] = line;
                count++;

            }
        }
        if(found) {

            System.out.println("--------------------------------------");
            System.out.printf("\n\n%-20s%-20s%-20s\n","Service Code","Description","Price");

            for (int i = 0; i < count; i++) {
                String[] parts = fLine[i].split(";");
                for (String a : parts) {
                    System.out.printf("%-20s",a);
                }
                System.out.println("\n");
            }

            File newFile = new File(tempFile);
            System.out.println("Please type the Service Code:");
            stringInput = sc.nextLine();
            System.out.println("Please type the reason for deletion:");
            String reason = sc.nextLine();
            System.out.println("Delete Service? Y/N");
            char choice = sc.nextLine().charAt(0);
            if(choice == 'Y' || choice == 'y') {
                try {
                    FileWriter fWrite = new FileWriter(tempFile, true);
                    bWrite = new BufferedWriter(fWrite);
                    PrintWriter pWrite = new PrintWriter(bWrite);
                    x = new Scanner(new File (filePath));
                    x.useDelimiter("[;\n]");

                    while (x.hasNextLine()) {
                        String line = x.nextLine();
                        String[] parts = line.split(";");
                        sCode = parts[0];
                        sDesc = parts[1];
                        sPrice = parts[2];
                        if (sCode.equals(stringInput)) {
                            serviceCode=sCode;
                            sDescrip=sDesc;
                            pWrite.println(sCode + ";" + sDesc + ";" + sPrice + ";" + "D;" + reason + ";");
                        }
                        else
                            pWrite.println(sCode + ";" + sDesc + ";" + sPrice + ";");
                    }


                    // CLOSE OUTPUT STREAM

                    pWrite.flush();
                    pWrite.close();

                    fWrite = new FileWriter(filePath,false);
                    pWrite = new PrintWriter(fWrite,false);

                    pWrite.flush();
                    pWrite.close();
                    fWrite.close();

                    x.close();

                    FileInputStream fis = new FileInputStream(newFile);
                    bRead = new BufferedReader(new InputStreamReader(fis));
                    fWrite = new FileWriter(file,true);
                    bWrite = new BufferedWriter(fWrite);
                    String line = null;

                    while((line = bRead.readLine()) != null){
                        bWrite.write(line);
                        bWrite.newLine();
                    }

                    bRead.close();
                    bWrite.close();

                    newFile.delete();


                    System.out.println("Service "+serviceCode+" "+sDescrip+" has been successfully deleted.");
                    System.out.println("Would you like to delete another service? Y/N");
                    choice = sc.nextLine().charAt(0);
                    if(choice == 'Y' || choice == 'y'){
                        deleteService();
                    }

                } catch (Exception e) {
                    System.out.println("An Error has occurred");
                    System.out.println();
                    Menu.initialize();
                }
            }

            else {
                System.out.println();
                Menu.initialize();
            }
        }
        else{
            System.out.println("No Record Found");
            Menu.initialize();
        }
    }


    public static void editService() throws IOException{

        Scanner sc = new Scanner(System.in);

        System.out.println("Editing a service requires you to delete and replace a service with a new one.");
        System.out.println("Would you proceed? Y/N");

        char choice = sc.nextLine().charAt(0);

        try {
            if (choice == 'Y' || choice == 'y') {
                deleteService();
                addService();
            } else
                Menu.initialize();
        }
        catch(Exception e){
            System.out.println("An Error has occurred");
            Menu.initialize();
        }
    }

}
