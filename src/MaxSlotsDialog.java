import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MaxSlotsDialog extends JDialog implements ActionListener {
    private JTextField maxSlotsTextField;
    private JButton okButton, cancelButton;
    private boolean isOkPressed;

    public MaxSlotsDialog(JFrame parent, Integer currMaxSlot) {
        super(parent, "Set Max Work Slots", true);
        isOkPressed = false;

        JPanel panel = new JPanel();
        maxSlotsTextField = new JTextField(10);

        if (currMaxSlot != null) {
            maxSlotsTextField.setText(currMaxSlot.toString());
        }

        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");

        okButton.addActionListener(this);
        cancelButton.addActionListener(this);

        panel.add(new JLabel("Enter Max Work Slots:"));
        panel.add(maxSlotsTextField);
        panel.add(okButton);
        panel.add(cancelButton);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(parent);
    }

    public boolean isOkPressed() {
        return isOkPressed;
    }

    public int getMaxSlots() {
        return Integer.parseInt(maxSlotsTextField.getText());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            isOkPressed = true;
        }
        dispose();
    }
}