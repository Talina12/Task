import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainClass {

    public static void main(String[] args) throws IOException {

         SalaryCalculationHandler.printSalary(SalaryDataHandler.getHoursPerRank("Employees"));

    }

}
