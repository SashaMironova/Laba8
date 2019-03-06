package Server;

import java.io.*;

public class InputOutput {//только для сервера


    public String input() {
        String contents = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Sasha\\Documents\\УЧЕБА\\Лабы\\Прога\\Policemen7.json"));
            //BufferedReader reader = new BufferedReader(new FileReader(System.getenv("InjuredPolicemen")));
            //BufferedReader reader = new BufferedReader(new FileReader("Policemen.json"));
            contents = reader.readLine();
        }
        catch (Exception e){
            System.out.println(e);
        }
        return contents;
    }

    public void output(String str){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Sasha\\Documents\\УЧЕБА\\Лабы\\Прога\\Policemen7.json", false));
            //BufferedWriter writer = new BufferedWriter(new FileWriter(System.getenv("InjuredPolicemen"), false));
            //BufferedWriter writer = new BufferedWriter(new FileWriter("Policemen.json", false));
            writer.write(str);
            writer.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}