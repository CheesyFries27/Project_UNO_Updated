import java.time.LocalDate;

public class Tasks{

    private int id;
    private String title;           
    private String description;     
    private int priority;           // 0 = none, 1 = low, 2 = medium, 3 = high
    private LocalDate dueDate;      // optional
    private boolean completed;
    private int createdBy;          // id of user who made it
    private int assignedTo;         // id of user it's assigned to

    public Tasks(String title, String description, int priority,
                LocalDate dueDate, int createdBy, int assignedTo) {
    	
        setTitle(title);            
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.createdBy = createdBy;
        this.assignedTo = assignedTo;
        this.completed = false;
    }

    public void markComplete() {
        this.completed = true;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title is required");
        }
        this.title = title.trim();
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public int getCreatedBy() { return createdBy; }
    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }

    public int getAssignedTo() { return assignedTo; }
    public void setAssignedTo(int assignedTo) { this.assignedTo = assignedTo; }
}






