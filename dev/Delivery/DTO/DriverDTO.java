package Delivery.DTO;

import Delivery.BusinessLayer.Driver;
import Delivery.BusinessLayer.Employee;

public class DriverDTO {
    private int licenseType;
//    private Employee employee;
    private String employeeName;

    public int getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(int licenseType) {
        this.licenseType = licenseType;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public DriverDTO(int licenseType, String employeeName){
        this.licenseType = licenseType;
        this.employeeName = employeeName;
    }
    public String toString() {
        return "Driver{" +
                "licenseType=" + licenseType +
                ", employee=" + employeeName +
                '}';
    }

    public boolean equals(Object other){
        if (other instanceof DriverDTO){
            DriverDTO oth = (DriverDTO) other;
            return oth.getEmployeeName().equals(getEmployeeName());
        }
        return false;
    }
}