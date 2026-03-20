package com.abinbev.b2b.promotion.consumer.helper;

import com.mongodb.MongoBulkWriteException;
import com.mongodb.bulk.BulkWriteError;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.bson.BsonDocument;
import org.springframework.data.mongodb.BulkOperationException;

public final class BulkOperationExceptionHelper {

  private static final String DUPLICATE_KEY_ERROR = "E11000 duplicate key error collection";

  public static BulkOperationException getBulkOperationException() {
    BulkWriteError bulkWriteError =
        new BulkWriteError(11000, DUPLICATE_KEY_ERROR, new BsonDocument(), 0);
    List<BulkWriteError> bulkWriteErrorList = new ArrayList<>();
    bulkWriteErrorList.add(bulkWriteError);
    MongoBulkWriteException mongoBulkWriteException =
        new MongoBulkWriteException(
            null, bulkWriteErrorList, null, null, Set.of(DUPLICATE_KEY_ERROR));
    BulkOperationException bulkOperationException =
        new BulkOperationException(DUPLICATE_KEY_ERROR, mongoBulkWriteException);
    return bulkOperationException;
  }
}
