package by.viraz84.parking3.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Parking {

    private static final Logger LOGGER = LoggerFactory.getLogger(Parking.class);

    private static int parkSize = 4;
    private Semaphore semaphore;
    private ReentrantLock lock = new ReentrantLock();
    private List<ParkPlace> parkPlaceList;


    public Parking(List<ParkPlace> parkPlaceList) {
        semaphore = new Semaphore(parkSize, true);
        this.parkPlaceList = parkPlaceList;
    }

    public static List<ParkPlace> createParkPlaces() {
        List<ParkPlace> parkPlaceList = new ArrayList<>();
        for (int i = 1; i <= parkSize; i++) {
            parkPlaceList.add(new ParkPlace(i));
            LOGGER.debug("Creating {} parking place", i);
        }
        return parkPlaceList;
    }

    public boolean getSem(long time) {
        boolean sem = false;
        try {
            sem = semaphore.tryAcquire(time, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            LOGGER.error("Something went wrong {}", e.getMessage());
        }
        return sem;
    }

    public ParkPlace getParkPlace() throws InterruptedException {
        lock.lock();
        ParkPlace parkPlace = parkPlaceList.stream().filter(t -> t.isFree()).findFirst().get();
        parkPlace.busy();
        lock.unlock();

        return parkPlace;
    }

    public void giveAwayParkPlace(ParkPlace parkPlace) {
        parkPlace.free();
        semaphore.release();
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }
}


