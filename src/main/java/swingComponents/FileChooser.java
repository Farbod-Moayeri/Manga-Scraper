package swingComponents;

import javax.swing.*;
import java.io.File;

public class FileChooser {

    public String getPath() {
        String folderPath = "";

        JFileChooser folderChooser = new JFileChooser();
        folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int result = folderChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = folderChooser.getSelectedFile();
            folderPath = selectedFolder.getAbsolutePath();
        }

        return folderPath;
    }
}
