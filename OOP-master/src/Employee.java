import lombok.*;
@RequiredArgsConstructor
@ToString
@Setter
@Getter
public class Employee{
    @NonNull
    private int id;
    @NonNull
    private double hours;


     private double baseHourPrice=250;
     private double numOfBaseHours=180;
     private double numOfOvertimeHours=30;
     private  double priceExtraHour=90;
     private double regularMonthlySupplement=400;


}
