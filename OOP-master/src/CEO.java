import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CEO extends Employee{
    private double baseHourPrice=500;
    private double numOfBaseHours=250;
    private double numOfOvertimeHours=50;
    private double priceExtraHour=120;
    private double regularMonthlySupplement=600;

    public CEO(int id, int hours) {
        super(id, hours);
    }
}
