package swingComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputBox {
    private String input;

    public String getInput(String title) throws Exception {
        final JTextField textField = new JTextField();
        final JOptionPane pane = new JOptionPane(
                new Object[] {title, textField},
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.OK_CANCEL_OPTION
        );

        final JDialog dialog = pane.createDialog("Input");
        SwingUtilities.invokeAndWait(() -> dialog.setVisible(true));

        Object selectedValue = pane.getValue();
        if (selectedValue instanceof Integer && (Integer) selectedValue == JOptionPane.OK_OPTION) {
            input = textField.getText();
        }

        return input;
    }
}