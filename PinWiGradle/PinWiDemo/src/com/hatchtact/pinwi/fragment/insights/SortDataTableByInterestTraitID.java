
package com.hatchtact.pinwi.fragment.insights;

import java.util.Comparator;

import com.hatchtact.pinwi.classmodel.GetInterestTraitsByChildIDOnInsight;

/**
 * The Class SortDataTableByDistanceFormatter.
 */
public class SortDataTableByInterestTraitID implements
		Comparator<GetInterestTraitsByChildIDOnInsight> {

	
	@Override
	public int compare(GetInterestTraitsByChildIDOnInsight object1,
			GetInterestTraitsByChildIDOnInsight object2) {

		if (object1.getInterestTraitID() == object2.getInterestTraitID())
			return 0;
		else if ((object1.getInterestTraitID()) > object2.getInterestTraitID())
			return 1;
		else
			return -1;
	}

}
