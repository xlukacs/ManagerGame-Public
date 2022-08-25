package objects.inc;

import core.FileErrorException;
import core.Scene;
import core.Settings;
import core.User;
import managers.FileManager;
import managers.UserManager;
import objects.CustomButton;
import objects.CustomLabel;
import objects.CustomPasswordField;
import objects.CustomTextField;
import roles.Admin;
import roles.Customer;
import roles.Guest;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static core.Settings.windowWidth;

/**
 * In admin panel these rows will be displayed as a tweaked JPanel.
 * This class has event handlers to open editing windows in which the admin can delete or modify user records.
 *
 * There is also built in option which can be set at each instance, how each row should be displayed.
 */
public class Row extends JPanel implements ActionListener {
    Scene parent;

    User rowData;
    UserManager manager;
    CustomButton saveButton;
    CustomButton deleteButton;

    //EDIT WINDOW DATA
    JFrame editWindow;

    CustomTextField usernameDisplay;
    CustomPasswordField passwordDisplay;
    CustomTextField firstNameDisplay;
    CustomTextField surNameDisplay;

    JComboBox rankList;

    /**
     * How should the row be displayed - horizontally or vertically.
     */
    public enum Direction{
        HORIZONTAL,
        VERTICAL
    }

    /**
     * Constructor for creating the layout and adding elements to it whit their own value.
     *
     * @param parent What is the parent scene
     * @param manager The manager, which can modify user data
     * @param user Who is the user, whose data should be in the row
     * @param direction Is this a horizontal or a vertical row
     */
    public Row(Scene parent, UserManager manager, User user, Direction direction){
        this.parent = parent;
        this.manager = manager;
        this.rowData = user;

        //Decide if the user wants horizontal or vertical row
        if (direction == Direction.HORIZONTAL)
            setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        else
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        //Set row characteristics
        setMaximumSize(new Dimension(windowWidth, 40));
        centerThis();
        setBackground(Color.GRAY);
        setBorder(new MatteBorder(0, 0, 2, 0, Color.GRAY));


        //Add elements to the row
        CustomLabel username = new CustomLabel(rowData.getUsername(), new Insets(0, 10, 0, 10));
        username.setMaximumSize(new Dimension((int) (windowWidth*0.4), 40));
        username.setForeground(Color.WHITE);
        add(username);

        CustomLabel password = new CustomLabel(rowData.getPassword(), new Insets(0, 10, 0, 10));
        password.setMaximumSize(new Dimension((int) (windowWidth*0.4), 40));
        password.setForeground(Color.WHITE);
        add(password);

        JButton btnForEditing = new JButton("Edit");
        btnForEditing.setMaximumSize(new Dimension((int) (windowWidth*0.17), 40));
        btnForEditing.addActionListener(e -> {
            this.openEditForUser();
        });
        add(btnForEditing);
    }

    /**
     * Secondary constructor for also setting the background color.
     *
     * @param parent
     * @param manager
     * @param user
     * @param direction
     * @param bgColor
     */
    public Row(Scene parent, UserManager manager, User user, Direction direction, Color bgColor){
        this(parent, manager, user, direction);

        setBackground(bgColor);
    }

    /**
     * Method for centering the elements at the absolute center point on the X axis.
     */
    private void centerThis(){
        this.setLocation(Settings.windowWidth / 2 - getWidth() / 2, getY());
    }

