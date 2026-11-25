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
        catch(FileNotFoundException | EOFException e){
            return null;
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void writeFile(String filename, Object obj){
        File target = new File(filename);
        File parent = target.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
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
