package be.riddler.v1.common.domain.feature;

/**
 * DomainFeature
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
public interface DomainFeature<T, R> {
    default void execute(T t) {
    }

    default R executeWithReturn(T t) {
        return null;
    }


    default R executeWithoutParameters() {
        return null;
    }
}
