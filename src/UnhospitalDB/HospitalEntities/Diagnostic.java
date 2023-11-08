package UnhospitalDB.HospitalEntities;

public class Diagnostic {

    //Attributes
    private int id;
    private int visitID;
    private String testName;
    private String testResults;

    //Relations
    private Visit visit;


    //Constructors
    public Diagnostic() {}
    public Diagnostic(String testName, String testResults) {
        this.testName = testName;
        this.testResults = testResults;
    }


    //ID
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    //Test Name
    public String getTestName() {
        return testName;
    }
    public void setTestName(String testName) {
        this.testName = testName;
    }


    //Test Results
    public String getTestResults() {
        return testResults;
    }
    public void setTestResults(String testResults) {
        this.testResults = testResults;
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
