package com.hatchtact.pinwi;


import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import com.hatchtact.pinwi.classmodel.ChildProfile;
import com.hatchtact.pinwi.classmodel.GetListofChildsByParentID;
import com.hatchtact.pinwi.classmodel.GetListofChildsByParentIDList;
import com.hatchtact.pinwi.classmodel.GetParentDetails;
import com.hatchtact.pinwi.classmodel.ParentProfile;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.SocialConstants;
import com.hatchtact.pinwi.utility.StaticVariables;

public class GetDataService extends Service {

    private ServiceMethod serviceMethod;
    private SocialConstants social;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if(serviceMethod==null)
        {
            serviceMethod=new ServiceMethod();
        }
        if(social==null)
        {
            social=new SocialConstants(GetDataService.this);
        }

        new GetParentDetailFromServer(StaticVariables.currentParentId).execute();
        new GetChildName().execute();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

   /* public void onTaskRemoved(Intent rootIntent) {
        stopSelf();
    }*/

    private class GetParentDetailFromServer extends AsyncTask<Void, Void, Integer>
    {
        private int parentId;

        private GetParentDetails parentInformationFromServer=null;

        public GetParentDetailFromServer(int parentId)
        {
            // TODO Auto-generated constructor stub
            this.parentId = parentId;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Void... params)
        {
            // TODO Auto-generated method stub
            int ErrorCode=0;

            if(new CheckNetwork().checkNetworkConnection(GetDataService.this))
            {
                parentInformationFromServer = serviceMethod.getParentinformation(parentId);
            }
            else
            {
                ErrorCode=-1;
            }
            return ErrorCode;
        }

        @Override
        protected void onPostExecute(Integer  result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);


            if(result==-1)
            {

            }
            else
            {
                if(parentInformationFromServer!=null)
                {
                    try {
                        ParentProfile parentProfile = new ParentProfile();
                        parentProfile.setFirstName(parentInformationFromServer.getFirstName());
                        parentProfile.setEmailAddress(parentInformationFromServer.getEmailAddress());
                        parentProfile.setParentID(StaticVariables.currentParentId);
                        parentProfile.setContact("+91" + parentInformationFromServer.getContact());
                        parentProfile.setRelation(parentInformationFromServer.getRelation());
                        parentProfile.setDateOfBirth(parentInformationFromServer.getDateOfBirth());
                        social.userProfileClevertap("", "", 2, parentProfile, null, 0, 0);
                    }
                    catch (Exception e)
                    {

                    }
                }
            }
        }
    }
    private GetListofChildsByParentIDList getListofChildsByParentIDList;


    private class GetChildName extends AsyncTask<Void, Void, Integer>
    {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

        }

        @Override
        protected Integer doInBackground(Void... params)
        {
            // TODO Auto-generated method stub
            int ErrorCode=0;

            if(new CheckNetwork().checkNetworkConnection(GetDataService.this))
            {
                getListofChildsByParentIDList =serviceMethod.getchildListByParentId(StaticVariables.currentParentId);
            }
            else
            {
                ErrorCode=-1;
            }
            return ErrorCode;
        }

        @Override
        protected void onPostExecute(Integer  result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);



            if(result==-1)
            {
                new ShowMessages(GetDataService.this).showToastMessage("Please check your network connection");

            }
            else
            {
                if(getListofChildsByParentIDList!=null && getListofChildsByParentIDList.getGetListofChildsByParentID().size()>0)
                {
                    for(int i=0;i<getListofChildsByParentIDList.getGetListofChildsByParentID().size();i++)
                    {
                        ChildProfile modelChild = new ChildProfile();
                        GetListofChildsByParentID model=getListofChildsByParentIDList.getGetListofChildsByParentID().get(i);
                        modelChild.setChildID(model.getChildID());
                        modelChild.setNickName(model.getNickName());
                        modelChild.setFirstName(model.getFirstName());
                        modelChild.setDateOfBirth(model.getDateOfBirth());
                        modelChild.setGender(model.getGender());
                        modelChild.setSchoolName(model.getSchoolName());

                        try {
                            int currentChild = i+1;
                            social.userProfileClevertap("", "", 3, null, modelChild, StaticVariables.currentParentId, currentChild);

                        } catch (Exception e) {

                        }
                    }
                }
                else
                {

                }
            }
        }
    }

}
