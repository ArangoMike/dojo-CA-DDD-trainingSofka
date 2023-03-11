package co.com.sofka.mongo.data;


public class AppointmentDTO {

    protected String appointmentId;
    protected  String appointmentDate;
    protected  String medicalCheckup;

    public String getAppointmentId() {
        return appointmentId;
    }

    public AppointmentDTO(String appointmentId, String appointmentDate, String medicalCheckup) {
        this.appointmentId = appointmentId;
        this.appointmentDate = appointmentDate;
        this.medicalCheckup = medicalCheckup;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getMedicalCheckup() {
        return medicalCheckup;
    }

    public void setMedicalCheckup(String medicalCheckup) {
        this.medicalCheckup = medicalCheckup;
    }
}
