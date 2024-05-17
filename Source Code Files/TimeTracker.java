import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeTracker {
    private LocalDateTime creation_time;
    private LocalDateTime completion_time;

    public void record_creation_time() {
        this.creation_time = LocalDateTime.now();
    }

    public void record_completion_time() {
        this.completion_time = LocalDateTime.now();
    }

    public String get_completion_time() {
        try {
            if (completion_time != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                return completion_time.format(formatter);
            }
            return "Not completed";
        } catch (Exception e) {
            return "Error retrieving completion time";
        }
    }

    public String get_duration() {
        try {
            if (completion_time != null) {
                Duration duration = Duration.between(creation_time, completion_time);
                long seconds = duration.getSeconds();
                long hours = seconds / 3600;
                seconds %= 3600;
                long minutes = seconds / 60;
                seconds %= 60;
                return String.valueOf(hours) + ":" + String.valueOf(minutes) + ":" + String.valueOf(seconds);
            }
            return "Task not completed";
        } catch (Exception e) {
            return "Error calculating duration";
        }
    }

}
