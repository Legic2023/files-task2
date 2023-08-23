import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    static String defPath = "F:\\JAVA_Proj\\FilesTask1\\Games\\savegames\\";
    static String zipPath = "F:\\JAVA_Proj\\FilesTask1\\Games\\archive\\";
    static GameProgress gp1 = new GameProgress(1, 1, 1, 1);
    static GameProgress gp2 = new GameProgress(3, 3, 3, 3);
    static GameProgress gp3 = new GameProgress(9, 9, 9, 9);

    static String[] entryNames;

    private static void saveGame(String fullPath, GameProgress gp) {

        try (FileOutputStream fos = new FileOutputStream(fullPath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(gp);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void zipFiles(String zipPath, String[] entryNames) {

        FileInputStream fis = null;

        try (FileOutputStream fos = new FileOutputStream(zipPath + "zip.zip");
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            for (String entryName : entryNames) {
                fis = new FileInputStream(defPath + entryName);
                ZipEntry saveDat = new ZipEntry(entryName);
                zos.putNextEntry(saveDat);

                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zos.write(buffer);
                zos.closeEntry();
                fis.close();
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void fileDelete(String[] entryNames) {

        for (int i = 0; i < entryNames.length; i++) {

            File file = new File(defPath, entryNames[i]);
            file.delete();
/*
            if (file.exists() && file.isFile()) {
                System.out.println(entryNames[i]);
            }

            if (file.exists() && file.delete()) {
                System.out.printf("файл %s удален из %s", entryNames[i], defPath);
            } else System.err.println("Не могу найти " + file + " (" + file.getName() + ")");
*/
        }
    }


    public static void main(String[] args) {

        saveGame(defPath + "save1.dat", gp1);
        saveGame(defPath + "save2.dat", gp2);
        saveGame(defPath + "save3.dat", gp3);

        File dir = new File(defPath);
        entryNames = dir.list();

        zipFiles(zipPath, entryNames);

        fileDelete(entryNames);

    }
}
