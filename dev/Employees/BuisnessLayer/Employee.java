package Employees.BuisnessLayer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Employee {


    private String name;
    private String ID;
    private String bankAccount;
    private int salary;
    private List<Role> roles;
    private TermsOfEmployee terms;
    private LocalDate dateOfHire;

    public Employee(String _name, String _ID, LocalDate _dateOfHire) {
        name = _name;
        ID = _ID;
        dateOfHire = _dateOfHire;
        roles = new ArrayList<>();
    }

    public ResponseT<String> getName() {
        return new ResponseT<String>(name);
    }


    public Response setName(String name) {
        this.name = name;
        return new Response();
    }

    public ResponseT<String> getID() {
        return new ResponseT<String>(ID);
    }

    public ResponseT<String> getBankAccount() {
        return new ResponseT<String>(bankAccount);
    }

    public ResponseT<Integer> getSalary() {
        return new ResponseT<Integer>(salary);
    }


    public ResponseT<List<Role>> getRoles() {
        return new ResponseT<>(roles);
    }

    public ResponseT<TermsOfEmployee> getTerms() {
        return new ResponseT<>(terms);
    }

    public Response setSalary(int salary) {
        this.salary = salary;
        return new Response();
    }

    public Response setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
        return new Response();
    }

    public Response AddRole(Role role){
        roles.add(role);
        return new Response();
    }

    public Response setTerms(TermsOfEmployee terms) {
        this.terms = terms;
        return new Response();
    }

    public ResponseT<Boolean> haveRoleCheck(String roleToCheck) {
        if (!roles.isEmpty()) {
            for (Role role : roles) {
                if (role.compare(roleToCheck))
                    return new ResponseT<Boolean>(true);
            }
            return new ResponseT<Boolean>(false);
        }
        return new ResponseT<Boolean>(false, "Error, No Rules Found");
    }


    //Check if this employee is HR/generarManager authorize
    public ResponseT<Boolean> checkAuthorizedHrOrGenral(){ //TODO; how should we work with those strings??
        if(this.haveRoleCheck("HR Manager").getValue() || this.haveRoleCheck("Genral Manager").getValue())
            return new ResponseT<Boolean>(true);

        return new ResponseT<Boolean>(false);
    }

    public ResponseT<String> getEmpDataTostring(){ //TODO: should we consider terms of employye as well??
        return new ResponseT<String>("Name: %s \nID: %s \nBank Account: %s \nSalary: %s \nDate Of Hire: %s".formatted(
                name, ID, bankAccount, salary, dateOfHire)
        );
        //what kind of error Response should it return??
    }



}
