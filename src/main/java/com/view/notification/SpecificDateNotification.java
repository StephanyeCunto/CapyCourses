package com.view.notification;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Timer;


public class SpecificDateNotification {
    public static void main(String[] args) {
        Timer timer = new Timer();

        // Agendar uma notificação para daqui a 5 segundos
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                CapyNotification.showSimple("Notificação única após 5 segundos.");
            }
        }, 5000);

        // Agendar notificações repetitivas a cada 10 segundos
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                CapyNotification.showWithTitleAndImage("Lembrete", "Essa é uma notificação recorrente.");
            }
        }, 10000, 10000);
    }

    /* 
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    // Data e hora específicas
    LocalDateTime targetDateTime = LocalDateTime.of(2024, 11, 24, 19, 14); // 25 de novembro às 14:30
    long delay = calculateDelayInSeconds(targetDateTime);

    if(delay>0)
    {
        scheduler.schedule(() -> {
            CapyNotification.showWithTitle("Hora de Estudar!", "Chegou a hora programada para seu curso.");
        }, delay, TimeUnit.SECONDS);
    }else
    {
        System.out.println("A data/hora já passou.");
    }

    private static long calculateDelayInSeconds(LocalDateTime targetDateTime) {
        LocalDateTime now = LocalDateTime.now();
        return java.time.Duration.between(now, targetDateTime).getSeconds();
    }
        */
}
