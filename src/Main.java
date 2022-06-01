import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] args) {
        String savegamesDir = "E:/Games/savegames/";
        String zipDir = "E:\\Games\\savegames\\save.zip";

        openZip(zipDir, savegamesDir);

        File saveFiles = new File(savegamesDir);
        for (File file: saveFiles.listFiles()){
            if (!file.getPath().equals(zipDir)) {
                System.out.println(openProgress(file.getPath()));
            }

        }
    }

    static boolean openZip(String zipPath, String dirPath) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(zipPath))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = dirPath + entry.getName();
                FileOutputStream fout = new FileOutputStream(name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }


    static GameProgress openProgress(String saveFile) {
        GameProgress gameProgress = null;
        try {
            FileInputStream fis = new FileInputStream(saveFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            gameProgress = (GameProgress) ois.readObject();
            fis.close();
            return gameProgress;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

}
