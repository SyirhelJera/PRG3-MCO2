package MCO2;

import java.io.*;
import java.util.Scanner;

public class FileProcess {


    public void createFile(String fileName)  {
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void writeToFile(String fileName, String input){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            writer.write(input);
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void readFile(String fileName){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(fileName)){};
            String line;
            while ((line=reader.readLine() ) != null){
                System.out.println(line);
            }
            reader.close();
        }catch (IOException e){e.printStackTrace();}


    }

    public void fileContains(String filePath, String searchStr) throws FileNotFoundException {

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

        if (found == true){
            System.out.printf(        // Print FORMAT
                    "%-25s" + // pUID
                            "%-25s" + // lName
                            "%-25s" + // fName
                            "%-25s" + // mName
                            " %n",
                    "Request's UID",
                    "Lab Test Type",
                    "Request Date",
                    "Result");

            // PRINTS THE RETRIEVED DATA
            for (int i = 0; i < count; i++) {
                String[] parts = fLine[i].split(";");



                System.out.printf("%-25s", parts[0]);
                System.out.printf("%-25s","Place Holder"); // insert dito yung service description
                for(i = 0; i<8; i++){
                    System.out.print(parts[0].charAt(i+3));
                }
                System.out.printf("%25s", parts[3]);

                System.out.println();
            }

        } else {
            System.out.println("\nNo Record Found");
        }
    }






}

