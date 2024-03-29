package com.cjrequena.sample.mapper;

import org.mapstruct.MappingTarget;

import java.util.List;

/**
 *
 * <p></p>
 * <p></p>
 * @author cjrequena
 */
public interface DTOEntityMapper<D, E> {

  /**
   * To dto.
   *
   * @param entity the entity
   * @return the dto
   */
  D toDTO(E entity);

  /**
   *
   * @param entity
   * @param dto
   */
  void toDTO(E entity, @MappingTarget D dto);

  /**
   * To entity.
   *
   * @param dto the dto
   * @return the e
   */
  E toEntity(D dto);

  /**
   *
   * @param dto
   * @param entity
   */
  void toEntity(D dto, @MappingTarget E entity);

  /**
   * To dtos.
   *
   * @param entities the entities
   * @return the list
   */
  List<D> toDTOs(List<E> entities);

  /**
   * To entities.
   *
   * @param dtos the dtos
   * @return the list
   */
  List<E> toEntities(List<D> dtos);
}
