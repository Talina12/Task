import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Manager extends Employee{
    private double baseHourPrice=400;
    private double numOfBaseHours=200;
    private double numOfOvertimeHours=40;
    private double priceExtraHour=100;
    private double regularMonthlySupplement=500;

    public Manager(int id, int hours) {
        super(id, hours);
    }
}
