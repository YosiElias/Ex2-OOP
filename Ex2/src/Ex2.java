import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;

/**
 * This class is the main class for Ex2 - your implementation will be tested using this class.
 */

public class Ex2 {
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGrapg(String json_file) {
        DWGalgo algo = new DWGalgo();
        if (!algo.load(json_file)) {
            System.err.println("Eror: No json_file was loaded");    //only for self testing
            return null;
        }
        DirectedWeightedGraph ans = algo.getGraph();
        return ans;
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DWGalgo algo = new DWGalgo();
        if (!algo.load(json_file)) {
            System.err.println("Eror: No json_file was loaded");     //only for self testing
            return null;
        }
        DirectedWeightedGraphAlgorithms ans = algo;
        return ans;
    }
    /**
     * This static function will run your GUI using the json fime.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     *
     */
    public static void runGUI(String json_file) {
        DirectedWeightedGraphAlgorithms alg = getGrapgAlgo(json_file);
        GuiGraph show = new GuiGraph();
        show.plotGraph(alg);

    }

    public static void main(String[] args) {
//        runGUI("data/G1.json");      //only for self testing
        runGUI(args[0]);
    }
}