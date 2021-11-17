package by.viraz84.parking3.entity;

public class ParkPlace {

    private boolean placeFree = true;
    private Integer placeNumber;

    public ParkPlace(Integer placeNumber) {

        this.placeNumber = placeNumber;
    }

    public boolean isFree(){

        return placeFree;
    }

    public void busy(){

        placeFree = false;
    }

    public void free(){

        placeFree = true;
    }

    public Integer getPlaceNumber(){

        return placeNumber;
    }

}
