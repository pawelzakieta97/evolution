public class main {
    public static void main(String[] args){
        ControllerClass con = new ControllerClass();
        EvolvableClass origin = new EvolvableClass();
        origin.x = 100;
        origin.y = 100;
        origin.z = 100;
        EvolutionController controller = new EvolutionController(origin);
        controller.setOwner(con);
        try {
            controller.start(1000,1,3);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        float i = 0;
        while(i<1000000) i+= 0.1;
        con.running = false;

    }
}
