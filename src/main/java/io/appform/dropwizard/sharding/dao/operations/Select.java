package io.appform.dropwizard.sharding.dao.operations;

import io.appform.dropwizard.sharding.query.QuerySpec;
import java.util.List;
import java.util.function.Function;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;

/**
 * Queries a list of entities with given selectParam.
 * Optional afterSelect function to mutate the selected entities before returning.
 *
 * @param <T> Type of entity to be queried.
 * @param <R> Return type of the operation after performing any afterSave method.
 */
@Data
@SuperBuilder
public class Select<T, R> extends OpContext<R> {

  @NonNull
  private SelectParam<T> selectParam;

  @Builder.Default
  private Function<List<T>, R> afterSelect = t -> (R) t;

  @NonNull
  private Function<SelectParam, List<T>> getter;

  @Override
  public R apply(Session session) {
    return afterSelect.apply(getter.apply(selectParam));
  }

  @Override
  public OpType getOpType() {
    return OpType.SELECT;
  }

  @Override
  public <R1> R1 visit(OpContextVisitor<R1> visitor) {
    return visitor.visit(this);
  }
}
