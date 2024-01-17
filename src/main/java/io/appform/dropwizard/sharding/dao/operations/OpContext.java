package io.appform.dropwizard.sharding.dao.operations;

import io.appform.dropwizard.sharding.dao.operations.lockedcontext.LockAndExecute;
import io.appform.dropwizard.sharding.dao.operations.lookupdao.CreateOrUpdateByLookupKey;
import io.appform.dropwizard.sharding.dao.operations.lookupdao.DeleteByLookupKey;
import io.appform.dropwizard.sharding.dao.operations.lookupdao.GetAndUpdateByLookupKey;
import io.appform.dropwizard.sharding.dao.operations.lookupdao.GetByLookupKey;
import io.appform.dropwizard.sharding.dao.operations.readonlycontext.ReadOnly;
import io.appform.dropwizard.sharding.dao.operations.relationaldao.CreateOrUpdateInLockedContext;
import java.util.function.Function;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import org.hibernate.Session;

/**
 * Operation to be executed as a transaction on a single shard.
 *
 * @param <T> return type of the operation.
 */
@Data
@SuperBuilder
public abstract class OpContext<T> implements Function<Session, T> {

  @NonNull
  public abstract OpType getOpType();


  public abstract <P> P visit(OpContextVisitor<P> visitor);

  public interface OpContextVisitor<P> {

    P visit(Count opContext);

    <T, R> P visit(Get<T, R> opContext);

    <T> P visit(GetAndUpdate<T> opContext);

    <T, R> P visit(GetByLookupKey<T, R> opContext);

    <T> P visit(GetAndUpdateByLookupKey<T> opContext);

    <T> P visit(ReadOnly<T> opContext);

    <T> P visit(LockAndExecute<T> opContext);

    <T> P visit(UpdateByQuery opContext);

    <T> P visit(UpdateWithScroll<T> opContext);

    <T> P visit(SelectAndUpdate<T> opContext);

    <T> P visit(RunInSession<T> opContext);

    <T> P visit(RunWithCriteria<T> opContext);

    P visit(DeleteByLookupKey opContext);

    <U, V> P visit(Save<U, V> opContext);

    <T> P visit(SaveAll<T> opContext);

    <T> P visit(CreateOrUpdateByLookupKey<T> opContext);

    <T> P visit(
        io.appform.dropwizard.sharding.dao.operations.relationaldao.CreateOrUpdate<T> opContext);

    <T, U> P visit(CreateOrUpdateInLockedContext<T, U> opContext);

    <T, R> P visit(Select<T, R> opContext);

  }

}
