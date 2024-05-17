import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TaskManagementGUI extends JFrame implements ActionListener {
    private TaskManager task_manager;
    private JTextPane to_do_area;
    private JTextPane in_progress_area;
    private JTextPane completed_area;
    private JButton add_task_button;
    private JButton move_in_progress_button;
    private JButton move_completed_button;
    private JButton exit_button; 

    public TaskManagementGUI() {
        task_manager = new TaskManager();
        setTitle("Task Management Application");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel main_panel = new JPanel(new GridLayout(1, 3));
        
     // Text panes for displaying tasks
        to_do_area = new JTextPane();
        in_progress_area = new JTextPane();
        completed_area = new JTextPane();
        
     // Scroll panes for task list text panes
        JScrollPane to_do_scroll_pane = new JScrollPane(to_do_area);
        JScrollPane in_progress_scroll_pane = new JScrollPane(in_progress_area);
        JScrollPane completed_scroll_pane = new JScrollPane(completed_area);

        add_task_button = new JButton("Add Task");
        move_in_progress_button = new JButton("Move to In Progress");
        move_completed_button = new JButton("Move to Completed");
        exit_button = new JButton("Exit"); // Instantiate the exit button

        add_task_button.addActionListener(this);
        move_in_progress_button.addActionListener(this);
        move_completed_button.addActionListener(this);
        exit_button.addActionListener(this); 

        JPanel button_panel = new JPanel(new GridLayout(4, 1)); 
        button_panel.add(add_task_button);
        button_panel.add(move_in_progress_button);
        button_panel.add(move_completed_button);
        button_panel.add(exit_button); 

        main_panel.add(create_panel_with_label(to_do_scroll_pane, "To Do", new Font("Arial", Font.BOLD, 30), Color.BLUE));
        main_panel.add(create_panel_with_label(in_progress_scroll_pane, "In Progress", new Font("Arial", Font.BOLD, 30), Color.RED));
        main_panel.add(create_panel_with_label(completed_scroll_pane, "Completed", new Font("Arial", Font.BOLD, 30), Color.GREEN));

        add(main_panel, BorderLayout.CENTER);
        add(button_panel, BorderLayout.EAST);
    }
 // Method to create a panel with a label of task list name
    private JPanel create_panel_with_label(Component component, String label, Font font, Color color) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel title_label = new JLabel(label, SwingConstants.CENTER);
        title_label.setFont(font);
        title_label.setForeground(color);
        panel.add(title_label, BorderLayout.NORTH);
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }
 // Method to update the task lists displayed on the GUI
    public void update_lists() {
        to_do_area.setText("");
        in_progress_area.setText("");
        completed_area.setText("");

        Font list_font = new Font("Arial", Font.PLAIN, 24); 
        Color list_color = Color.BLACK; 

        // Displaying tasks in each list
        int todo_count = 1;
        for (Task task : task_manager.get_to_do_list()) {
            append_text_with_font_and_color(to_do_area, todo_count++ + ". " + task.get_description() + " ", list_font, list_color);
            add_button(to_do_area, task);
            append_new_line(to_do_area);
        }

        int in_progress_count = 1;
        for (Task task : task_manager.get_in_progress_list()) {
            append_text_with_font_and_color(in_progress_area, in_progress_count++ + ". " + task.get_description() + " ", list_font, list_color);
            add_button(in_progress_area, task);
            append_new_line(in_progress_area);
        }

        int completed_count = 1;
        for (Task task : task_manager.get_completed_list()) {
            append_text_with_font_and_color(completed_area, completed_count++ + ". " + task.get_description() + " ", list_font, list_color);
            add_button(completed_area, task);
            append_new_line(completed_area);
        }
    }

    private void append_new_line(JTextPane text_pane) {
        text_pane.replaceSelection("\n");
    }

    private void append_text_with_font_and_color(JTextPane text_pane, String text, Font font, Color color) {
        SimpleAttributeSet attribute_set = new SimpleAttributeSet();
        StyleConstants.setFontFamily(attribute_set, font.getFamily());
        StyleConstants.setFontSize(attribute_set, font.getSize());
        StyleConstants.setForeground(attribute_set, color);
        text_pane.setCharacterAttributes(attribute_set, false);
        text_pane.replaceSelection(text);
    }
 // Method to add a button to a text pane for a task
    private void add_button(JTextPane text_pane, Task task) {
        TaskButtonHandler button_handler = new TaskButtonHandler(this, task_manager, task);
        button_handler.putClientProperty("task", task); 
        text_pane.insertComponent(button_handler);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == add_task_button) {
                String description = JOptionPane.showInputDialog("Enter task description:");
                if (description != null && !description.isEmpty()) {
                    Task new_task = new Task(description);
                    task_manager.add_task(new_task);
                    update_lists();
                }
            } else if (e.getSource() == move_in_progress_button) {
                String selected_text = to_do_area.getSelectedText();
                if (selected_text != null && !selected_text.isEmpty()) {
                    int index = to_do_area.getText().indexOf(selected_text);
                    task_manager.move_task_to_in_progress(index);
                    update_lists();
                } else {
                	JOptionPane.showMessageDialog(this, "Please select a task from the To Do list.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (e.getSource() == move_completed_button) {
                String selected_text = in_progress_area.getSelectedText();
                if (selected_text != null && !selected_text.isEmpty()) {
                    int index = in_progress_area.getText().indexOf(selected_text);
                    task_manager.move_task_to_completed(index);
                    update_lists();
                } else {
                	JOptionPane.showMessageDialog(this, "Please select a task from the In Progress list.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (e.getSource() == exit_button) {
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TaskManagementGUI task_management_GUI = new TaskManagementGUI();
                task_management_GUI.setVisible(true);
            }
        });
    }
}