    /**
     * Method which creates new frames and panel in order to show more detailed user data of the selected user.
     * It has methods to handle changes or user deletion.
     */
    private void openEditForUser(){
        editWindow = new JFrame();
        Settings.setupWindow(editWindow, "icon2.png", "User record editing tool", 300 , 350, manager.getFileManager());
        editWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel userDataSheet = new JPanel();
        userDataSheet.setLayout(null);
        userDataSheet.setSize(300, 350);

        CustomLabel usernameLabel = new CustomLabel("Username: ");
        CustomLabel passwordLabel = new CustomLabel("Password: ");
        CustomLabel firstNameLabel = new CustomLabel("Firstname: ");
        CustomLabel surNameLabel = new CustomLabel("Surname: ");
        usernameLabel.setBounds(10,10, 100,20);
        passwordLabel.setBounds(10,75, 100,20);
        firstNameLabel.setBounds(10,140, 100,20);
        surNameLabel.setBounds(10,205, 100,20);

        usernameDisplay = new CustomTextField(rowData.getUsername());
        passwordDisplay = new CustomPasswordField(rowData.getPassword());
        firstNameDisplay = new CustomTextField(rowData.returnWhoIAm().getName().getFirstName());
        surNameDisplay = new CustomTextField(rowData.returnWhoIAm().getName().getSurName());
        usernameDisplay.setBoundsAndCenter(10,35, 250,30, 300);
        passwordDisplay.setBoundsAndCenter(10,100, 250,30, 300);
        firstNameDisplay.setBoundsAndCenter(10,165, 250,30, 300);
        surNameDisplay.setBoundsAndCenter(10,230, 250,30, 300);

        String [] ranks = {"Guest","Customer","Admin"};
        rankList = new JComboBox(ranks);
        rankList.setBounds(25, 270, 130, 30);
        if(rowData.returnWhoIAm() instanceof Guest)
            rankList.setSelectedIndex(0);
        if(rowData.returnWhoIAm() instanceof Customer)
            rankList.setSelectedIndex(1);
        if(rowData.returnWhoIAm() instanceof Admin)
            rankList.setSelectedIndex(2);

        deleteButton = new CustomButton(190, 5, 70, 25, "Delete");
        deleteButton.addActionListener(this);

        saveButton = new CustomButton(195, 270, 80, 30, "Save");
        saveButton.addActionListener(this);

        userDataSheet.add(usernameLabel);
        userDataSheet.add(passwordLabel);
        userDataSheet.add(firstNameLabel);
        userDataSheet.add(surNameLabel);

        userDataSheet.add(usernameDisplay);
        userDataSheet.add(passwordDisplay);
        userDataSheet.add(firstNameDisplay);
        userDataSheet.add(surNameDisplay);

        userDataSheet.add(rankList);
        userDataSheet.add(deleteButton);
        userDataSheet.add(saveButton);

        editWindow.add(userDataSheet);

        editWindow.repaint();
    }

    /**
     * Detect what was the target of the click on the detailed frame.
     *
     * @param e Event variable
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == saveButton) {
            try {
                changeRecords(rowData);
            } catch (IOException | FileErrorException ex) {
                ex.printStackTrace();
            }
        }

        if (e.getSource() == deleteButton){
            try {
                deleteUser(rowData);
            } catch (IOException ioException) {
                //ioException.printStackTrace();
            } catch (FileErrorException fileErrorException) {
                //fileWriteException.printStackTrace();
                //throw new FileWriteException("Failed to delete user.");
            }
        }
    }

    /**
     * Method for changing user records. This is done by copying ,removing and adding back the user.
     * After the changes has been made, then update the internal ArrayList and serialise the changes.
     *
     * @param user What users data should be changed
     * @throws IOException
     * @throws FileErrorException
     */
    public void changeRecords(User user) throws IOException, FileErrorException {
        manager.setUsers(manager.getFileManager().removeUserFromList(user, manager.getUsers()));

        User newUser;
        if (rankList.getSelectedIndex() == 0)
            newUser = manager.getFileManager().createUser(usernameDisplay.getText(), String.valueOf(passwordDisplay.getPassword()), new Guest(user.returnWhoIAm().getName().getFirstName(), user.returnWhoIAm().getName().getSurName(), 44));
        else if(rankList.getSelectedIndex() == 1)
            newUser = manager.getFileManager().createUser(usernameDisplay.getText(), String.valueOf(passwordDisplay.getPassword()), new Customer(user.returnWhoIAm().getName().getFirstName(), user.returnWhoIAm().getName().getSurName(), 44));
        else
            newUser = manager.getFileManager().createUser(usernameDisplay.getText(), String.valueOf(passwordDisplay.getPassword()), new Admin(user.returnWhoIAm().getName().getFirstName(), user.returnWhoIAm().getName().getSurName(), 44));

        //add new user to existing lists and serialise it
        manager.getUsers().add(newUser);
        manager.getFileManager().writeToFile(newUser, manager.getUsers(), FileManager.Action.RETAIN);

        //update scene information
        parent.onActivate();
        parent.getManager().updateScene();

        //close this window
        editWindow.dispose();
    }

    /**
     * Method for deleting the selected user from the known user list.
     *
     * @param user Who will be deleted
     * @throws IOException
     * @throws FileErrorException
     */
    public void deleteUser(User user) throws IOException, FileErrorException {
        //update existing list and serialise once again the list
        manager.setUsers(manager.getFileManager().removeUserFromList(user, manager.getUsers()));
        manager.getFileManager().writeToFile(user, manager.getUsers(),FileManager.Action.DELETE);

        //update the information of the scene
        parent.onActivate();
        parent.getManager().updateScene();

        //close this window
        editWindow.dispose();
    }
}
