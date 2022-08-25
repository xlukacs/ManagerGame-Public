package scenes.admin;

import core.Scene;
import core.Settings;
import managers.SceneManager;
import roles.Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

/**
 * This is an overview window, where the roles can be seen while listing users.
 */
public class ListUsers extends Scene{

    public ListUsers(int sceneID, SceneManager manager) {
        super(sceneID, manager);
    }

    public void onInit(){
        populateTable();
    }

    public void onActivate(){
        populateTable();
    }

    /**
     * List all users with their roles / ranks.
     * Add them to a new JTable and make them scrollable.
     */
    public void populateTable() {
        getListOfObjects().clear();
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Username");
        model.addColumn("Role");

        Vector<Vector<String>>rowVector = new Vector<>();
        manager.getLoginManager().getUsers().forEach(user -> {
            Vector<String> columnVector = new Vector<>();
            columnVector.add("   " + user.getUserId());
            columnVector.add(" " + user.getUsername());
            if (user.returnWhoIAm() instanceof Admin)
                columnVector.add("  Admin");
            else
                columnVector.add("  Guest");
            rowVector.add(columnVector);
        });
        rowVector.forEach(model::addRow);

        JTable list = new JTable(model);
        list.setDefaultEditor(Object.class, null);
        list.setRowHeight(30);

        JScrollPane niceList = new JScrollPane(list);
        niceList.setBounds(0, 0, Settings.windowWidth, Settings.finalHeight);

        getListOfObjects().add(niceList);
    }
}
