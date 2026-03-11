import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // make args[0] the directory (copied as path)
        File directory = new File(args[0]);

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                handleFile(file.getAbsolutePath());
            }
        } else {
            System.out.println("Directory is empty or does not exist.");
        }



    }

    public static void handleFile(String filename){
        try {
            File partFile = new File(filename);
            Scanner fileReader = new Scanner(partFile);
            FileWriter wtr = new FileWriter("participant_results.txt");

            String data ="";
            String[] cqs = new String[10];
            int cqIndex = 0;
            String[] ranges = new String[40];
            int rangeIndex = 0;
            String[] rts = new String[40];
            int rtIndex = 0;
            String lastRT = "";
            String id = "0";
            String age = "0";

            while (fileReader.hasNext()) {
                data = fileReader.nextLine();

                // record participant ID
                if (data.contains("uuid")){
                    id = data.substring(6);
                }

                // record participant age
                if (data.equals("l: age")){
                    while (fileReader.hasNext() && !data.contains("a: ")){
                        data = fileReader.nextLine();
                    }
                    age = data.substring(3);
                }

                // record T (time spent on screen)
                if (data.contains("T: ")){
                    lastRT = data.substring(3);
                }

                // place comprehension questions responses into an array
                if (data.contains("cq1")){
                    while (fileReader.hasNext() && !data.contains("a:")){
                        data = fileReader.nextLine();
                    }
                    cqs[cqIndex] = data.substring(3);
                    ++cqIndex;
//                     wtr.write(data.substring(3) + "/n");
//                     System.out.println(data.substring(3));
                }

                //Place range responses into an array
                if (data.contains("s:")){
                    ranges[rangeIndex] = data.substring(3);
                    ++rangeIndex;
//                     wtr.write(data.substring(3) + "/n");
//                     System.out.println(data.substring(3));
                    rts[rtIndex] = lastRT;
                    ++rtIndex;
                }

            } // end While

            if (data.contains("end")){
                printFile(id, age, cqs, ranges, rts, wtr);
            }
//
//            // Write CQ array
//            System.out.println("----------------------------------------");
//            System.out.println("Printing Comprehension Question Responses");
//            System.out.println("----------------------------------------");
//            String cqLine = cqs[0] + "\n\n\n\n\n" + cqs[1] + "\n\n\n\n" + cqs[2] +
//                    "\n\n\n\n\n\n" + cqs[3] + "\n\n\n\n\n\n" + cqs[4] + "\n\n\n\n\n\n"
//                    + cqs[5] + "\n\n\n\n" + cqs[6] + "\n\n\n\n" + cqs[7];
//
//            wtr.write(cqLine);
//
//            System.out.print(cqLine);
//
//            if (cqs[8] != null){
//                System.out.println("Extra CQ response detected, please investigate");
//            }
//
//             // Write Range Array
//            System.out.println("\n----------------------------------------");
//            System.out.println("Printing Range Question Responses");
//            System.out.println("----------------------------------------");
//            for (int i = 0; i < 38; ++i){
//                if (i == 0 || i == 1){
//                    continue;
//                }
//                System.out.println(ranges[i]);
//                wtr.write(ranges[i] + "\n");
//            }

            // Write CQs and ranges at once:

        } catch (Exception e) {
            System.out.print("An error occured: ");
            e.printStackTrace();
        }
    }

    public static void printFile(String id, String age, String[] cqs, String[] ranges, String[] rts, FileWriter wtr) throws IOException {
//        System.out.println("\n----------------------------------------");
//        System.out.println("Printing Range Question Responses");
//        System.out.println("----------------------------------------");
//        System.out.println(id);
//        System.out.println(age);
//        System.out.println("----------------------------------------");
        int cqCount = 0;
        for (int i = 0; i < 38; ++i){
            if (i == 0 || i == 1){
                continue;
            }
            if (i == 2 || i == 7 || i == 11 || i == 17 || i == 23 || i == 29 || i == 33 || i == 37){
                System.out.println( id + "\t" + age + "\t" + cqs[cqCount] + "\t" + ranges[i] + "\t" + rts[i]);
                wtr.write(id + "\t" + age + "\t" +cqs[cqCount] + "\t" + ranges[i] + "\t" + rts[i] + "\n");
                ++cqCount;
            }
            else {
                System.out.println(id + "\t" + age + "\t" +"0\t" + ranges[i] + "\t" + rts[i]);
                wtr.write(id + "\t" + age + "\t" +"0\t" + ranges[i] + "\t" + rts[i] + "\n");
            }
        }

        if (ranges[39] != null){
            System.out.println("Extra range response detected, please investigate and adjust range response and RT");
        }

    }
}