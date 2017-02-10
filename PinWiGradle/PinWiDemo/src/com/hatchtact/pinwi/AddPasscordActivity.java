package com.hatchtact.pinwi;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.TypeFace;
import com.hatchtact.pinwi.utility.Validation;

public class AddPasscordActivity  extends MainActionBarActivity 
{

	private TypeFace typefaceClass;
	//private SharePreferenceClass sharePrefClass;
	private Validation checkValidation;
	private ShowMessages showMessage;

	private EditText etPassword;
	private TextView text_passcode;
	
	
	@Override
	public void onBackPressed() 
	{
		// TODO Auto-generated method stub
		finish();
		setResult(RESULT_CANCELED);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		//screenName="Setup Passcode";
		screenName="Lock your Profile";

		super.onCreate(savedInstanceState);

		setContentView(R.layout.passcode_activity);
		init();
	}
	public void init()
	{
		typefaceClass=new TypeFace(AddPasscordActivity.this);
		checkValidation = new Validation();
		showMessage = new ShowMessages(AddPasscordActivity.this);

		etPassword=(EditText) findViewById(R.id.passcode2);
		text_passcode = (TextView) findViewById(R.id.text_passcode);
		
		Bundle bundle=getIntent().getExtras();

		String passcode=bundle.getString("passCode");

		typefaceClass.setTypefaceRegular(text_passcode);
		typefaceClass.setTypefaceLight(etPassword);

		etPassword.setText(passcode);
		etPassword.requestFocus();


		etPassword.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if(etPassword.getText().length()==4)
				{
					Intent returnIntent = new Intent();
					returnIntent.putExtra("passCode",etPassword.getText().toString().trim());
					setResult(RESULT_OK, returnIntent);
					finish();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});	
		showKeyBoard();
	}


	private void showKeyBoard()
	{

		((InputMethodManager) getSystemService(AddPasscordActivity.INPUT_METHOD_SERVICE))
		.toggleSoftInput(InputMethodManager.SHOW_FORCED,
				InputMethodManager.HIDE_IMPLICIT_ONLY);


	}
}
