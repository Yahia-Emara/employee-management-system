package database;

import java.io.*;

public class Helper {
    public static Object readFile(String filename){
        try (
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file)
        ){
            Object obj = in.readObject();
            return obj;
        }
        catch(FileNotFoundException e){
            return null;
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void writeFile(String filename, Object obj){
        try (
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file)
        ) {
            out.writeObject(obj);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
