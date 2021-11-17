import by.viraz84.parking3.entity.ParkPlace;
import by.viraz84.parking3.entity.Parking;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;


public class TestCar {

    @Test
    public void testCreateParkPlacesSize() {
       List<ParkPlace> listPark2 = Parking.createParkPlaces();
       Assert.assertEquals(4, listPark2.size());
    }

    @Test
    public void testCreateParkPlacesNum() {
        List<ParkPlace> listPark2 = Parking.createParkPlaces();
        Assertions.assertThat(listPark2.get(1).getPlaceNumber()).isEqualTo(2);
    }

    @Test
    public void testCreateParkPlacesFree() {
        List<ParkPlace> listPark2 = Parking.createParkPlaces();
        Assertions.assertThat(listPark2.get(1).isFree()).isEqualTo(true);
    }

    @Test
    public void testSemaphore() {
        List<ParkPlace> listPark = new ArrayList<>();

        ParkPlace parkPlace1 = new ParkPlace(1);
        ParkPlace parkPlace2 = new ParkPlace(2);
        ParkPlace parkPlace3 = new ParkPlace(3);
        ParkPlace parkPlace4 = new ParkPlace(4);

        listPark.add(parkPlace1);
        listPark.add(parkPlace2);
        listPark.add(parkPlace3);
        listPark.add(parkPlace4);

        Parking parking = new Parking(listPark);
        boolean sem = parking.getSem(100);

        Assertions.assertThat(sem).isEqualTo(true);
        Assertions.assertThat(parking.getSemaphore().availablePermits()).isEqualTo(3);
    }

    @Test
    public void testGetParkPlace() throws InterruptedException {
        List<ParkPlace> listPark = new ArrayList<>();

        ParkPlace parkPlace1 = new ParkPlace(1);
        ParkPlace parkPlace2 = new ParkPlace(2);
        ParkPlace parkPlace3 = new ParkPlace(3);
        ParkPlace parkPlace4 = new ParkPlace(4);

        listPark.add(parkPlace1);
        listPark.add(parkPlace2);
        listPark.add(parkPlace3);
        listPark.add(parkPlace4);

        Parking parking = new Parking(listPark);
        ParkPlace testParkPlace= parking.getParkPlace();

        Assertions.assertThat(testParkPlace.getPlaceNumber()).isEqualTo(1);
    }

    @Test
    public void testGetParkPlaceFree() throws InterruptedException {
        List<ParkPlace> listPark = new ArrayList<>();

        ParkPlace parkPlace1 = new ParkPlace(1);
        ParkPlace parkPlace2 = new ParkPlace(2);
        ParkPlace parkPlace3 = new ParkPlace(3);
        ParkPlace parkPlace4 = new ParkPlace(4);

        listPark.add(parkPlace1);
        listPark.add(parkPlace2);
        listPark.add(parkPlace3);
        listPark.add(parkPlace4);

        Parking parking = new Parking(listPark);
        ParkPlace testParkPlace= parking.getParkPlace();
        Assertions.assertThat(testParkPlace.isFree()).isEqualTo(false);
    }

    @Test
    public void testGiveAwayParkPlace() throws InterruptedException {
        List<ParkPlace> listPark = new ArrayList<>();

        ParkPlace parkPlace1 = new ParkPlace(1);
        ParkPlace parkPlace2 = new ParkPlace(2);
        ParkPlace parkPlace3 = new ParkPlace(3);
        ParkPlace parkPlace4 = new ParkPlace(4);

        listPark.add(parkPlace1);
        listPark.add(parkPlace2);
        listPark.add(parkPlace3);
        listPark.add(parkPlace4);

        Parking parking = new Parking(listPark);

        ParkPlace parkPlaceTest = listPark.stream().findFirst().get();
        parkPlaceTest.busy();
        parking.giveAwayParkPlace(parkPlaceTest);

        Assertions.assertThat(parkPlaceTest.isFree()).isEqualTo(true);
    }

    @Test
    public void testGiveAwayParkPlaceSemaphore() throws InterruptedException {
        List<ParkPlace> listPark = new ArrayList<>();

        ParkPlace parkPlace1 = new ParkPlace(1);
        ParkPlace parkPlace2 = new ParkPlace(2);
        ParkPlace parkPlace3 = new ParkPlace(3);
        ParkPlace parkPlace4 = new ParkPlace(4);

        listPark.add(parkPlace1);
        listPark.add(parkPlace2);
        listPark.add(parkPlace3);
        listPark.add(parkPlace4);

        Parking parking = new Parking(listPark);
        parking.getSemaphore().acquire();
        ParkPlace parkPlaceTest = listPark.stream().findFirst().get();
        parking.giveAwayParkPlace(parkPlaceTest);

        Assertions.assertThat(parking.getSemaphore().availablePermits()).isEqualTo(4);
        System.out.println();
    }



}
