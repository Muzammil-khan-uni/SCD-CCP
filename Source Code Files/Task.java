class Task {
    private String description;
    private boolean completed;
    private TimeTracker time_tracker;

    public Task(String description) {
        this.description = description;
        this.completed = false;
        this.time_tracker = new TimeTracker();
        this.time_tracker.record_creation_time();
    }
//returns description of new task
    public String get_description() {
        return description;
    }
 // Returns true if the task is completed, otherwise returns false.
    public boolean is_completed() {
        return completed;
    }

    public void mark_as_completed() {
        this.completed = true;
        this.time_tracker.record_completion_time();
    }

    public String get_completion_time() {
        return time_tracker.get_completion_time();
    }

    public String get_duration() {
        return time_tracker.get_duration();
    }

}