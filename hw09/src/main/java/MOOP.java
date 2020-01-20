public class MOOP {

    public static void main(String[] args) {

        String PROBLEM = args[0];
        int POPULATION_SIZE = Integer.parseInt(args[1]);
        String SHARE_SPACE = args[2];
        int MAXIMUM_ITERATIONS = Integer.parseInt(args[3]);

        if (PROBLEM.equals("1")) NSGA.solve(new Problem1(), MAXIMUM_ITERATIONS, POPULATION_SIZE, SHARE_SPACE);
        else NSGA.solve(new Problem2(), MAXIMUM_ITERATIONS, POPULATION_SIZE, SHARE_SPACE);
    }

    public static boolean isCharacter(String input) {
        if (input.length() == 3) return true;
        if (input.charAt(1) == '\\') {
            char escapedChar = input.charAt(2);
            return escapedChar == 'n' || escapedChar == 't' || escapedChar == '0' || escapedChar == '\''
                    || escapedChar == '\"' || escapedChar == '\\';
        }
        return false;
    }
}
