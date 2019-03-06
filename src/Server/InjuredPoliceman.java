package Server;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

public class InjuredPoliceman extends Policeman {

    public String name;
    public int yearOfBirth;
    public String location;
    Date date = new Date();
    public String dateOfCreation = date.toString();
    public String colour;
    public double x = 0;
    public double y = 0;
    public float size = 75;
    OffsetDateTime created = ZonedDateTime.now().toOffsetDateTime();
    OffsetDateTime delited = ZonedDateTime.now().toOffsetDateTime();

    public InjuredPoliceman() {
        this.id = this.hashCode();
    }

    public FacePartEnum injuredFacePart;

//    public void writeFaceParts(int number){
//        System.out.println("У " + getClass().getName() + (number+1) + " пластырь на " + injuredFacePart);
//    }

//    public void writeBodyParts(int number){
//        for (int i = 0; i<bodyPartsEnum.size(); i++) {
//            System.out.println("У " + getClass().getName() + (number+1) + " ранена " + bodyPartsEnum.get(i));
//        }
//    }
//
//    public int getId (){
//        return id;
//    }

    @Override
    public String toString(){
        return this.name;
    }
}