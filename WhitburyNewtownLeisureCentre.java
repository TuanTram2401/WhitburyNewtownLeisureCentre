
/**
 * Write a description of class a here.
 *
 * @author (2529722)
 * @version (19/07/2021)
 */

/**
 * Write a description of class 
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.sql.*;

public class WhitburyNewtownLeisureCentre extends JFrame implements ActionListener {
    // Declareing elements
    JButton btnSearchMemberByTown = new JButton("Search Member In Springfield");

    JButton btnSearchMemberByTypeName = new JButton("Search Swim Membership Member");

    JLabel idLabel = new JLabel(" memberID");
    JTextField idText = new JTextField(3);
    JLabel fnameLabel = new JLabel("fName");
    JTextField fNameText = new JTextField(10);
    JLabel sNameLabel = new JLabel("sName");
    JTextField sNameText = new JTextField(10);
    JTextField DOBText = new JTextField(10);
    JLabel DOBLabel = new JLabel("DOB");
    JTextField houseNoNameText = new JTextField(3);
    JLabel houseNoNameLabel = new JLabel("houseNoName");
    JTextField StreetText = new JTextField(20);
    JLabel StreetLabel = new JLabel("Street");
    JTextField TownText = new JTextField(11);
    JLabel TownLabel = new JLabel("Town");
    JTextField CountyText = new JTextField(12);
    JLabel CountyLabel = new JLabel("County");
    JTextField PostcodeText = new JTextField(8);
    JLabel PostcodeLabel = new JLabel("Postcode");
    JTextField telephoneNoText = new JTextField(11);
    JLabel telephoneNoLabel = new JLabel("telephoneNo");
    JButton btnAddMember = new JButton("Add Member");

    JButton btnDeleteMember = new JButton("Delete Member");
    JTextField DeleteMemberText = new JTextField(3);
    JLabel DeleteMemberLabel = new JLabel("Enter memberID to delete");

    JButton btnEditMember = new JButton("Edit Member");
    JTextField EditMemberText = new JTextField(3);
    JLabel EditMemberLabel = new JLabel("Enter memberID to edit");
    JButton btnSaveEdit = new JButton("Save Edit");

    JButton btnReset = new JButton("Reset table");

    JTable table;
    Connection con;
    Statement stmt;
    ResultSet rs;
    DefaultTableModel model;

    public WhitburyNewtownLeisureCentre(String title) // constructor
    {
        super(title);

        setLayout(new FlowLayout());
        // Adding elements to GUI frame.

        add(idLabel);
        add(idText);
        add(fnameLabel);
        add(fNameText);
        add(sNameLabel);
        add(sNameText);
        add(DOBLabel);
        add(DOBText);
        add(houseNoNameLabel);
        add(houseNoNameText);
        add(StreetLabel);
        add(StreetText);
        add(TownLabel);
        add(TownText);
        add(CountyLabel);
        add(CountyText);
        add(PostcodeLabel);
        add(PostcodeText);
        add(telephoneNoLabel);
        add(telephoneNoText);

        add(btnAddMember);

        add(DeleteMemberLabel);
        add(DeleteMemberText);
        add(btnDeleteMember);

        add(EditMemberLabel);
        add(EditMemberText);
        add(btnEditMember);
        add(btnSaveEdit);
        btnSaveEdit.setEnabled(false);

        add(btnSearchMemberByTown);

        add(btnSearchMemberByTypeName);

        add(btnReset);

        model = new DefaultTableModel();
        showTable();

        // Setting up ActionListener
        btnSearchMemberByTown.addActionListener(this);
        btnSearchMemberByTown.setActionCommand("SearchMemberInSpringfield");

        btnSearchMemberByTypeName.addActionListener(this);
        btnSearchMemberByTypeName.setActionCommand("SearchSwimMembershipMember");

        btnAddMember.addActionListener(this);
        btnAddMember.setActionCommand("AddMember");

        btnDeleteMember.addActionListener(this);
        btnDeleteMember.setActionCommand("DeleteMember");

        btnEditMember.addActionListener(this);
        btnEditMember.setActionCommand("EditMember");

        btnSaveEdit.addActionListener(this);
        btnSaveEdit.setActionCommand("SaveEdit");

        btnReset.addActionListener(this);
        btnReset.setActionCommand("Reset");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    // showTable method which create Jtable and show up the data in the table when
    // the interface first appears.
    public void showTable() {
        model.setRowCount(0);
        String[] columnNames = { "memberID", "fName", "sName", "DOB", "houseNoName", "street", "town", "county",
                "postcode", "telephoneNo", "membershipTypeID", "typeName", "price" };
        model.setColumnIdentifiers(columnNames);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/leisure_centre", "user1",
                    "TramAnhTuan1234567890");
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(
                    "select tbl_member.memberID,tbl_member.fName,tbl_member.sName,tbl_member.DOB,tbl_member.houseNoName,tbl_member.street,tbl_member.town,tbl_member.county,tbl_member.postcode,tbl_member.telephoneNo,tbl_member.membershipTypeID,tbl_membership_type.typeName,tbl_membership_type.price from tbl_member inner join tbl_membership_type on tbl_member.membershipTypeID = tbl_membership_type.membershipTypeID");

            while (rs.next()) {

                String memberID = String.valueOf(rs.getInt(1));
                String fName = rs.getString(2);
                String sName = rs.getString(3);
                String DOB = rs.getString(4);
                String houseNoName = rs.getString(5);
                String street = rs.getString(6);
                String town = rs.getString(7);
                String county = rs.getString(8);
                String postcode = rs.getString(9);
                String telephoneNo = rs.getString(10);
                String membershipTypeID = rs.getString(11);
                String typeName = rs.getString(12);
                String price = rs.getString(13);

                model.addRow(new Object[] { memberID, fName, sName, DOB, houseNoName, street, town, county, postcode,
                        telephoneNo, membershipTypeID, typeName, price });
            }
            con.close();
            stmt.close();
            rs.close();
            
        } catch (Exception err) {
            JOptionPane.showMessageDialog(this, err.getMessage());
        }
        table = new JTable();
        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scroll);
    }

    // searchByTown method. When user clicks on the Button the Jtable will
    // automatically displays all members who are living in Springfield

    public void searchByTown() {
        model.setRowCount(0);
        String[] columnNames = { "memberID", "fName", "sName", "DOB", "houseNoName", "street", "town", "county",
                "postcode", "telephoneNo" };
        model.setColumnIdentifiers(columnNames);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/leisure_centre", "user1",
                    "TramAnhTuan1234567890");
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(
                    "select memberID,fName,sName,DOB,houseNoName,street,town,county,postcode,telephoneNo from tbl_member where town ='Springfield'");

            while (rs.next()) {
                String memberID = String.valueOf(rs.getInt(1));
                String fName = rs.getString(2);
                String sName = rs.getString(3);
                String DOB = rs.getString(4);
                String houseNoName = rs.getString(5);
                String street = rs.getString(6);
                String town = rs.getString(7);
                String county = rs.getString(8);
                String postcode = rs.getString(9);
                String telephoneNo = rs.getString(10);

                model.addRow(new Object[] { memberID, fName, sName, DOB, houseNoName, street, town, county, postcode,
                        telephoneNo });
            }
            con.close();
            stmt.close();
            rs.close();
            
        } catch (Exception err) {
            JOptionPane.showMessageDialog(this, err.getMessage());
        }
    }

    // searchmember method. When user clicks in the button the table will
    // automatically exhibites all Swim_members
    public void searchMemberBymembership() {
        model.setRowCount(0);
        String[] columnNames = { "memberID", "fName", "sName", "DOB", "houseNoName", "street", "town", "county",
                "postcode", "telephoneNo", "membershipTypeID", "typeName", "price" };
        model.setColumnIdentifiers(columnNames);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/leisure_centre", "user1",
                    "TramAnhTuan1234567890");
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(
                    "select tbl_member.memberID,tbl_member.fName,tbl_member.sName,tbl_member.DOB,tbl_member.houseNoName,tbl_member.street,tbl_member.town,tbl_member.county,tbl_member.postcode,tbl_member.telephoneNo,tbl_member.membershipTypeID,tbl_membership_type.typeName,tbl_membership_type.price from tbl_member inner join tbl_membership_type on tbl_member.membershipTypeID = tbl_membership_type.membershipTypeID where typeName='Swim_Membership'");

            while (rs.next()) {
                String memberID = String.valueOf(rs.getInt(1));
                String fName = rs.getString(2);
                String sName = rs.getString(3);
                String DOB = rs.getString(4);
                String houseNoName = rs.getString(5);
                String street = rs.getString(6);
                String town = rs.getString(7);
                String county = rs.getString(8);
                String postcode = rs.getString(9);
                String telephoneNo = rs.getString(10);
                String membershipTypeID = rs.getString(11);
                String typeName = rs.getString(12);
                String price = rs.getString(13);

                model.addRow(new Object[] { memberID, fName, sName, DOB, houseNoName, street, town, county, postcode,
                        telephoneNo, membershipTypeID, typeName, price });
            }
            con.close();
            stmt.close();
            rs.close();
            
        } catch (Exception err) {
            JOptionPane.showMessageDialog(this, err.getMessage());
        }
    }

    // add method to add the data to the Jtable when the user typed in all of the
    // data want to add.
    public void add() {
        model.setRowCount(0);
        String[] columnNames = { "memberID", "fName", "sName", "DOB", "houseNoName", "street", "town", "county",
                "postcode", "telephoneNo" };
        model.setColumnIdentifiers(columnNames);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/leisure_centre", "user1",
                    "TramAnhTuan1234567890");
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String MemberID = idText.getText();
            String FName = fNameText.getText();
            String SName = sNameText.getText();
            String DateOB = DOBText.getText();
            String HouseNoName = houseNoNameText.getText();
            String Street = StreetText.getText();
            String Town = TownText.getText();
            String County = CountyText.getText();
            String Postcode = PostcodeText.getText();
            String TelephoneNo = telephoneNoText.getText();

            String sql = "INSERT INTO tbl_member (memberID,fName,sName,DOB,houseNoName,street,town,county,postcode,telephoneNo) VALUES ('"
                    + MemberID + "','" + FName + "','" + SName + "','" + DateOB + "','" + HouseNoName + "','" + Street
                    + "','" + Town + "','" + County + "','" + Postcode + "','" + TelephoneNo + "')";
            stmt.executeUpdate(sql);

            rs = stmt.executeQuery(
                    "select memberID,fName,sName,DOB,houseNoName,street,town,county,postcode,telephoneNo from tbl_member");

            while (rs.next()) {
                String memberID = String.valueOf(rs.getInt(1));
                String fName = rs.getString(2);
                String sName = rs.getString(3);
                String DOB = rs.getString(4);
                String houseNoName = rs.getString(5);
                String street = rs.getString(6);
                String town = rs.getString(7);
                String county = rs.getString(8);
                String postcode = rs.getString(9);
                String telephoneNo = rs.getString(10);

                model.addRow(new Object[] { memberID, fName, sName, DOB, houseNoName, street, town, county, postcode,
                        telephoneNo });
            }
            
            con.close();
            stmt.close();
            rs.close();
            
            JOptionPane.showMessageDialog(this, "Record has been added");
        } catch (Exception err) {
            JOptionPane.showMessageDialog(this, err.getMessage());
        }
    }

    // delete method which help user to delete the following data base on memberID
    // after they filled in the table with memberID
    public void delete(String deleteMemberID) {
        model.setRowCount(0);
        String[] columnNames = { "memberID", "fName", "sName", "DOB", "houseNoName", "street", "town", "county",
                "postcode", "telephoneNo" };
        model.setColumnIdentifiers(columnNames);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/leisure_centre", "user1",
                    "TramAnhTuan1234567890");
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            stmt.executeUpdate("delete from tbl_member where +memberID = '" + deleteMemberID + "'");
            rs = stmt.executeQuery(
                    "select memberID,fName,sName,DOB,houseNoName,street,town,county,postcode,telephoneNo from tbl_member");

            while (rs.next()) {
                String memberID = String.valueOf(rs.getInt(1));
                String fName = rs.getString(2);
                String sName = rs.getString(3);
                String DOB = rs.getString(4);
                String houseNoName = rs.getString(5);
                String street = rs.getString(6);
                String town = rs.getString(7);
                String county = rs.getString(8);
                String postcode = rs.getString(9);
                String telephoneNo = rs.getString(10);

                model.addRow(new Object[] { memberID, fName, sName, DOB, houseNoName, street, town, county, postcode,
                        telephoneNo });
            }
            con.close();
            rs.close();
            stmt.close();
            
            JOptionPane.showMessageDialog(this, "Record has been deleted");

        } catch (Exception err) {
            JOptionPane.showMessageDialog(this, err.getMessage());
        }
    }

    // this method allows the user to edit the data after the user searched for the
    // memberID they wanted to edit.
    public void edit(String searchMemberID) {
        model.setRowCount(0);
        String[] columnNames = { "memberID", "fName", "sName", "DOB", "houseNoName", "street", "town", "county",
                "postcode", "telephoneNo" };
        model.setColumnIdentifiers(columnNames);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/leisure_centre", "user1",
                    "TramAnhTuan1234567890");
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(
                    "select memberID,fName,sName,DOB,houseNoName,street,town,county,postcode,telephoneNo from tbl_member where memberID='"
                            + searchMemberID + "'");
            int i =0;               
            while (rs.next()) {
                String memberID = String.valueOf(rs.getInt(1));
                String fName = rs.getString(2);
                String sName = rs.getString(3);
                String DOB = rs.getString(4);
                String houseNoName = rs.getString(5);
                String street = rs.getString(6);
                String town = rs.getString(7);
                String county = rs.getString(8);
                String postcode = rs.getString(9);
                String telephoneNo = rs.getString(10);

                model.addRow(new Object[] { memberID, fName, sName, DOB, houseNoName, street, town, county, postcode,
                        telephoneNo });

                idText.setText(memberID);
                fNameText.setText(fName);
                sNameText.setText(sName);
                DOBText.setText(DOB);
                houseNoNameText.setText(houseNoName);
                StreetText.setText(street);
                TownText.setText(town);
                CountyText.setText(county);
                PostcodeText.setText(postcode);
                telephoneNoText.setText(telephoneNo);
                i++;
            }
            if (i < 1) {
                JOptionPane.showMessageDialog(null, "No Record Found", "Error", JOptionPane.ERROR_MESSAGE);
            }

 
            idText.setEditable(false);
            con.close();
            rs.close();
            stmt.close();
           

        } catch (Exception err) {
            JOptionPane.showMessageDialog(this, err.getMessage());
        }
    }

    // SaveEdit method which Save the edit when the user updates the data, the
    // button to execute this method will be hidden, the button is only visible when
    // the user clicks on the edit button.
    public void saveEdit() {
        model.setRowCount(0);
        String[] columnNames = { "memberID", "fName", "sName", "DOB", "houseNoName", "street", "town", "county",
                "postcode", "telephoneNo" };
        model.setColumnIdentifiers(columnNames);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/leisure_centre", "user1",
                    "TramAnhTuan1234567890");
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            
            String MemberID = idText.getText();
            String FName = fNameText.getText();
            String SName = sNameText.getText();
            String DateOB = DOBText.getText();
            String HouseNoName = houseNoNameText.getText();
            String Street = StreetText.getText();
            String Town = TownText.getText();
            String County = CountyText.getText();
            String Postcode = PostcodeText.getText();
            String TelephoneNo = telephoneNoText.getText();

            stmt.executeUpdate("Update tbl_member set fName='" + FName + "',sName= '" + SName + "',DOB='" + DateOB
                    + "',houseNoName='" + HouseNoName + "',street='" + Street + "',town='" + Town + "',county='"
                    + County + "',postcode='" + Postcode + "',telephoneNo='" + TelephoneNo + "'Where memberID = '"
                    + MemberID + "'");
            idText.setText(MemberID);
            fNameText.setText(FName);
            sNameText.setText(SName);
            DOBText.setText(DateOB);
            houseNoNameText.setText(HouseNoName);
            StreetText.setText(Street);
            TownText.setText(Town);
            CountyText.setText(County);
            PostcodeText.setText(Postcode);
            telephoneNoText.setText(TelephoneNo);

           
            rs = stmt.executeQuery(
                    "select memberID,fName,sName,DOB,houseNoName,street,town,county,postcode,telephoneNo from tbl_member");
            while (rs.next()) {
                String memberID = String.valueOf(rs.getInt(1));
                String fName = rs.getString(2);
                String sName = rs.getString(3);
                String DOB = rs.getString(4);
                String houseNoName = rs.getString(5);
                String street = rs.getString(6);
                String town = rs.getString(7);
                String county = rs.getString(8);
                String postcode = rs.getString(9);
                String telephoneNo = rs.getString(10);

                model.addRow(new Object[] { memberID, fName, sName, DOB, houseNoName, street, town, county, postcode,
                        telephoneNo });
                
            }
            con.close();
            rs.close();
            stmt.close();
            idText.setEditable(true);
            JOptionPane.showMessageDialog(this, "Record has been been updated");
        } catch (Exception err) {
            JOptionPane.showMessageDialog(this, err.getMessage());
        }
    }

    // Reset method which help the user when they want to go back to see the table
    // when it first occupied
    public void Reset() {
        model.setRowCount(0);
        String[] columnNames = { "memberID", "fName", "sName", "DOB", "houseNoName", "street", "town", "county",
                "postcode", "telephoneNo", "membershipTypeID", "typeName", "price" };
        model.setColumnIdentifiers(columnNames);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/leisure_centre", "user1",
                    "TramAnhTuan1234567890");
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(
                    "select tbl_member.memberID,tbl_member.fName,tbl_member.sName,tbl_member.DOB,tbl_member.houseNoName,tbl_member.street,tbl_member.town,tbl_member.county,tbl_member.postcode,tbl_member.telephoneNo,tbl_member.membershipTypeID,tbl_membership_type.typeName,tbl_membership_type.price from tbl_member inner join tbl_membership_type on tbl_member.membershipTypeID = tbl_membership_type.membershipTypeID");

            while (rs.next()) {

                String memberID = String.valueOf(rs.getInt(1));
                String fName = rs.getString(2);
                String sName = rs.getString(3);
                String DOB = rs.getString(4);
                String houseNoName = rs.getString(5);
                String street = rs.getString(6);
                String town = rs.getString(7);
                String county = rs.getString(8);
                String postcode = rs.getString(9);
                String telephoneNo = rs.getString(10);
                String membershipTypeID = rs.getString(11);
                String typeName = rs.getString(12);
                String price = rs.getString(13);

                model.addRow(new Object[] { memberID, fName, sName, DOB, houseNoName, street, town, county, postcode,
                        telephoneNo, membershipTypeID, typeName, price });
            }

            stmt.close();
            rs.close();
            con.close();
        } catch (Exception err) {
            JOptionPane.showMessageDialog(this, err.getMessage());
        }
    }

    // actionPerform method where buttons executed when they are clicked
    public void actionPerformed(ActionEvent evt) {
        if (evt.getActionCommand().equals("SearchMemberInSpringfield")) {
            searchByTown();
            model.fireTableDataChanged();

        }

        if (evt.getActionCommand().equals("SearchSwimMembershipMember")) {
            searchMemberBymembership();
            model.fireTableDataChanged();

        }

        if (evt.getActionCommand().equals("AddMember")) {
            add();
            model.fireTableDataChanged();
        }

        if (evt.getActionCommand().equals("DeleteMember")) {
            String deleteMemberID = DeleteMemberText.getText();
            delete(deleteMemberID);
            model.fireTableDataChanged();
        }

        if (evt.getActionCommand().equals("EditMember")) {
            btnAddMember.setEnabled(false);
            btnDeleteMember.setEnabled(false);
            btnSearchMemberByTown.setEnabled(false);
            btnSearchMemberByTypeName.setEnabled(false);
            btnSaveEdit.setEnabled(true);
            btnReset.setEnabled(false);
            String searchMemberID = EditMemberText.getText();
            edit(searchMemberID);
            model.fireTableDataChanged();
        }
        if (evt.getActionCommand().equals("SaveEdit")) {
            btnAddMember.setEnabled(true);
            btnDeleteMember.setEnabled(true);
            btnSearchMemberByTown.setEnabled(true);
            btnSearchMemberByTypeName.setEnabled(true);
            btnSaveEdit.setEnabled(false);
            btnEditMember.setEnabled(true);
            btnReset.setEnabled(true);
            saveEdit();     
            model.fireTableDataChanged();
        }

        if (evt.getActionCommand().equals("Reset")) {
            Reset();
            model.fireTableDataChanged();
        }

    }

    // Main method which is the method that execute the program.
    public static void main(String[] args) {
        WhitburyNewtownLeisureCentre GUI = new WhitburyNewtownLeisureCentre("Membership Management");
        GUI.setSize(900,900);
        GUI.setVisible(true);
    }

}
