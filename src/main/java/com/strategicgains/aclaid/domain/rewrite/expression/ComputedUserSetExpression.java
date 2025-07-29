package com.strategicgains.aclaid.domain.rewrite.expression;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.strategicgains.aclaid.domain.ObjectId;
import com.strategicgains.aclaid.domain.Tuple;
import com.strategicgains.aclaid.domain.TupleStore;
import com.strategicgains.aclaid.domain.UserSet;
import com.strategicgains.aclaid.domain.rewrite.RewriteRule;

/**
 * Computes, for the input object, a new userset. For example, this allows the userset expression
 * for a viewer relation to refer to the editor userset on the same object, thus offering an ACL
 * inheritance capability between relations.
 *
 * @author Todd Fredrich
 * @see RewriteRule
 */
public class ComputedUserSetExpression
implements UsersetExpression
{
	private ObjectId objectId;
	private String relation;
	private String objectToken;

	public ComputedUserSetExpression(ObjectId objectId, String relation, String objectToken)
	{
		super();
		setObjectId(objectId);
		setRelation(relation);
		setObjectToken(objectToken);
	}

	public ObjectId getObjectId()
	{
		return objectId;
	}

	protected void setObjectId(ObjectId objectId)
	{
		this.objectId = objectId;
	}

	protected String getObjectToken()
	{
		return objectToken;
	}

	public boolean hasObjectToken()
	{
		return (objectToken != null);
	}

	protected void setObjectToken(String objectToken)
	{
		this.objectToken = objectToken;
	}

	public boolean hasRelation()
	{
		return (relation != null);
	}

	protected void setRelation(String relation)
	{
		this.relation = relation;
	}

	@Override
	public boolean evaluate(TupleStore tuples, UserSet userset)
	{
		return false;
	}

	private UserSet compute(Tuple tuple, ObjectId objectId, String relation)
	{
		UserSet userset = new UserSet(objectId, relation);
		
		if (hasObjectToken())
		{
			switch(getObjectToken())
			{
				case Tuple.USERSET_OBJECT:
					userset.setObjectId(tuple.getUsersetObjectId());
					break;
				case Tuple.USERSET_RELATION:
					System.out.println(Tuple.USERSET_RELATION + " of " + userset);
					userset.setRelation(tuple.getUsersetRelation());
					break;
				case Tuple.RELATION:
					System.out.println(Tuple.RELATION + " of " + userset);
					userset.setRelation(tuple.getRelation());
					break;
				default:
					System.out.println(getObjectToken() + " of " + userset);
			}
		}

		return userset;
	}

	public List<UserSet> compute(Collection<Tuple> filtered, UserSet userset)
	{
		if (filtered == null || filtered.isEmpty())
		{
			return Collections.emptyList();
		}

		return filtered.stream()
			.map(tuple -> compute(tuple, userset.getObjectId(), userset.getRelation()))
			.toList();
	}

	public boolean evaluateAll(TupleStore tuples, List<UserSet> computed, UserSet userset)
	{
		if (computed == null || computed.isEmpty())
		{
			return false;
		}

		for (UserSet c : computed)
		{
			System.out.println("Checking " + userset + " for " + relation + " on " + c.getObjectId());
			if (tuples.check(userset, relation, c.getObjectId()) 
)			{
				return true;
			}
		}

		return false; 
	}
}
