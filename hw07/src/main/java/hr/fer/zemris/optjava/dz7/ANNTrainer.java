package hr.fer.zemris.optjava.dz7;

public class ANNTrainer {

    public static void main(String[] args) {

        String ALGORITHM = args[1];
        int POPULATION_SIZE = Integer.parseInt(args[2]);
        double MAXIMUM_ERROR = Double.parseDouble(args[3]);
        int MAXIMUM_ITERATIONS = Integer.parseInt(args[4]);

        IReadOnlyDataset dataset = new Dataset(args[0]);
        FFANN network = new FFANN(new int[]{4, 5, 3, 3},
                new ITransferFunction[]{
                        new SigmoidTransferFunction(),
                        new SigmoidTransferFunction(),
                        new SigmoidTransferFunction()},
                dataset);


        ANNTrainingAlgorithm algorithm = null;

        if (ALGORITHM.equals("pso-a")) {
            algorithm = new PSOGlobal(-2, 2
                    , MAXIMUM_ERROR, MAXIMUM_ITERATIONS, POPULATION_SIZE
                    , 2, 2);
        } else if (ALGORITHM.startsWith("pso-b")) {
            algorithm = new PSOLocal(-2, 2
                    , MAXIMUM_ERROR, MAXIMUM_ITERATIONS, POPULATION_SIZE
                    , 2, 2, Integer.parseInt(ALGORITHM.split("-")[2]));
        } else if (ALGORITHM.equals("clonalg")) {
            algorithm = new ClonAlg(POPULATION_SIZE, 1.5
                    , MAXIMUM_ERROR, MAXIMUM_ITERATIONS, 0.2);
        } else {
            System.err.println("Wrong algorithm type!");
            System.exit(1);
        }

        algorithm.train(network);
        network.classify();
    }
}
