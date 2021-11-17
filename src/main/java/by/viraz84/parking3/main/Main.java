package by.viraz84.parking3.main;

import by.viraz84.parking3.entity.Car;
import by.viraz84.parking3.entity.ParkPlace;
import by.viraz84.parking3.entity.Parking;

import java.util.List;
import java.util.concurrent.Exchanger;
import java.util.stream.IntStream;

public class Main {


    public static void main(String[] args) {
        List<ParkPlace> listPark = Parking.createParkPlaces();
        Parking parking = new Parking(listPark);
        Exchanger<ParkPlace> exchanger = new Exchanger<ParkPlace>();

        IntStream.rangeClosed(1, 15).forEach(i -> new Car(i, parking, exchanger).start());


    }
}
