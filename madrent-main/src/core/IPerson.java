package core;

public interface IPerson {
    //void vypisKtoSom();
    //void vypisPlatbu();
    default String getFullName() {
        return "Not defined.";
    }

    Name getName();
}
