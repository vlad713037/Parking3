package by.viraz84.parking3.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class Car extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(Car.class);

    private Integer carNumber;
    private Parking parking;
    ReentrantLock lock = new ReentrantLock();
    Exchanger<ParkPlace> exchanger;
    private static int a = 0;


    public Car(Integer carNumber, Parking parking, Exchanger<ParkPlace> ex/*,CyclicBarrier berrier*/) {
        this.carNumber = carNumber;
        this.parking = parking;
        setName("Car" + carNumber);
        this.exchanger = ex;
        LOGGER.debug("Car{} is created", carNumber);
    }

    @Override
    public void run() {
        LOGGER.debug("Car {} drove up to the parking", getCarNumber());
        try {
            sleep((long)(Math.random()*20));
            if (parking.getSem(70)) {
                ParkPlace parkPlace = parking.getParkPlace();
                LOGGER.info("Car{} took {} place on parking", getCarNumber(), parkPlace.getPlaceNumber());
                if (a < 2) {
                    a++;
                    parkPlace = exchanger.exchange(parkPlace);
                    LOGGER.warn("EXCHANGE: Car {} get parkPlace {}", getCarNumber(), parkPlace.getPlaceNumber());
                }

                ParkPlace parkPlace2 = usingParkPlace(parkPlace);

                LOGGER.info("Car{} left the parking place {}", getCarNumber(), parkPlace.getPlaceNumber());
                parking.giveAwayParkPlace(parkPlace2);

            } else {
                LOGGER.info("------CAR {} timeout. Go out from parking.-----", getCarNumber());
                interrupt();
            }

        } catch (InterruptedException e) {
            LOGGER.error("Car Error {}", e.getMessage());
            interrupt();
            throw new RuntimeException();
        }
    }

    public Integer getCarNumber() {

        return carNumber;
    }

    public ParkPlace usingParkPlace(ParkPlace parkPlace) {
        try {
            lock.lock();
            LOGGER.debug("Car {} using {} parkPlace", getCarNumber(), parkPlace.getPlaceNumber());
            long time = (long) (Math.random() * 80);
            sleep(time);
            lock.unlock();
        } catch (InterruptedException e) {
            interrupt();
            LOGGER.error("Error sleep {}", e.getMessage());
            throw new RuntimeException();

        }
        return parkPlace;
    }

}
