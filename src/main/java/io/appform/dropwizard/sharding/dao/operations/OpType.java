package io.appform.dropwizard.sharding.dao.operations;

public enum OpType {

  // Read operations
  COUNT,
  SELECT,
  GET,
  READ_ONLY,

  // Write operations
  LOCK_AND_EXECUTE,
  CREATE_OR_UPDATE_BY_LOOKUP_KEY,
  GET_AND_UPDATE_BY_LOOKUP_KEY,
  DELETE_BY_LOOKUP_KEY,
  UPDATE_BY_QUERY,
  UPDATE_WITH_SCROLL,
  GET_AND_UPDATE,
  SELECT_AND_UPDATE,
  RUN_IN_SESSION,
  RUN_WITH_CRITERIA,
  SAVE_ALL,
  SAVE,
  CREATE_OR_UPDATE,
  CREATE_OR_UPDATE_IN_LOCKED_CONTEXT
}
