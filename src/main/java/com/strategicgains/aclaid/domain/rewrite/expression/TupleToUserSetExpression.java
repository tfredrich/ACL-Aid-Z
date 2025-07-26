package com.strategicgains.aclaid.domain.rewrite.expression;

import java.util.Collection;
import java.util.List;

import com.strategicgains.aclaid.domain.ObjectId;
import com.strategicgains.aclaid.domain.Tuple;
import com.strategicgains.aclaid.domain.TupleSet;
import com.strategicgains.aclaid.domain.TupleStore;
import com.strategicgains.aclaid.domain.UserSet;

/**
 * From the Zanzibar document: 
 * 1) Computes a tupleset from the input object,
 * 2) Fetches relation tuples matching the tupleset, and
 * 3) Computes a userset from every fetched relation tuple.
 * 
 * This flexible primitive allows our clients to express
 * complex policies such as “look up the parent folder of
 * the document and inherit its viewers”.
 */
public class TupleToUserSetExpression
implements UsersetLeafExpression
{
	private ObjectId objectId;
	private String relation;
	private ComputedUserSetExpression userSetExpression;

	public TupleToUserSetExpression(ObjectId objectId, String relation, UsersetExpression computedUserSet)
	{
		super();
		this.objectId = objectId;
		this.relation = relation;
		this.userSetExpression = (ComputedUserSetExpression) computedUserSet;
	}

	@Override
	public boolean evaluate(TupleStore tuples, UserSet userset)
    {
		Collection<Tuple> filtered = tuples.read(new TupleSet(relation, objectId));

		if (filtered == null || filtered.isEmpty())
		{
			System.out.println("No tuples found for " + relation + " / " + objectId);
			return false;
		}

		System.out.println("Found " + filtered.size() + " tuples for " + relation + " / " + objectId);

		List<UserSet> computed = userSetExpression.compute(filtered, userset);		
		return userSetExpression.evaluateAll(tuples, computed, userset);
	}
}
