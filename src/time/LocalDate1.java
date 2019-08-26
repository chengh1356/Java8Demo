package time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class LocalDate1 {
    public static void main(String[] args){
        //前后时间设置
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plus(1,ChronoUnit.DAYS);
        LocalDate yestody = today.minusDays(1);
        System.out.println(today);
        System.out.println(tomorrow);
        System.out.println(yestody);
        //时间转换
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parseTime = LocalDateTime.parse("2019-10-11 12:23:11",formatter);
        String formatTime = parseTime.format(formatter);
        System.out.println(LocalDateTime.now());
        System.out.println(parseTime);
        System.out.println(formatTime);
        //时间比较
        Boolean flag = today.isBefore(tomorrow);
        System.out.println(flag);
        Long betweendays = ChronoUnit.DAYS.between(today,tomorrow);
        System.out.println(betweendays);
    }
}
