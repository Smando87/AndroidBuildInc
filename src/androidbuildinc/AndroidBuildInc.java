/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package androidbuildinc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author sdelprete
 */
public class AndroidBuildInc {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {


        if (args.length < 2) {
            usage();
            System.exit(1);
        }

        String filename = args[0];

        int inc = Integer.parseInt(args[1]);

        
        ArrayList<String> lineList= new ArrayList<String>();
        
        File f = new File(filename);
        BufferedReader file = new BufferedReader(new FileReader(f));
        String line;
       
       
        do {
            if ((line = file.readLine()) != null) {
                if (line.contains("android:versionCode")) {
                    String oldbuild = line.substring(line.indexOf("\"") + 1, line.indexOf("\"", line.indexOf("\"")+1));
                    int build=Integer.parseInt(oldbuild) + 1;
                    System.out.println("Build corrente: "+oldbuild);
                    System.out.println("Build nuova: "+build);
                    line=line.replace(oldbuild, "" + build);
                    lineList.add(line);
                }
                else
                    lineList.add(line);
            }
        } while (line != null);

        writeToFile(filename,lineList);


    }

    private static void usage() {
        System.out.println("Utilizzo:\n"
                + "AndroidBuildInc.jar AndroidManifest.xml 1");
    }
    
    private static void writeToFile(String filename,ArrayList al) throws IOException{
            
            File oldfile=new File(filename+".bak");
            oldfile.delete();
            oldfile=new File(filename);
            oldfile.renameTo(new File(filename+".bak"));
            
            BufferedWriter bw;
            bw=new BufferedWriter(new FileWriter(filename));
            Object[] array=al.toArray();
            for(int i =0;i<array.length;i++){
                try{
                    bw.write(array[i].toString()+"\n");
                }catch(NullPointerException e){
                    bw.write("\n");
                }
            }
            bw.flush();
            bw.close();
    }
}
