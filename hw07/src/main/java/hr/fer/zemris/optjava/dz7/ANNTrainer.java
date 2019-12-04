package hr.fer.zemris.optjava.dz7;

public class ANNTrainer {

    public static void main(String[] args) {

        IReadOnlyDataset dataset = new Dataset(args[0]);
        FFANN network = new FFANN(new int[]{4, 5, 3, 3},
                new ITransferFunction[]{
                        new SigmoidTransferFunction(),
                        new SigmoidTransferFunction(),
                        new SigmoidTransferFunction()},
                dataset);

        System.out.println(network.getWeightsCount());

    }
}
