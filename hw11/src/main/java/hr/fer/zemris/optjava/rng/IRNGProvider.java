package hr.fer.zemris.optjava.rng;

/**
 * Sučelje koje predstavlja objekte koji sadrže generator slučajnih
 * brojeva i koji ga stavljaju drugima na raspolaganje uporabom
 * metode {@link #getRNG()}. Objekti koji implementiraju ovo sučelje
 * ne smiju na svaki poziv metode {@link #getRNG()} stvarati i vraćati
 * novi generator već moraju imati ili svoj vlastiti generator koji vraćaju,
 * ili pristup do kolekcije postojećih generatora iz koje dohvaćaju i vraćaju
 * jedan takav generator (u skladu s pravilima konkretne implementacije ovog
 * sučelja) ili isti stvaraju na zahtjev i potom čuvaju u cache-u za istog
 * pozivatelja.
 *
 * @author marcupic
 */

public interface IRNGProvider {
    /**
     * Metoda za dohvat generatora slučajnih brojeva koji pripada
     * ovom objektu.
     *
     * @return generator slučajnih brojeva
     */
    public IRNG getRNG();
}