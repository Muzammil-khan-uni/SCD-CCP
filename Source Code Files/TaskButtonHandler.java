import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TaskButtonHandler extends JPanel implements ActionListener {
    private TaskManager task_manager;
    private Task task;
    private TaskManagementGUI gui; 
    public TaskButtonHandler(TaskManagementGUI gui, TaskManager task_manager, Task task) {
        this.gui = gui; 
        this.task_manager = task_manager;
        this.task = task;

        setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton delete_button = new JButton("Delete");
        delete_button.addActionListener(this);
        add(delete_button);

        JButton details_button = new JButton("Details");
        details_button.addActionListener(this);
        add(details_button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getActionCommand().equals("Delete")) {
                handle_delete();
            } else if (e.getActionCommand().equals("Details")) {
                handle_details();
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void handle_delete() {
        if (task.is_completed()) {
            task_manager.get_completed_list().remove(task);
        } else {
            if (task_manager.get_to_do_list().contains(task)) {
                task_manager.get_to_do_list().remove(task);
            } else if (task_manager.get_in_progress_list().contains(task)) {
                task_manager.get_in_progress_list().remove(task);
            }
        }
     // Updating the GUI after deletion
        gui.update_lists();
    }

    private void handle_details() {
        JOptionPane.showMessageDialog(this, "Task Completed at: " + task.get_completion_time() + "\nTask Duration: " + task.get_duration());
    }
}