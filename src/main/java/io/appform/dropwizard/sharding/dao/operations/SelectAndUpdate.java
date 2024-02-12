package io.appform.dropwizard.sharding.dao.operations;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import org.hibernate.Session;

/**
 * Get entity by criteria, mutate it and persist to DB. Using select instead of get so it does not
 * fail in case of more than one result.
 *
 * @param <T> return type of the entity to be updated.
 */
@Data
@SuperBuilder
public class SelectAndUpdate<T> extends OpContext<Boolean> {

  @NonNull
  private SelectParam<T> selectParam;
  @NonNull
  private Function<SelectParam<T>, List<T>> selector;
  @Builder.Default
  private Function<T, T> mutator = t -> t;
  private BiConsumer<T, T> updater;

  @Override
  public Boolean apply(Session session) {
    List<T> entityList = selector.apply(selectParam);
    if (entityList == null || entityList.isEmpty()) {
      return false;
    }
    T oldEntity = entityList.get(0);
    if (null == oldEntity) {
      return false;
    }
    T newEntity = mutator.apply(oldEntity);
    if (null == newEntity) {
      return false;
    }
    updater.accept(oldEntity, newEntity);
    return true;
  }

  @Override
  public OpType getOpType() {
    return OpType.SELECT_AND_UPDATE;
  }

  @Override
  public <R> R visit(OpContextVisitor<R> visitor) {
    return visitor.visit(this);
  }
}
