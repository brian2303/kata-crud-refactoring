package co.com.sofka.crud.converter;

public interface Converter<E,D> {

    E fromDTO(D dto);

    D fromEntity(E entity);
}
