package kon.blats;

import java.time.*;

import static java.lang.Math.abs;

public class App {
    public static void main(String[] args) {

        String desiredCutOffTime = "05:00";
        int desiredHour = Integer.parseInt(desiredCutOffTime.split(":")[0]);
        int desiredMinutes = Integer.parseInt(desiredCutOffTime.split(":")[1]);

        ZonedDateTime utcZonedDateTime = ZonedDateTime.now(ZoneOffset.UTC);
        ZonedDateTime bspTimeNow = utcZonedDateTime.withZoneSameInstant(ZoneId.of("+8"));

        int hourNowInBsp = bspTimeNow.getHour();
        int minuteNowInBsp = bspTimeNow.getMinute();

        boolean isPositiveHoursDiff = desiredHour - hourNowInBsp > 0;
        LocalTime bspTime = LocalTime.of(hourNowInBsp, minuteNowInBsp);
        LocalTime desiredTime = LocalTime.of(desiredHour, desiredMinutes);

        long secondsToCutOff;
        if (isPositiveHoursDiff) {
            secondsToCutOff = Duration.between(bspTime, desiredTime).getSeconds();
        } else {
            LocalTime midnightTime = LocalTime.of(23, 59, 59, 99);
            long midnightDiff = abs(Duration.between(bspTime, midnightTime).getSeconds());
            long absoluteDiff = abs(Duration.between(LocalTime.of(00, 00, 00, 00), desiredTime).getSeconds());
            secondsToCutOff = absoluteDiff + midnightDiff;
        }
        System.out.println(secondsToCutOff);

    }
}
