/*
 * Copyright (C) 2013 The Mango Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * The code of this project is a port of (or wrapper around) the Guava-libraries.
 *    See http://code.google.com/p/guava-libraries/
 * 
 * @author Markus Schneider
 */
package org.feijoas.mango.common.collect

import scala.collection.mutable.Builder
import org.feijoas.mango.common.annotations.Beta
import org.feijoas.mango.common.base.Preconditions._

/** Factory for RangeSet implementations
 *
 *  @author Markus Schneider
 *  @since 0.8
 */
@Beta
trait RangeSetFactory[Repr[C, O <: Ordering[C]] <: RangeSet[C, O] with RangeSetLike[C, O, Repr[C, O]]] {

  /** Returns a [[RangeSet]] containing the single range `Range#all`
   */
  def all[C, O <: Ordering[C]](implicit ord: O): Repr[C, O] = apply(Range.all[C, O])

  /** Returns an empty [[RangeSet]].
   */
  def empty[C, O <: Ordering[C]](implicit ord: O): Repr[C, O] = newBuilder[C, O](ord).result

  /** Returns a [[RangeSet]] that contains the provided ranges
   */
  def apply[C, O <: Ordering[C]](ranges: Range[C, O]*)(implicit ord: O): Repr[C, O] = {
    val builder = newBuilder[C, O](ord)
    ranges.foreach { builder += checkNotNull(_) }
    builder.result
  }

  /** Returns a [[RangeSet]] initialized with the ranges in the specified range set.
   */
  def apply[C, O <: Ordering[C]](rangeSet: RangeSet[C, O])(implicit ord: O): Repr[C, O] = {
    val builder = newBuilder[C, O](ord)
    rangeSet.asRanges.foreach { builder += checkNotNull(_) }
    builder.result
  }

  /** Returns a new builder for a range set.
   */
  def newBuilder[C, O <: Ordering[C]](implicit ord: O): Builder[Range[C, O], Repr[C, O]]
}