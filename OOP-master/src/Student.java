import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student extends Employee{

    private double baseHourPrice=90;
    private double numOfBaseHours=90;
    private double numOfOvertimeHours=0;
    private double priceExtraHour=0;
    private double regularMonthlySupplement=0;

    public Student(int id, int hours) {
        super(id, hours);

    }
}

