package UnhospitalDB.HospitalEntities;

import javax.tools.Diagnostic;
import java.util.List;

public class Visit {

    // Attributes
    private int id;
    private int patientID;
    private String date;
    private String reasonForVisit;


    public enum Derivation{
        General_Practice, Traumatology, Internal_Medicine
    }
    private Derivation derivation;

    //Relations
    private Patient patient; // FK Patient ID (One-to-One relationship)
    private Evaluation evaluation; // FK Evaluation ID (One-to-One relationship)
    private Employee employee; // FK Employee ID (One-to-One relationship)
    private List<javax.tools.Diagnostic> diagnostics; // FK Diagnostic ID (One-to-Many relationship)
    private List<Treatment> treatments; // FK Treatment ID (One-to-Many relationship)

    // Constructors
    public Visit() {}
    public Visit(String date, String reasonForVisit, Derivation derivation, int patientID) {
        this.date = date;
        this.reasonForVisit = reasonForVisit;
        this.derivation = derivation;
        this.patientID=patientID;
    }


    //ID
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    //Date
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }


    //Reason for Visit
    public String getReasonForVisit() {
        return reasonForVisit;
    }
    public void setReasonForVisit(String reasonForVisit) {
        this.reasonForVisit = reasonForVisit;
    }


    //Derivation
    public Derivation getSpecialityDerivation() {
        return derivation;
    }
    public String getDerivationAsString() {
        return derivation.name();
    }
    public void setSpecialityDerivation(Derivation specialityDerivation) {
        this.derivation = specialityDerivation;
    }
    public void setDerivationFromString(String derivationString) {
        this.derivation = Visit.Derivation.valueOf(derivationString);
    }

    //Related Patient
    public Patient getPatient() {
        return patient;
    }
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    //Assigned Doctor
    public Employee getEmployee() {
        return employee;
    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    //Evaluation
    public Evaluation getEvaluation() {
        return evaluation;
    }
    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }


    //Diagnostics
    public List<javax.tools.Diagnostic> getDiagnostics() {
        return diagnostics;
    }
    public void setDiagnostics(List<Diagnostic> diagnostics) {
        this.diagnostics = diagnostics;
    }


    //Treatments
    public List<Treatment> getTreatments() {
        return treatments;
    }
    public void setTreatments(List<Treatment> treatments) {
        this.treatments = treatments;
    }

    //FK Patient ID
    public int getPatientID() {
        return patientID;
    }
    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

}
