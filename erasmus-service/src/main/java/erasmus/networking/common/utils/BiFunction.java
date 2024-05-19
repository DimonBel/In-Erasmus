package erasmus.networking.common.utils;

@FunctionalInterface
public interface BiFunction<T, U, R> {
  R apply(T t, U u);
}
