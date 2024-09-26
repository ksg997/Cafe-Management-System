import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SysAdminViewAccountsPg extends JFrame implements ActionListener
{
    private JFrame frame;
    private SysAdminViewAccountsController v = new SysAdminViewAccountsController();
    // for determining y-axis of each User entry
    private int i = 1;
    private ArrayList<User> userArray = v.displayAll();

    private static final Insets WEST_INSETS = new Insets(5, 0, 5, 5);
    private static final Insets EAST_INSETS = new Insets(5, 5, 5, 0);

    public SysAdminViewAccountsPg()
    {
        // Setup for UI LAF
        FlatDarkLaf.setup();

        // Create Panel
        JPanel panel = new JPanel(new GridBagLayout());

        // Set System Admin Panel
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Accounts List"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        GridBagConstraints c;

        // Set titles
        JLabel headerEmpIDLabel = new JLabel("Employee ID");
        c = createGbc(0,0);
        panel.add(headerEmpIDLabel, c);

        JLabel headerEmpNameLabel = new JLabel("Name");
        c = createGbc(1,0);
        panel.add(headerEmpNameLabel, c);

        JLabel headerSalaryLabel = new JLabel("Salary");
        c = createGbc(2,0);
        panel.add(headerSalaryLabel, c);

        JLabel headerDateJoinedLabel = new JLabel("Date Joined");
        c = createGbc(3,0);
        panel.add(headerDateJoinedLabel, c);

        JLabel headerRoleLabel = new JLabel("Role");
        c = createGbc(4,0);
        panel.add(headerRoleLabel, c);

        JLabel headerUsernameLabel = new JLabel("Username");
        c = createGbc(5,0);
        panel.add(headerUsernameLabel, c);

        JLabel headerPasswordLabel = new JLabel("Password");
        c = createGbc(6,0);
        panel.add(headerPasswordLabel, c);

        JLabel headerPositionLabel = new JLabel("Position");
        c = createGbc(7,0);
        panel.add(headerPositionLabel, c);

        // displaying all records row by row
        for(User u:userArray){
            JLabel empID = new JLabel(String.valueOf(u.getEmpID()));
            c = createGbc(0,i);
            panel.add(empID, c);

            JLabel name = new JLabel(u.getName());
            c = createGbc(1,i);
            panel.add(name, c);

            JLabel salary = new JLabel(String.valueOf(u.getSalary()));
            c = createGbc(2,i);
            panel.add(salary, c);

            JLabel dateJoined = new JLabel(String.valueOf(u.getDateJoined()));
            c = createGbc(3,i);
            panel.add(dateJoined, c);

            JLabel role = new JLabel(getRoleName(u.getRoleID()));
            c = createGbc(4,i);
            panel.add(role, c);

            JLabel username = new JLabel(u.getUsername());
            c = createGbc(5,i);
            panel.add(username, c);

            JLabel password = new JLabel(u.getPassword());
            c = createGbc(6,i);
            panel.add(password, c);

            JLabel position = new JLabel(u.getPosition());
            c = createGbc(7,i);
            panel.add(position, c);

            i++;
        }

        frame = new JFrame("View Accounts");
        frame.setLayout(new GridBagLayout());

        frame.add(panel, c);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        // can combine update and/or search in here
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

    private String getRoleName(int id){ //susceptible to change, edit to role arraylist
        Profile p = new Profile();
        ArrayList<String> roleArr = p.profileRecordArray();
        return roleArr.get(id-1);
    }

    public static void main(String[] args) {
        new SysAdminViewAccountsPg();
    }
}
