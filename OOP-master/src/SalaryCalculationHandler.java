import java.util.List;

public class SalaryCalculationHandler {

    public static Employee createEmployee(HoursPerRank h){
        switch (h.getRank()) {
                    case ("סטודנט"):
                        return new Student(h.getId(), h.getHours());
                    case ("מנכל"):
                        return new CEO(h.getId(), h.getHours());
                    case ("מנהל"):
                        return new Manager(h.getId(), h.getHours());
                    default:
                        return new Employee(h.getId(), h.getHours());
                }
            }

    private static double calculateSalary(Employee emp){
        double sum=0;
        if(emp.getNumOfBaseHours()<emp.getHours()){
            sum+=calculateSalary(emp.getNumOfBaseHours(),emp.getBaseHourPrice());
            if(emp.getNumOfOvertimeHours()<(emp.getHours()-emp.getNumOfBaseHours())){
                sum+=calculateSalary(emp.getNumOfOvertimeHours(),emp.getPriceExtraHour());
            }else sum+=calculateSalary(emp.getHours()-emp.getNumOfBaseHours(),emp.getPriceExtraHour());
        } else return calculateSalary(emp.getHours(),emp.getBaseHourPrice());
        return sum+ emp.getRegularMonthlySupplement();

    }

    private static double calculateSalary(double numOfBaseHours, double baseHourPrice){
        return numOfBaseHours*baseHourPrice;
    }

    public static void printSalary (List<HoursPerRank> hours){
        if(hours.size()>0) {

            for (HoursPerRank h : hours) {
                Employee employee = createEmployee(h);
                System.out.println("Employee num: " + employee.getId() + " Salary " + calculateSalary(employee));
            }

        }else  System.out.println("Check your file. Something went wrong");


    }
}
