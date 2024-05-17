import java.util.ArrayList;

public class TaskListManager {

    public ArrayList<Task> tasks;

    public TaskListManager() {
        tasks = new ArrayList<>();
    }

    public void add_task(Task task) {
        tasks.add(task);
    }
 // Method to remove a task from the task list based on its index
    public Task remove_task(int index) {
        if (index >= 0 && index < tasks.size()) {
            return tasks.remove(index);
        }
        return null;
    }
    // Method to retrieve all tasks in the task list
    public ArrayList<Task> get_tasks() {
        return tasks;
    }
}
