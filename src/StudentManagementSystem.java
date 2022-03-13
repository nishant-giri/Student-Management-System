
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class StudentManagementSystem {
    private JTextField txtRoll;
    private JTextField txtName;
    private JTextField txtGender;
    private JTextField txtBranch;
    private JTextField txtSectn;
    private JTextField txtEmail;
    private JTextField txtMobile;
    private JTable table1;
    private JButton addButton;
    private JButton searchButton;
    private JTextField txtid;
    private JButton updateButton;
    private JButton deleteButton1;
    private JPanel Main;
    private JScrollPane table_1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("StudentManagementSystem");
        frame.setContentPane(new StudentManagementSystem().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;
    public void connect(){
        try {
            // registering mysql Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Database connection with the path (system path to DB)
            con = DriverManager.getConnection("jdbc:mysql://localhost/jdbc-video", "root", "qaswedfr4321H");
            System.out.println("Success");
        }
        catch (ClassNotFoundException ex){

            ex.printStackTrace();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }

    }

    void table_load(){
        try {
            pst = con.prepareStatement("select * from studinfo");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public StudentManagementSystem() {
        connect();
        table_load();
        addButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String roll, name, gender, branch, sect, email, mobile;

                roll = txtRoll.getText();
                name = txtName.getText();
                gender = txtGender.getText();
                branch = txtBranch.getText();
                sect = txtSectn.getText();
                email = txtEmail.getText();
                mobile = txtMobile.getText();

                try {
                    pst = con.prepareStatement("insert into studinfo(roll, name, gender, branch, section, email, mobile) values(?, ?, ?, ?, ?, ?, ?)");
                    pst.setString(1, roll);
                    pst.setString(2, name);
                    pst.setString(3, gender);
                    pst.setString(4, branch);
                    pst.setString(5, sect);
                    pst.setString(6, email);
                    pst.setString(7, mobile);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Added ..");
                    // table_load();
                    txtRoll.setText("");
                    txtName.setText("");
                    txtGender.setText("");
                    txtBranch.setText("");
                    txtSectn.setText("");
                    txtEmail.setText("");
                    txtMobile.setText("");
                    txtRoll.requestFocus();
                }
                catch (SQLException e1) {
                    System.out.println("Here");
                    e1.printStackTrace();
                }
                table_load();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String rollid = txtid.getText();

                    pst = con.prepareStatement("select name, gender, branch, section, email, mobile from studinfo where roll = ?");
                    pst.setString(1, rollid);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next() == true) {
                        String name = rs.getString(1);
                        String gender = rs.getString(2);
                        String branch = rs.getString(3);
                        String sect = rs.getString(4);
                        String email = rs.getString(5);
                        String mobile = rs.getString(6);

                        txtRoll.setText(rollid);
                        txtName.setText(name);
                        txtGender.setText(gender);
                        txtBranch.setText(branch);
                        txtSectn.setText(sect);
                        txtEmail.setText(email);
                        txtMobile.setText(mobile);
                        txtid.setText("Enter Roll No. here");

                    } else {
                        txtRoll.setText("");
                        txtName.setText("");
                        txtGender.setText("");
                        txtBranch.setText("");
                        txtSectn.setText("");
                        txtEmail.setText("");
                        txtMobile.setText("");
                        txtid.setText("Enter Roll No. here");
                        JOptionPane.showMessageDialog(null, "Invalid Roll Number. Try Again");
                    }
                } catch ( SQLException e1 ) {
                    e1.printStackTrace();
                }

            }
        });
        updateButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String roll, name, gender, branch, sect, email, mobile;

                roll = txtRoll.getText();
                name = txtName.getText();
                gender = txtGender.getText();
                branch = txtBranch.getText();
                sect = txtSectn.getText();
                email = txtEmail.getText();
                mobile = txtMobile.getText();

                try {
                    pst = con.prepareStatement("update studinfo set name = ?, gender = ?, branch = ?, section = ?, email = ?, mobile = ? where roll = ?");

                    pst.setString(1, name);
                    pst.setString(2, gender);
                    pst.setString(3, branch);
                    pst.setString(4, sect);
                    pst.setString(5, email);
                    pst.setString(6, mobile);
                    pst.setString(7, roll);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Updated.");
                    table_load();
                    txtRoll.setText("");
                    txtName.setText("");
                    txtGender.setText("");
                    txtBranch.setText("");
                    txtSectn.setText("");
                    txtEmail.setText("");
                    txtMobile.setText("");
                    txtRoll.requestFocus();
                    txtid.setText("Enter Roll No. here");
                } catch (SQLException e1){
                    e1.printStackTrace();
                }

            }
        });

        deleteButton1.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                String rollid;

                rollid = txtid.getText();
                try {
                    pst = con.prepareStatement("delete from studinfo where roll = ?");
                    pst.setString(1, rollid);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleted");
                    table_load();
                    txtRoll.setText("");
                    txtName.setText("");
                    txtGender.setText("");
                    txtBranch.setText("");
                    txtSectn.setText("");
                    txtEmail.setText("");
                    txtMobile.setText("");
                    txtRoll.requestFocus();
                    txtid.setText("Enter Roll No. here");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}
