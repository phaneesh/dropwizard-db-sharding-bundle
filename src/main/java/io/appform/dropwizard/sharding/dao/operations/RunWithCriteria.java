package io.appform.dropwizard.sharding.dao.operations;

import java.util.function.Function;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;

/**
 * Run a query with given crietria inside this shard and returns resulting list.
 *
 * @param <T> Return type on performing the operation.
 */
@Data
@SuperBuilder
public class RunWithCriteria<T> extends OpContext<T> {

  @NonNull
  private Function<DetachedCriteria, T> handler;
  @NonNull
  private DetachedCriteria detachedCriteria;

  @Override
  public T apply(Session session) {
    return handler.apply(detachedCriteria);
  }

  @Override
  public OpType getOpType() {
    return OpType.RUN_WITH_CRITERIA;
  }

  @Override
  public <R> R visit(OpContextVisitor<R> visitor) {
    return visitor.visit(this);
  }
}
