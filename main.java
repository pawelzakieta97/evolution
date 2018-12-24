public class main {
    public static void main(String[] args){
        EvolvableClass origin = new EvolvableClass();
        origin.x = 100;
        origin.y = 100;
        origin.z = 100;
        EvolutionController controller = new EvolutionController(origin);
        for (int i = 0; i<200; i++){
            controller.update(10, 2, 3);
        }
    }
}
