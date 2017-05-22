/**
 * Created by alexhoffman on 4/24/17.
 */
public class Project {

    private String status;
    private String projectTitle;
    private String email;
    private String startDate;
    private String endDate;
    private String dueDate;
    private int songCount;
    private String serviceType;
    private Double totalCost;


    public Project(String projectTitle, String email, String startDate, String endDate, String dueDate, String songCount, String serviceType, String totalCost) {
        this.status = ProjectStatus.NEW.toString();
        this.projectTitle = projectTitle;
        this.email = email;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dueDate = dueDate;
        this.songCount = Integer.parseInt(songCount);
        this.serviceType = serviceType;
        this.totalCost = Double.parseDouble(totalCost);

    }

    public Project(String project_title, String email, String due_date, String song_count, String service_type, String project_status, String total_cost) {
        this.projectTitle = project_title;
        this.email = email;
        this.dueDate = due_date;
        this.songCount = Integer.parseInt(song_count);
        this.serviceType = service_type;
        this.status = project_status;
        this.totalCost = Double.parseDouble(total_cost);
    }


    public String getEmail() {
        return email;
    }

    public String getTitle() {
        return projectTitle;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public int getSongCount() {
        return songCount;
    }

    public String getServiceType() {
        return serviceType;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public String getStatus() {
        return status;
    }
}
