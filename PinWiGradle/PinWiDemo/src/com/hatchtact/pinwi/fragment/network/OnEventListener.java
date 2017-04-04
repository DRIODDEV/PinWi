package com.hatchtact.pinwi.fragment.network;

/**
 * Created by Ankur on 12/18/2015.
 */
public interface OnEventListener<T>
{

        public void onSuccess(T object);
        public void onFailure(T object);


}
