/**
 * Created by alexhoffman on 4/24/17.
 */
public class Project {

    private ProjectStatus status;
    private String projectTitle;
    private String email;
    private String startDate;
    private String endDate;
    private String dueDate;
    private int songCount;
    private String serviceType;
    private Double totalCost;


    public Project(String projectTitle, String email, String startDate, String endDate, String dueDate, String songCount, String serviceType, String totalCost) {
        this.status = ProjectStatus.NEW;
        this.projectTitle = projectTitle;
        this.email = email;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dueDate = dueDate;
        this.songCount = Integer.parseInt(songCount);
        this.serviceType = serviceType;
        this.totalCost = Double.parseDouble(totalCost);

    }

    public void Project (){

    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
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
}
