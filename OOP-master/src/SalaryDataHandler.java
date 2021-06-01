import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SalaryDataHandler {

    private static List<String> readFromFile(String file) {

        try {
            return Files.readAllLines(Paths.get(file), StandardCharsets.UTF_8);
        } catch (IOException ee) {
            ee.printStackTrace();
            return Collections.emptyList();
        }
    }

    private static List<HoursPerRank> createHoursForRank(List<String> lines){

        if (lines.size() > 0) {
            List<HoursPerRank> hoursPerRank = new ArrayList<>();
            HoursPerRank currentEmployee;

            for(String line:lines) {
                String[] splitLine = line.split("\\s+");
                currentEmployee = new HoursPerRank(Integer.parseInt(splitLine[0]), splitLine[1], Integer.parseInt(splitLine[2]));
                hoursPerRank.add(currentEmployee);
            }

            return hoursPerRank;
        } else return Collections.emptyList();



    }

    public static List<HoursPerRank> getHoursPerRank (String file){
        return createHoursForRank(readFromFile(file));
    }

}
