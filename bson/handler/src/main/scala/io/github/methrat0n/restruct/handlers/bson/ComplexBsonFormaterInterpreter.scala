package io.github.methrat0n.restruct.handlers.bson

import io.github.methrat0n.restruct.core.data.constraints.Constraint
import io.github.methrat0n.restruct.core.data.schema.ComplexSchemaAlgebra
import io.github.methrat0n.restruct.readers.bson.ComplexBsonReaderInterpreter
import io.github.methrat0n.restruct.writers.bson.ComplexBsonWriterInterpreter

class ComplexBsonFormaterInterpreter extends ComplexSchemaAlgebra[BsonHandler]
  with SimpleBsonFormaterInterpreter with SemiGroupalBsonFormaterInterpreter
  with InvariantBsonFormaterInterpreter with IdentityBsonFormaterInterpreter {

  private[this] object Reader extends ComplexBsonReaderInterpreter
  private[this] object Writer extends ComplexBsonWriterInterpreter

  override def many[T](name: String, schema: BsonHandler[T], default: Option[List[T]]): BsonHandler[List[T]] =
    BsonHandler(
      Reader.many(name, schema, default).read,
      Writer.many(name, schema, default).write
    )

  override def optional[T](name: String, schema: BsonHandler[T], default: Option[Option[T]]): BsonHandler[Option[T]] =
    BsonHandler(
      Reader.optional[T](name, schema, default).read,
      Writer.optional[T](name, schema, default).write
    )

  override def required[T](name: String, schema: BsonHandler[T], default: Option[T]): BsonHandler[T] =
    BsonHandler(
      Reader.required[T](name, schema, default).read,
      Writer.required[T](name, schema, default).write
    )

  override def verifying[T](schema: BsonHandler[T], constraint: Constraint[T]): BsonHandler[T] =
    BsonHandler(
      Reader.verifying[T](schema, constraint).read,
      Writer.verifying[T](schema, constraint).write
    )

}