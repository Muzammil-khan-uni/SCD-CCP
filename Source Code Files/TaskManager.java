import java.util.ArrayList;

class TaskManager {
    private TaskListManager to_do_list_manager;
    private TaskListManager in_progress_list_manager;
    private TaskListManager completed_list_manager;

    public TaskManager() {
        to_do_list_manager = new TaskListManager();
        in_progress_list_manager = new TaskListManager();
        completed_list_manager = new TaskListManager();
    }

    public void add_task(Task task) {
        try {
            if (task != null) {
                to_do_list_manager.add_task(task);
            } else {
                throw new IllegalArgumentException("Task cannot be null");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void move_task_to_in_progress(int index) {
        Task task = to_do_list_manager.remove_task(index);
        if (task != null) {
            in_progress_list_manager.add_task(task);
        }
    }

    public void move_task_to_completed(int index) {
        Task task = in_progress_list_manager.remove_task(index);
        if (task != null) {
            task.mark_as_completed();
            completed_list_manager.add_task(task);
        }
    }
    // Returns the to-do list of tasks
    public ArrayList<Task> get_to_do_list() {
        return to_do_list_manager.get_tasks();
    }
    // Returns the in-progress list of tasks
    public ArrayList<Task> get_in_progress_list() {
        return in_progress_list_manager.get_tasks();
    }
 // Returns the completed list of tasks
    public ArrayList<Task> get_completed_list() {
        return completed_list_manager.get_tasks();
    }
}