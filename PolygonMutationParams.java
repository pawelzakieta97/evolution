public class PolygonMutationParams implements MutationParameters {
    double amount;
    double addPolyChance;
    double deletePolyChance;
    double colorChange;
    double vertexShift;
    double polygonSize;
    int ROIx = 0;
    int ROIy = 0;
    int width;
    int height;

    public PolygonMutationParams(double amount, double addPolyChance, double deletePolyChance, double polygonSize, double colorChange, double addVertexChance, double deleteVertexChance, double vertexShift) {
        this.amount = amount;
        this.addPolyChance = addPolyChance;
        this.deletePolyChance = deletePolyChance;
        this.colorChange = colorChange;
        this.vertexShift = vertexShift;
        this.addVertexChance = addVertexChance;
        this.deleteVertexChance = deleteVertexChance;
        this.polygonSize = polygonSize;
    }

    double addVertexChance = 0;
    double deleteVertexChance = 0;
    public double getAmount(){
        return amount;
    }
    public Object clone(){
        PolygonMutationParams o = new PolygonMutationParams(amount, addPolyChance, deletePolyChance, polygonSize, colorChange, addVertexChance, deleteVertexChance, vertexShift);
        o.ROIx = ROIx;
        o.ROIy = ROIy;
        o.width = width;
        o.height = height;
        return o;
    }
}
