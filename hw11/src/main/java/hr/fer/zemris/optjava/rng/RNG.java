package hr.fer.zemris.optjava.rng;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Properties;

public class RNG {
    private static IRNGProvider rngProvider;
    static {
        Properties properties = new Properties();

        try {
            properties.load(Objects.requireNonNull(RNG.class.getClassLoader().getResourceAsStream("rng-config.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            rngProvider = (IRNGProvider) RNG.class.getClassLoader()
                    .loadClass(properties.getProperty("rng-provider"))
                    .getDeclaredConstructor()
                    .newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    public static IRNG getRNG() {
        return rngProvider.getRNG();
    }
}
