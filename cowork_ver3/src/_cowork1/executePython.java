package _cowork1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class executePython {
    public void Run() {
        ProcessBuilder pb = new ProcessBuilder("python", "C:\\ITStudy\\04_java\\cowork\\src\\_cowork1\\webCrawling.py");
        try {
            Process p = pb.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            // System.out.println(11);
            while ((line = br.readLine()) != null) {
            	System.out.println(line);
            	System.out.println(br.readLine());
            }
            // System.out.println(22);
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("파이썬 실행 완료");
        }
    }


    //     public static void main(String[] args) {
    
    //     Run();
    // }
}