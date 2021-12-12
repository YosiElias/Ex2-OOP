import java.lang.reflect.Type;
import java.util.Map.Entry;

import api.DirectedWeightedGraph;
import api.NodeData;
import com.google.gson.*;



public class GraphJsonDeserializer implements JsonDeserializer<DirectedWeightedGraph> {
    @Override
    public DirectedWeightedGraph deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        DirectedWeightedGraph graph = new DirectedWeightedGraphClass();

        JsonObject graphJsonObj = json.getAsJsonObject();

        JsonArray edgesArry = graphJsonObj.get("Edges").getAsJsonArray();
        JsonArray nodesArry = graphJsonObj.get("Nodes").getAsJsonArray();

        for (int i = 0; i < nodesArry.size(); i++) {
            JsonObject NodesJsonObj = nodesArry.get(i).getAsJsonObject();
            int id = NodesJsonObj.get("id").getAsInt();
            String pos[] = NodesJsonObj.get("pos").getAsString().split(",");
            double x = Double.parseDouble(pos[0]);
            double y = Double.parseDouble(pos[1]);
            double z = Double.parseDouble(pos[2]);
            NodeData n = new NodeDataClass(id, x, y, z);
            graph.addNode(n);
        }


        for (int i = 0; i < edgesArry.size(); i++) {
            JsonObject EdgeJsonObj = edgesArry.get(i).getAsJsonObject();
            int src = EdgeJsonObj.get("src").getAsInt();
            int dest = EdgeJsonObj.get("dest").getAsInt();
            double weight = EdgeJsonObj.get("w").getAsDouble();
            graph.connect(src, dest, weight);
        }

        return graph;
    }

}
