package BuisnessLayer.EmployeesBuisnessLayer;
import DataAccessLayer.EmployeesDataAccessLayer.DAOs.ShiftDAO;
import DataAccessLayer.EmployeesDataAccessLayer.DTOs.ShiftDTO;

import java.time.LocalDate;

import java.time.LocalTime;

public class MorningShift extends Shift{

    public MorningShift(LocalDate _date, ShiftDAO dao) {
        super(_date, dao);
    }

    public MorningShift(ShiftDTO dto) {
        super(dto.getDate(), dto.getDao());
    }

    @Override
    public MorningShift clone() {
        return new MorningShift(date, new ShiftDAO());
    }

    @Override
    public void setStart() {
        start = LocalTime.of(6,0);
    }

    @Override
    public void setEnd() {
        end = LocalTime.of(14,0);
    }
}
