
package com.hatchtact.pinwi.fragment.insights;

import java.util.Comparator;

import com.hatchtact.pinwi.classmodel.GetDelightTraitsByChildIDOnInsight;

/**
 * The Class SortDataTableByDistanceFormatter.
 */
public class SortDataTableByRating implements
		Comparator<GetDelightTraitsByChildIDOnInsight> {

	
	@Override
	public int compare(GetDelightTraitsByChildIDOnInsight object1,
			GetDelightTraitsByChildIDOnInsight object2) {

		if (object1.getRating() == object2.getRating())
			return 0;
		else if ((object1.getRating()) > object2.getRating())
			return 1;
		else
			return -1;
	}

}
