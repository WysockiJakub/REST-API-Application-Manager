package pl.rest.application;

import pl.rest.application.entity.Status;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Test {

    public static void main(String[] args) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(("yyyy-MM-dd HH:mm:ss.SSS"));
//
//        LocalDateTime date = LocalDateTime.now();
//        date.getNano();
//        System.out.println(LocalDateTime.parse("2021-02-01 12:12:12", formatter));

//        ZonedDateTime dateTime = LocalDateTime.now().atZone(ZoneId.of("Europe/Paris"));
//        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));

        Status status = null;
        String name = "asad";

        if (status == null && name == null) {
            System.out.println("3");
        } else if (status != null && name == null) {
            System.out.println("3");
        } else {
            System.out.println("3");
        }

    }

    public static LocalDateTime now2() {
        return LocalDateTime.now();
    }
}
