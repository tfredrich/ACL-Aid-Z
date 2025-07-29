package com.strategicgains.aclaid.domain;

import java.util.Collection;

import com.strategicgains.aclaid.exception.InvalidTupleException;

/**
 * A TupleStore is a collection of Tuples. It is the primary data structure used to store and query access control data.
 * 
 * @author Todd Fredrich
 * @see Tuple, LocalTupleStore
 */
public interface TupleStore
{
	boolean isEmpty();

	/**
	 * Check if the provided user has the relation to the object in this tuple set.
	 * 
	 * From the Zanzibar document:
	 * A check request specifies a userset, represented by ⟨object#relation⟩, a
	 * putative user, often represented by an authentication token.
	 * 
	 * @param userset
	 * @param relation
	 * @param objectId
	 * @return
	 */
	boolean check(UserSet userset, String relation, ObjectId objectId);

	/**
	 * Perform a single read operation against the tuple store using the provided tuple set.
	 * This is used to retrieve tuples that match the criteria specified in the tuple set.
	 * 
	 * @param tupleSet A tuple set that defines the criteria for the read operation.
	 * @return a collection of tuples that match the criteria defined in the tuple set.
	 */
	Collection<Tuple> read(TupleSet tupleSet);

	/**
	 * Perform one or more read operations against the tuple store using the provided tuple sets.
	 * This is used to retrieve tuples that match the criteria specified in the tuple sets.
	 * 
	 * @param tupleSets A collection of one or more tuple sets that define the criteria for the read operation(s).
	 * @return a collection of tuples that match the criteria defined in the tuple sets.
	 */
	Collection<Tuple> read(Collection<TupleSet> tupleSets);

	/**
	 * Add a tuple to this tuple set.
	 * 
	 * @param tuple
	 * @return
	 */
	TupleStore write(Tuple tuple)
	throws InvalidTupleException;

	/**
	 * Add a collection of tuples to this tuple set.
	 * 
	 * @param tuples a collection of tuples to be added to the tuple store.
	 * @return
	 */
	TupleStore write(Collection<Tuple> tuples)
	throws InvalidTupleException;

	/**
	 * Create a new tuple using the given object ID, relation and userset then add it to the tuple set.
	 * 
	 * @param userset
	 * @param relation
	 * @param objectId
	 * @return
	 * @throws InvalidTupleException 
	 */
	TupleStore write(UserSet userset, String relation, ObjectId objectId)
	throws InvalidTupleException;

	/**
	 * Remove a tuple from this tuple set.
	 * 
	 * @param tuple
	 * @return
	 */
	TupleStore remove(Tuple tuple);

	/**
	 * Remove a tuple from this tuple set using the given object ID, relation and userset.
	 * 
	 * @param userset
	 * @param relation
	 * @param objectId
	 * @return
	 */
	TupleStore remove(UserSet userset, String relation, ObjectId objectId);
}