package UnhospitalDB.HospitalEntities;

public class Treatment {

    // Attributes
    private int id;
    private int visitID;
    private String treatmentName;
    private String treatmentDescription;

    //Relations
    private Visit visit;


    // Constructors
    public Treatment() {}
    public Treatment(String treatmentName, String treatmentDescription) {
        this.treatmentName = treatmentName;
        this.treatmentDescription = treatmentDescription;
    }


    // ID
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    // Treatment Name
    public String getTreatmentName() {
        return treatmentName;
    }
    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }


    // Treatment Description
    public String getTreatmentDescription() {
        return treatmentDescription;
    }
    public void setTreatmentDescription(String treatmentDescription) {
        this.treatmentDescription = treatmentDescription;
    }

    //Visit
    public Visit getVisit() {
        return visit;
    }
    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    //FK Visit ID
    public int getVisitID() {
        return visitID;
    }
    public void setVisitID(int visitID) {
        this.visitID = visitID;
    }
}
