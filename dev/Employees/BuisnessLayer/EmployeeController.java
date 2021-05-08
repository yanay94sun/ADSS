package Employees.BuisnessLayer;

import Employees.DataAccessLayer.DAOs.EmployeeDAO;
import Employees.DataAccessLayer.DTOs.EmployeeDTO;
import Employees.DataAccessLayer.Objects.Mapper;

import java.time.LocalDate;
import java.util.HashMap;

public class EmployeeController {
    private static EmployeeController employeeController;
    HashMap<String, Employee> employees;
    Mapper mapper;
    EmployeeDAO dao;

    private EmployeeController()
    {
        employees = new HashMap<>();
        mapper = Mapper.getInstance();
        dao = new EmployeeDAO();
    }

    public static EmployeeController getInstance(){
        if (employeeController == null)
            employeeController = new EmployeeController();
        return employeeController;
    }

    public Response AddEmployee(String ID, String name, String bankAccount, int salary,
                            int sickDays, int studyFund, int daysOff, String roleName, LocalDate _dateOfHire){
        if (sickDays < 0 || studyFund < 0 || daysOff < 0)
            return new Response("All values of terms must be greater than 0");
        TermsOfEmployee terms = new TermsOfEmployee(sickDays, studyFund, daysOff);
        Role role = new Role(roleName);
        Employee e;
        try{
             e = new Employee(name, ID, _dateOfHire);
        }catch (IllegalArgumentException exception){
            return new Response(exception.getMessage());
        }
        e.setTerms(terms);
        e.setBankAccount(bankAccount);
        e.setSalary(salary);
        e.AddRole(role);
        e.setDTO(dao);
        Response r = e.persist();
        if (!r.isErrorOccured())
            employees.put(e.getID().getValue(), e);
        return r;
    }

    public ResponseT<Employee> getEmployee(String id){
        if (employees.containsKey(id))
            return new ResponseT<>(employees.get(id));
        ResponseT<EmployeeDTO> empDto = mapper.getEmployee(id);
        if(!empDto.isErrorOccured()){
            Employee e = new Employee(empDto.getValue());
            employees.put(e.getID().getValue(), e);
            return new ResponseT<>(e);
        }
        return new ResponseT<>(null, empDto.getErrorMessage());
    }

    public Response setEmpName(String ID, String newEmpName) {
        ResponseT<Employee> rE = getEmployee(ID);
        if(rE.isErrorOccured())
            return rE;
        return rE.getValue().setName(newEmpName);
    }

    public Response updateEmpBankAccount(String ID, String newBankAccount) {
        ResponseT<Employee> rE = getEmployee(ID);
        if(rE.isErrorOccured())
            return rE;
        return rE.getValue().setBankAccount(newBankAccount);
    }

    public Response updateEmpSalary(String ID, int newSalary) {
        ResponseT<Employee> rE = getEmployee(ID);
        if(rE.isErrorOccured())
            return rE;
        return rE.getValue().setSalary(newSalary);
    }

    public Response updateEmpSickDays(String ID, int updatedSickDays) {
        ResponseT<Employee> rE = getEmployee(ID);
        if(rE.isErrorOccured())
            return rE;
        return rE.getValue().getTerms().setSickDays(updatedSickDays);
    }

    public Response updateEmpStudyFund(String ID, int newStudyFund) {
        ResponseT<Employee> rE = getEmployee(ID);
        if(rE.isErrorOccured())
            return rE;
        return rE.getValue().getTerms().setAdvancedStudyFund(newStudyFund);
    }

    public Response updateEmpDaysOff(String ID, int newDaysOff) {
        ResponseT<Employee> rE = getEmployee(ID);
        if(rE.isErrorOccured())
            return rE;
        return rE.getValue().getTerms().setDaysOff(newDaysOff);
    }

    public Response addRoleToEmp(String ID, String role) {
        ResponseT<Employee> rE = getEmployee(ID);
        if(rE.isErrorOccured())
            return rE;
        return rE.getValue().AddRole(new Role(role));
    }

    public ResponseT<String> getEmpData(Employee employee) {
        return employee.getEmpDataTostring();
    }

    public void initData() {
        AddEmployee("312174295", "Yanay", "12345", 1000, 30, 500, 30,
                "General Manager", LocalDate.now());
        AddEmployee("205952971", "Nitzan", "12345", 1000, 30, 500, 30,
                "HR Manager", LocalDate.now());
        AddEmployee("123456789", "Dana", "12345", 1000, 30, 500, 30,
                "Cashier", LocalDate.now());
        addRoleToEmp("123456789", "Cleaner");
        AddEmployee("987654321", "Dafna", "54321", 10000, 30, 500, 30,
                "Storage", LocalDate.now());
        addRoleToEmp("987654321", "Driver");

    }

}

