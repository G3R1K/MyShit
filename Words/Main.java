import java.io.*;
import java.io.BufferedReader;
import java.util.Scanner;

public class Main {

    public static void loadDictionary(String srcPath) throws IOException{
        File dir = new File(".");
        String source = srcPath;
        if (srcPath == null) {
            source = dir.getCanonicalPath() + File.separator + "dict.txt";
        }
        String dest = dir.getCanonicalPath() + File.separator + "tempDict.txt";

        File fin = new File(source);
        FileInputStream fis = new FileInputStream(fin);
        BufferedReader in = new BufferedReader(new InputStreamReader(fis));

        FileWriter fstream = new FileWriter(dest, false);
        BufferedWriter out = new BufferedWriter(fstream);

        String aLine = null;
        while ((aLine = in.readLine()) != null) {
            out.write(aLine);
            out.newLine();
        }

        in.close();
        out.close();
    }

    private static boolean deleteFile(String path){
        File file = new File(path);
        return file.delete();
    }

    public static void deleteWord(String word ,String srcPath) throws IOException{
        File dir = new File(".");
        String source = srcPath;
        if (srcPath == null) {
            source = dir.getCanonicalPath() + File.separator + "tempDict.txt";
        }
        String dest = dir.getCanonicalPath() + File.separator + "delTempDict.txt";

        File fin = new File(source);
        FileInputStream fis = new FileInputStream(fin);
        BufferedReader in = new BufferedReader(new InputStreamReader(fis));

        FileWriter fstream = new FileWriter(dest, false);
        BufferedWriter out = new BufferedWriter(fstream);

        String aLine = null;
        while ((aLine = in.readLine()) != null) {
            if(!aLine.equals(word)){
                out.write(aLine);
                out.newLine();
            }
        }

        in.close();
        out.close();

        loadDictionary("delTempDict.txt");
        deleteFile("delTempDict.txt");
    }

    public static String findWord(String firstLetter, String lastLetter) throws IOException {
        String regEx;
        if(lastLetter == null){
            regEx = firstLetter + ".+?$";
        }else{
            regEx = firstLetter + ".+?" + lastLetter + "$";
        }

        String source = "tempDict.txt";
        File fin = new File(source);
        FileInputStream fis = new FileInputStream(fin);
        BufferedReader in = new BufferedReader(new InputStreamReader(fis));

        String aLine = null;
        while ((aLine = in.readLine()) != null) {
            if(aLine.matches(regEx)){
                in.close();
                deleteWord(aLine, null);
                return "-> " + aLine;
            }
        }
        in.close();
        return null;
    }

    public static String findDefinition(String word, String srcPath) throws IOException {
        String regEx = word + ":.+$";

        String source = srcPath;
        if (srcPath == null) {
            source = new File(".").getCanonicalPath() + File.separator + "defDict.txt";
        }
        File fin = new File(source);
        FileInputStream fis = new FileInputStream(fin);
        BufferedReader in = new BufferedReader(new InputStreamReader(fis));

        String aLine = null;
        while ((aLine = in.readLine()) != null) {
            if(aLine.matches(regEx)){
                in.close();
                return aLine;
            }
        }
        in.close();
        return null;
    }

    public static String findWord(String firstLetter) throws IOException {
        return findWord(firstLetter,null);
    }

    public static void requestAnalyzer(String request) throws IOException {
        switch (request.substring(0,1)){
            case "!":
                if(request.length() == 4){
                    System.out.println(findWord(request.substring(1,2),request.substring(3,4)));
                }else{
                    if(request.length() == 2){
                        System.out.println(findWord(request.substring(1,2)));
                    }
                }
                break;
            case "?":
                System.out.println(findDefinition(request.substring(1),null));
                break;
            case "-":
                deleteWord(request.substring(1),null);
                break;
            case "/":
                System.out.println("GoodBye :)");
                deleteFile("tempDict.txt");
                System.exit(0);
                break;
            default:
                System.out.println("Something wrong with your request...");
                break;
        }
    }

    public static void request() throws IOException {
        Scanner in = new Scanner(System.in,"cp1251");
        String request = in.next();
        System.out.println(request);
        requestAnalyzer(request);
    }

    public static void main(String[] args) throws IOException {
        String dictPath = null;
        if(args.length == 1){
            dictPath = args[0];
        }
        try {
            loadDictionary(dictPath);
            System.out.println(findWord("а","р"));
            while (true){
                request();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}