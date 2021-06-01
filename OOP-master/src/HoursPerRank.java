import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class HoursPerRank {

    private int id;
    private String rank;
    private int hours;
}
