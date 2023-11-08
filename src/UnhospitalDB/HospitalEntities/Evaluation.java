package UnhospitalDB.HospitalEntities;

public class Evaluation {

    // Attributes
    private int id;



    private int visitID;
    private String physicalExamination;
    private String medicalHistory;


    //Relations
    private Visit visit; //FK Visit ID (Onr-to-One relationship)

    // Constructors
    public Evaluation() {}
    public Evaluation(String physicalExamination, String medicalHistory) {
        this.physicalExamination = physicalExamination;
        this.medicalHistory = medicalHistory;
    }


    //ID
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    //Physical Exam
    public String getPhysicalExamination() {
        return physicalExamination;
    }
    public void setPhysicalExamination(String physicalExamination) {
        this.physicalExamination = physicalExamination;
    }


    //Medical History
    public String getMedicalHistory() {
        return medicalHistory;
    }
    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }


    //Related Visit
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
