
package com.hatchtact.pinwi.fragment.insights;

import java.util.Comparator;

import com.hatchtact.pinwi.classmodel.GetInterestPatternByChildIDOnInsight;

/**
 * The Class SortDataTableByDistanceFormatter.
 */
public class SortDataTableByInterestPatternID implements
Comparator<GetInterestPatternByChildIDOnInsight> {


	@Override
	public int compare(GetInterestPatternByChildIDOnInsight object1,
			GetInterestPatternByChildIDOnInsight object2) {
		int idobj1=Integer.parseInt(object1.getInterestTraitID().trim());
		int idobj2=Integer.parseInt(object2.getInterestTraitID().trim());		

		if (idobj1==idobj2)
			return 0;
		else if ((idobj1 > idobj2))
			return 1;
		else
			return -1;
	}

}
