package _cowork1;

import java.io.BufferedReader;
import java.io.FileInputStream;
// import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class filescanner {
    // String pause = "중단점";
    public String readFileToString(String filePath, String encoding) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), encoding))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
                // System.out.println(line);
                // System.out.println(content);
            }
        }
        // System.out.println(content);
        // String pause = "중단점";
        return content.toString();
    }
    
    // public static void main(String[] args) {
    //     String filePath = "C:\\ITStudy\\04_java\\cowork\\data\\output.txt";
    //     String encoding = "UTF-8"; // 파일의 인코딩에 따라 수정

    //     try {
    //         String fileContent = readFileToString(filePath, encoding);
    //         System.out.println("파일 내용:\n" + fileContent);
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }
}
