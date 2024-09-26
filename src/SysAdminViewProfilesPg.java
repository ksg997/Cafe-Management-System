// Page to display list of user profiles created in the database
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SysAdminViewProfilesPg extends JFrame
{
    private JFrame frame;

    private static final Insets WEST_INSETS = new Insets(5, 0, 5, 5);
    private static final Insets EAST_INSETS = new Insets(5, 5, 5, 0);

    public SysAdminViewProfilesPg()
    {
        SysAdminViewProfilesController vpc = new SysAdminViewProfilesController();
        ArrayList<Profile> profileArray = vpc.getProfileArrayList();
        
        // Setup for UI LAF
        FlatDarkLaf.setup();

        // Setup JFrame
        frame = new JFrame("View Profiles Page");
        frame.setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GridBagConstraints c;

        JPanel mainPanel = new JPanel(new GridBagLayout());

        // ==============Set mainPanel=================
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Profiles List"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Set profile ID Header
        JLabel profileIDHeaderLabel = new JLabel("Profile ID");
        profileIDHeaderLabel.setFont(new Font("Arial", Font.BOLD, 12));
        c = createGbc(0,0);
        mainPanel.add(profileIDHeaderLabel, c);

        // Set profile Name Header
        JLabel profileNameHeaderLabel = new JLabel("Profile Name");
        profileNameHeaderLabel.setFont(new Font("Arial", Font.BOLD, 12));
        c = createGbc(1,0);
        mainPanel.add(profileNameHeaderLabel, c);

        // To separate each record per role
        int i = 1;

        // displaying all records row by row
        for(Profile p : profileArray){
            JLabel profileID = new JLabel(String.valueOf(p.getID()));
            c = createGbc(0,i);
            mainPanel.add(profileID, c);

            JLabel name = new JLabel(p.getName());
            c = createGbc(1,i);
            mainPanel.add(name, c);

            // Increment for next row
            i++;
        }

        // Set preferred size
        mainPanel.setPreferredSize(new Dimension(300, 300));

        frame.add(mainPanel, c);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private GridBagConstraints createGbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        gbc.anchor = (x == 0) ? GridBagConstraints.WEST : GridBagConstraints.EAST;
        gbc.fill = (x == 0) ? GridBagConstraints.BOTH : GridBagConstraints.HORIZONTAL;

        gbc.insets = (x == 0) ? WEST_INSETS : EAST_INSETS;
        gbc.weightx = (x == 0) ? 0.1 : 1.0;
        gbc.weighty = 1.0;
        return gbc;
    }

    public static void main(String[] args) {
        new SysAdminViewProfilesPg();
    }
}

