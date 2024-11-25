package interface_adapter.home;

public class HomeState {

    private String username;
//    private Food[] createdBreakfast;
//    private Food[] createdLunch;
//    private Food[] createdSupper;

    public void setUsername(String username) {
        this.username = username;
    }
//    public void setCreatedBreakfast(Food[] createdBreakfast) {
//        this.createdBreakfast = createdBreakfast;
//    }
//
//    public void setCreatedLunch(Food[] createdLunch) {
//        this.createdLunch = createdLunch;
//    }
//
//    public void setCreatedSupper(Food[] createdSupper) {
//        String[] givenSupper = new String[createdSupper.length];
//        for (int i = 0; i < createdSupper.length; i++) {
//            givenSupper[i] = createdSupper[i].toString();
//        }
//        this.createdSupper = createdSupper;
//    }
//

    public String getUsername() {
        return username;
    }
//    public Food[] getCreatedBreakfast() {
//        return createdBreakfast;
//    }
//
//    public Food[] getCreatedLunch() {
//        return createdLunch;
//    }
//
//    public Food[] getCreatedSupper() {
//        return createdSupper;
//    }
}
